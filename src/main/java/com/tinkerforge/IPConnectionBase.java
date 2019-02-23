/*
 * Copyright (C) 2012-2015 Matthias Bolte <matthias@tinkerforge.com>
 * Copyright (C) 2011-2012 Olaf Lüke <olaf@tinkerforge.com>
 *
 * Redistribution and use in source and binary forms of this file,
 * with or without modification, are permitted. See the Creative
 * Commons Zero (CC0 1.0) License for more details.
 */

package com.tinkerforge;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.SecureRandom;
import java.util.Hashtable;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class IPConnectionBase implements java.io.Closeable {
    private final static String BASE58 = "123456789abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";

    public final static byte FUNCTION_ENUMERATE = (byte) 254;
    public final static byte CALLBACK_ENUMERATE = (byte) 253;

    public final static byte CALLBACK_CONNECTED = 0;
    public final static byte CALLBACK_DISCONNECTED = 1;

    private final static int BROADCAST_UID = 0;

    // enumeration_type parameter to the enumerate callback
    public final static short ENUMERATION_TYPE_AVAILABLE = 0;
    public final static short ENUMERATION_TYPE_CONNECTED = 1;
    public final static short ENUMERATION_TYPE_DISCONNECTED = 2;

    // connect_reason parameter to the connected callback
    public final static short CONNECT_REASON_REQUEST = 0;
    public final static short CONNECT_REASON_AUTO_RECONNECT = 1;

    // disconnect_reason parameter to the disconnected callback
    public final static short DISCONNECT_REASON_REQUEST = 0;
    public final static short DISCONNECT_REASON_ERROR = 1;
    public final static short DISCONNECT_REASON_SHUTDOWN = 2;

    // returned by get_connection_state
    public final static short CONNECTION_STATE_DISCONNECTED = 0;
    public final static short CONNECTION_STATE_CONNECTED = 1;
    public final static short CONNECTION_STATE_PENDING = 2; // auto-reconnect in process

    final static int QUEUE_EXIT = 0;
    final static int QUEUE_META = 1;
    final static int QUEUE_PACKET = 2;

    BrickDaemon brickd = null;

    int responseTimeout = 2500;

    Hashtable<Long, Device> devices = new Hashtable<Long, Device>();
    LinkedBlockingQueue<CallbackQueueObject> callbackQueue = null;

    Object socketMutex = new Object();
    Object socketSendMutex = new Object();

    private String host;
    private int port;

    private final static int SEQUENCE_NUMBER_POS = 4;
    private int nextSequenceNumber = 0; // protected by sequenceNumberMutex
    private Object sequenceNumberMutex = new Object();

    private long nextAuthenticationNonce = 0; // protected by authenticationMutex
    private Object authenticationMutex = new Object(); // protects authentication handshake

    boolean receiveFlag = false;

    boolean autoReconnect = true;
    boolean autoReconnectAllowed = false;
    boolean autoReconnectPending = false;
    Socket socket = null; // protected by socketMutex
    long socketID = 0; // protected by socketMutex
    OutputStream out = null;
    InputStream in = null;
    ReceiveThread receiveThread = null;
    CallbackThread callbackThread = null;
    DisconnectProbeThread disconnectProbeThread = null;
    boolean disconnectProbeFlag = false;

    static class CallbackQueueObject {
        final int kind;
        final byte functionID;
        final short parameter;
        final long socketID;
        final byte[] packet;

        public CallbackQueueObject(int kind, byte functionID, short parameter,
                                   long socketID, byte[] packet) {
            this.kind = kind;
            this.functionID = functionID;
            this.parameter = parameter;
            this.socketID = socketID;
            this.packet = packet;
        }
    }

    static class DeviceHighLevelCallback {
        Object data = null;
        int length = 0;
    }

    public IPConnectionBase() {
    }

    /**
     * Creates a TCP/IP connection to the given \c host and \c port. The host
     * and port can point to a Brick Daemon or to a WIFI/Ethernet Extension.
     * <p>
     * Devices can only be controlled when the connection was established
     * successfully.
     * <p>
     * Blocks until the connection is established and throws an exception if
     * there is no Brick Daemon or WIFI/Ethernet Extension listening at the
     * given host and port.
     */
    public void connect(String host, int port) throws NetworkException, AlreadyConnectedException {
        NetworkException exception = null;
        CallbackThread callbackThreadTmp = null;
        LinkedBlockingQueue<CallbackQueueObject> callbackQueueTmp = null;

        synchronized (socketMutex) {
            if (socket != null) {
                throw new AlreadyConnectedException("Already connected to " + this.host + ":" + this.port);
            }

            this.host = host;
            this.port = port;

            try {
                connectUnlocked(false);
            } catch (NetworkException e) {
                exception = e;

                callbackThreadTmp = callbackThread;
                callbackQueueTmp = callbackQueue;

                callbackThread = null;
                callbackQueue = null;
            }
        }

        if (exception != null) {
            try {
                callbackQueueTmp.put(new CallbackQueueObject(QUEUE_EXIT, (byte) 0, (short) 0, 0, null));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (Thread.currentThread() != callbackThreadTmp) {
                try {
                    callbackThreadTmp.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            throw exception;
        }
    }

    // NOTE: Assumes that socket is null and socketMutex is locked
    void connectUnlocked(boolean isAutoReconnect) throws NetworkException {
        if (callbackThread == null) {
            callbackQueue = new LinkedBlockingQueue<CallbackQueueObject>();
            callbackThread = new CallbackThread(this, callbackQueue);
            callbackThread.start();
        }

        Socket tmpSocket;

        try {
            tmpSocket = new Socket(host, port);
        } catch (Exception e) {
            throw new NetworkException("Could not create socket: " + e.getMessage(), e);
        }

        try {
            tmpSocket.setTcpNoDelay(true);
        } catch (Exception e) {
            throw new NetworkException("Could not enable TCP-No-Delay socket option: " + e.getMessage(), e);
        }

        InputStream tmpIn;
        OutputStream tmpOut;

        try {
            tmpIn = tmpSocket.getInputStream();
        } catch (Exception e) {
            throw new NetworkException("Could not get socket input stream: " + e.getMessage(), e);
        }

        try {
            tmpOut = tmpSocket.getOutputStream();
        } catch (Exception e) {
            throw new NetworkException("Could not get socket output stream: " + e.getMessage(), e);
        }

        try {
            tmpOut.flush();
        } catch (Exception e) {
            throw new NetworkException("Could not flush socket output stream: " + e.getMessage(), e);
        }

        socket = tmpSocket;
        in = tmpIn;
        out = tmpOut;
        ++socketID;

        // create disconnect probe thread
        disconnectProbeFlag = true;
        disconnectProbeThread = new DisconnectProbeThread(this);
        disconnectProbeThread.start();

        callbackThread.setPacketDispatchAllowed(true);

        receiveFlag = true;
        receiveThread = new ReceiveThread(this);
        receiveThread.start();

        autoReconnectAllowed = false;
        autoReconnectPending = false;

        short connectReason = IPConnectionBase.CONNECT_REASON_REQUEST;

        if (isAutoReconnect) {
            connectReason = CONNECT_REASON_AUTO_RECONNECT;
        }

        try {
            callbackQueue.put(new CallbackQueueObject(QUEUE_META, CALLBACK_CONNECTED,
                    connectReason, 0, null));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Implement Closable interface to allow something like
     * <p>
     * try (IPConnection ipcon = new IPConnection()) { }
     */
    @Override
    public void close() throws java.io.IOException {
        try {
            disconnect();
        } catch (NotConnectedException e) {
            throw new java.io.IOException(e.getMessage(), e);
        }
    }

    /**
     * Disconnects the TCP/IP connection from the Brick Daemon or the
     * WIFI/Ethernet Extension.
     */
    public void disconnect() throws NotConnectedException {
        CallbackThread callbackThreadTmp = null;
        LinkedBlockingQueue<CallbackQueueObject> callbackQueueTmp = null;

        synchronized (socketMutex) {
            autoReconnectAllowed = false;

            if (autoReconnectPending) {
                autoReconnectPending = false;
            } else {
                if (socket == null) {
                    throw new NotConnectedException();
                }

                disconnectUnlocked();
            }

            callbackThreadTmp = callbackThread;
            callbackQueueTmp = callbackQueue;

            callbackThread = null;
            callbackQueue = null;
        }

        try {
            callbackQueueTmp.put(new CallbackQueueObject(QUEUE_META, CALLBACK_DISCONNECTED,
                    DISCONNECT_REASON_REQUEST, 0, null));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            callbackQueueTmp.put(new CallbackQueueObject(QUEUE_EXIT, (byte) 0, (short) 0, 0, null));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (Thread.currentThread() != callbackThreadTmp) {
            try {
                callbackThreadTmp.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // NOTE: Assumes that socket is not null and socketMutex is locked
    void disconnectUnlocked() {
        disconnectProbeThread.shutdown();
        try {
            disconnectProbeThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // stop dispatching packet callbacks before ending the receive
        // thread to avoid timeout exceptions due to callback functions
        // trying to call getters
        callbackThread.setPacketDispatchAllowed(false);

        receiveFlag = false;

        closeSocket();

        if (receiveThread != null) {
            try {
                receiveThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            receiveThread = null;
        }
    }

    /**
     * Performs an authentication handshake with the connected Brick Daemon or
     * WIFI/Ethernet Extension. If the handshake succeeds the connection switches
     * from non-authenticated to authenticated state and communication can
     * continue as normal. If the handshake fails then the connection gets closed.
     * Authentication can fail if the wrong secret was used or if authentication
     * is not enabled at all on the Brick Daemon or the WIFI/Ethernet Extension.
     * <p>
     * For more information about authentication see
     * http://www.tinkerforge.com/en/doc/Tutorials/Tutorial_Authentication/Tutorial.html
     */
    public void authenticate(String secret) throws TimeoutException, NotConnectedException, CryptoException {
        synchronized (authenticationMutex) {
            if (nextAuthenticationNonce == 0) {
                byte[] seed = new SecureRandom().generateSeed(4);
                ByteBuffer bb = ByteBuffer.wrap(seed, 0, 4);

                bb.order(ByteOrder.LITTLE_ENDIAN);
                nextAuthenticationNonce = bb.getInt();
            }

            byte[] serverNonce = brickd.getAuthenticationNonce();
            byte[] clientNonce;
            ByteBuffer bb = ByteBuffer.allocate(4);

            bb.order(ByteOrder.LITTLE_ENDIAN);
            bb.putInt((int) nextAuthenticationNonce);

            clientNonce = bb.array();
            nextAuthenticationNonce = (nextAuthenticationNonce + 1) % ((long) 1 << 32);

            byte[] data = new byte[serverNonce.length + clientNonce.length];

            System.arraycopy(serverNonce, 0, data, 0, serverNonce.length);
            System.arraycopy(clientNonce, 0, data, serverNonce.length, clientNonce.length);

            byte[] digest;

            try {
                Mac mac = Mac.getInstance("HmacSHA1");

                mac.init(new SecretKeySpec(secret.getBytes(), "HmacSHA1"));

                digest = mac.doFinal(data);
            } catch (Exception e) {
                throw new CryptoException("Could not generate HMAC-SHA1: " + e.getMessage(), e);
            }

            brickd.authenticate(clientNonce, digest);
        }
    }

    /**
     * Can return the following states:
     * <p>
     * - CONNECTION_STATE_DISCONNECTED: No connection is established.
     * - CONNECTION_STATE_CONNECTED: A connection to the Brick Daemon or
     * the WIFI/Ethernet Extension is established.
     * - CONNECTION_STATE_PENDING: IP Connection is currently trying to
     * connect.
     */
    public short getConnectionState() {
        if (socket != null) {
            return CONNECTION_STATE_CONNECTED;
        }

        if (autoReconnectPending) {
            return CONNECTION_STATE_PENDING;
        }

        return CONNECTION_STATE_DISCONNECTED;
    }

    /**
     * Enables or disables auto-reconnect. If auto-reconnect is enabled,
     * the IP Connection will try to reconnect to the previously given
     * host and port, if the connection is lost.
     * <p>
     * Default value is *true*.
     */
    public void setAutoReconnect(boolean autoReconnect) {
        this.autoReconnect = autoReconnect;

        if (!autoReconnect) {
            autoReconnectAllowed = false;
        }
    }

    /**
     * Returns *true* if auto-reconnect is enabled, *false* otherwise.
     */
    public boolean getAutoReconnect() {
        return autoReconnect;
    }

    /**
     * Sets the timeout in milliseconds for getters and for setters for which the
     * response expected flag is activated.
     * <p>
     * Default timeout is 2500.
     */
    public void setTimeout(int timeout) {
        if (timeout < 0) {
            throw new IllegalArgumentException("Timeout cannot be negative");
        }

        responseTimeout = timeout;
    }

    /**
     * Returns the timeout as set by setTimeout.
     */
    public int getTimeout() {
        return responseTimeout;
    }

    /**
     * Broadcasts an enumerate request. All devices will respond with an enumerate
     * callback.
     */
    public void enumerate() throws NotConnectedException {
        ByteBuffer request = createRequestPacket((byte) 8, FUNCTION_ENUMERATE, null);

        sendRequest(request.array());
    }

    // to preserve the exact current behavior of the IPConnection all of the
    // following abstract methods have to operate synchronous. all listeners
    // have to be called before the methods return. this is especially important
    // for the DisconnectedListener, because the callback thread expects that
    // the user got informed about the disconnect event before it starts the
    // auto-reconnect logic. this is because the user shall get a chance to act
    // upon the disconnect event before the auto-reconnect logic starts.
    //
    // synchronous dispatch of the callback is only important if the current
    // behavior should be preserved exactly. all callbacks can also be dispatched
    // asynchronously. the only negative effect of asynchronous dispatch is
    // that the current order of operations around the disconnected callback
    // cannot be guaranteed anymore.
    protected abstract void callEnumerateListeners(String uid, String connectedUid, char position,
                                                   short[] hardwareVersion, short[] firmwareVersion,
                                                   int deviceIdentifier, short enumerationType);

    protected abstract boolean hasEnumerateListeners();

    protected abstract void callConnectedListeners(short connectReason);

    protected abstract void callDisconnectedListeners(short disconnectReason);

    protected abstract void callDeviceListener(Device device, byte functionID, byte[] packet);

    void handleResponse(byte[] packet) {
        byte functionID = getFunctionIDFromData(packet);
        short sequenceNumber = unsignedByte(getSequenceNumberFromData(packet));

        disconnectProbeFlag = false;

        if (sequenceNumber == 0 && functionID == CALLBACK_ENUMERATE) {
            handleEnumerate(packet);
            return;
        }

        long uid = getUIDFromData(packet);

        if (!devices.containsKey(uid)) {
            // Message for an unknown device, ignoring it
            return;
        }

        Device device = devices.get(uid);

        if (sequenceNumber == 0) {
            if (device.callbacks[functionID] != null) {
                try {
                    callbackQueue.put(new CallbackQueueObject(QUEUE_PACKET, (byte) 0,
                            (short) 0, 0, packet));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return;
        }

        if (functionID == device.expectedResponseFunctionID &&
                sequenceNumber == device.expectedResponseSequenceNumber) {
            try {
                device.responseQueue.put(packet);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return;
        }

        // Response seems to be OK, but can't be handled
    }

    // NOTE: Assumes that socketMutex is locked, if disconnectImmediately is true
    void handleDisconnectByPeer(short disconnectReason, long socketID, boolean disconnectImmediately) {
        autoReconnectAllowed = true;

        if (disconnectImmediately) {
            disconnectUnlocked();
        }

        try {
            callbackQueue.put(new CallbackQueueObject(QUEUE_META, CALLBACK_DISCONNECTED,
                    disconnectReason, socketID, null));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // NOTE: Assumes that socketMutex is locked
    void closeSocket() {
        if (in != null) {
            try {
                in.close();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }

        if (out != null) {
            try {
                out.close();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }

        if (socket != null) {
            try {
                socket.close();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }

        in = null;
        out = null;
        socket = null;
    }

    static long getUIDFromData(byte[] data) {
        return (long) (data[0] & 0xFF) | ((long) (data[1] & 0xFF) << 8) |
                ((long) (data[2] & 0xFF) << 16) | ((long) (data[3] & 0xFF) << 24);
    }

    static byte getLengthFromData(byte[] data) {
        return data[4];
    }

    static byte getFunctionIDFromData(byte[] data) {
        return data[5];
    }

    static byte getSequenceNumberFromData(byte[] data) {
        return (byte) ((((int) data[6]) >> 4) & 0x0F);
    }

    static boolean getResponseExpectedFromData(byte[] data) {
        return ((((int) data[6]) >> 3) & 0x01) == 0x01;
    }

    static byte getErrorCodeFromData(byte[] data) {
        return (byte) ((((int) data[7]) >> 6) & 0x03);
    }

    static String string(ByteBuffer buffer, int length) {
        StringBuilder builder = new StringBuilder(length);
        int i = 0;

        while (i < length) {
            char c = (char) buffer.get();
            ++i;

            if (c == 0) {
                break;
            }

            builder.append(c);
        }

        while (i < length) {
            buffer.get();
            ++i;
        }

        return builder.toString();
    }

    static short unsignedByte(byte data) {
        return (short) ((short) data & 0xFF);
    }

    static int unsignedShort(short data) {
        return (int) data & 0xFFFF;
    }

    static long unsignedInt(int data) {
        return (long) data & 0xFFFFFFFFL;
    }

    void sendRequest(byte[] request) throws NotConnectedException {
        synchronized (socketMutex) {
            if (getConnectionState() != CONNECTION_STATE_CONNECTED) {
                throw new NotConnectedException();
            }

            try {
                synchronized (socketSendMutex) {
                    out.write(request);
                }
            } catch (java.net.SocketException e) {
                handleDisconnectByPeer(DISCONNECT_REASON_ERROR, 0, true);
                throw new NotConnectedException("Disconnected during send operartion: " + e.getMessage(), e);
            } catch (Exception e) {
                e.printStackTrace();
            }

            disconnectProbeFlag = false;
        }
    }

    private void handleEnumerate(byte[] packet) {
        if (hasEnumerateListeners()) {
            try {
                callbackQueue.put(new CallbackQueueObject(QUEUE_PACKET, (byte) 0,
                        (short) 0, 0, packet));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    byte getNextSequenceNumber() {
        synchronized (sequenceNumberMutex) {
            int sequenceNumber = nextSequenceNumber + 1;
            nextSequenceNumber = sequenceNumber % 15;
            return (byte) sequenceNumber;
        }
    }

    ByteBuffer createRequestPacket(byte length, byte functionID, Device device) {
        int uid = BROADCAST_UID;
        byte options = 0;
        byte flags = 0;

        if (device != null) {
            uid = (int) device.uid;

            if (device.getResponseExpected(functionID)) {
                options = 8;
            }
        }

        options |= getNextSequenceNumber() << SEQUENCE_NUMBER_POS;

        ByteBuffer packet = ByteBuffer.allocate(length);

        packet.order(ByteOrder.LITTLE_ENDIAN);
        packet.putInt(uid);
        packet.put(length);
        packet.put(functionID);
        packet.put(options);
        packet.put(flags);

        return packet;
    }

    static String base58Encode(long value) {
        String encoded = "";

        while (value >= 58) {
            long div = value / 58;
            int mod = (int) (value % 58);
            encoded = BASE58.charAt(mod) + encoded;
            value = div;
        }

        return BASE58.charAt((int) value) + encoded;
    }

    static long base58Decode(String encoded) {
        long value = 0;
        long columnMultiplier = 1;

        for (int i = encoded.length() - 1; i >= 0; i--) {
            int column = BASE58.indexOf(encoded.charAt(i));

            if (column < 0) {
                throw new IllegalArgumentException("Invalid Base58 value: " + encoded);
            }

            value += column * columnMultiplier;
            columnMultiplier *= 58;
        }

        return value;
    }
}