package berlin.yuna.tinkerforgesensor.model.sensor.bricklet;

import berlin.yuna.tinkerforgesensor.model.exception.NetworkConnectionException;
import berlin.yuna.tinkerforgesensor.model.type.ValueType;
import com.tinkerforge.BrickletDualButtonV2;
import com.tinkerforge.Device;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

import static berlin.yuna.tinkerforgesensor.model.sensor.bricklet.Sensor.LedStatusType.LED_ADDITIONAL_OFF;
import static berlin.yuna.tinkerforgesensor.model.sensor.bricklet.Sensor.LedStatusType.LED_ADDITIONAL_ON;
import static berlin.yuna.tinkerforgesensor.model.sensor.bricklet.Sensor.LedStatusType.LED_ADDITIONAL_STATUS;
import static berlin.yuna.tinkerforgesensor.model.sensor.bricklet.Sensor.LedStatusType.LED_STATUS;
import static berlin.yuna.tinkerforgesensor.model.sensor.bricklet.Sensor.LedStatusType.LED_STATUS_HEARTBEAT;
import static berlin.yuna.tinkerforgesensor.model.sensor.bricklet.Sensor.LedStatusType.LED_STATUS_OFF;
import static berlin.yuna.tinkerforgesensor.model.sensor.bricklet.Sensor.LedStatusType.LED_STATUS_ON;
import static berlin.yuna.tinkerforgesensor.model.type.ValueType.BUTTON;
import static berlin.yuna.tinkerforgesensor.model.type.ValueType.BUTTON_PRESSED;
import static berlin.yuna.tinkerforgesensor.model.type.ValueType.DEVICE_TIMEOUT;

/**
 * <h3>{@link DualButton}</h3>
 * <i>Two tactile buttons with built-in blue LEDs</i>
 *
 * <h3>Values</h3>
 * <ul>
 * <li>{@link ValueType#BUTTON} [10, 20] = Released</li>
 * <li>{@link ValueType#BUTTON} [11, 21] = Pressed</li>
 * <li>{@link ValueType#BUTTON_PRESSED} [0/1] = Released/Pressed</li>
 * </ul>
 * <h3>Technical Info</h3>
 * <ul>
 * <li><a href="href="https://www.tinkerforge.com/en/doc/Hardware/Bricklets/Dual_Button.html">Official documentation</a></li>
 * </ul>
 * <h6>Getting button with pressed value (digit_1= button, digit_2 = pressed/released) example</h6>
 * <code>stack.values().button();</code>
 * <h6>Getting button pressed example</h6>
 * <code>stack.values().buttonPressed();</code>
 * <h6>Set LEDs on</h6>
 * <code>button.ledAdditionalOn();</code>
 * <h6>Set LEDs off</h6>
 * <code>button.ledAdditionalOff();</code>
 * <h6>Set LEDs active on press</h6>
 * <code>button.ledAdditionalStatus();</code>
 * <h6>Set LEDs active on release</h6>
 * <code>button.ledAdditionalHeartbeat();</code>
 */
public class DualButton extends Sensor<BrickletDualButtonV2> {

    private int buttonL;
    private int buttonR;

    public DualButton(final Device device, final String uid) throws NetworkConnectionException {
        super((BrickletDualButtonV2) device, uid, true);
    }

    @Override
    protected Sensor<BrickletDualButtonV2> initListener() {
        device.addStateChangedListener((buttonL, buttonR, ledL, ledR) -> {
            sendEvent(buttonL, buttonR);
        });
        refreshPeriod(69);
        return this;
    }

    @Override
    public Sensor<BrickletDualButtonV2> send(final Object value) {
        return this;
    }

    @Override
    public Sensor<BrickletDualButtonV2> ledStatus(final Integer value) {
        try {
            if (value == LED_STATUS_OFF.bit) {
                device.setStatusLEDConfig((short) LED_STATUS_OFF.bit);
            } else if (value == LED_STATUS_ON.bit) {
                device.setStatusLEDConfig((short) LED_STATUS_ON.bit);
            } else if (value == LED_STATUS_HEARTBEAT.bit) {
                device.setStatusLEDConfig((short) LED_STATUS_HEARTBEAT.bit);
            } else if (value == LED_STATUS.bit) {
                device.setStatusLEDConfig((short) LED_STATUS.bit);
            }
        } catch (TimeoutException | NotConnectedException ignored) {
            sendEvent(DEVICE_TIMEOUT, 404L);
        }
        return this;
    }

    //TODO: set button 1 && button 2 different
    @Override
    public Sensor<BrickletDualButtonV2> ledAdditional(final Integer value) {
        try {
            if (value == LED_ADDITIONAL_OFF.bit) {
                device.setLEDState(3, 3);
            } else if (value == LED_ADDITIONAL_ON.bit) {
                device.setLEDState(2, 2);
            } else if (value == LED_STATUS_HEARTBEAT.bit) {
                device.setLEDState(0, 0); //FIXME: does this make sense [= invert status]???
            } else if (value == LED_ADDITIONAL_STATUS.bit) {
                device.setLEDState(1, 1);
            }
        } catch (TimeoutException | NotConnectedException ignored) {
            sendEvent(DEVICE_TIMEOUT, 404L);
        }
        return this;
    }

    @Override
    public Sensor<BrickletDualButtonV2> flashLed() {
        try {
            ledAdditionalOff();
            Thread.sleep(128);
            ledAdditionalOn();
            Thread.sleep(128);
            ledAdditionalOff();
            Thread.sleep(128);
            device.setLEDState(2, 3);
            Thread.sleep(128);
            device.setLEDState(3, 2);
            Thread.sleep(128);
            ledAdditionalStatus();
        } catch (Exception ignore) {
        }
        return this;
    }

    @Override
    public Sensor<BrickletDualButtonV2> refreshPeriod(final int milliseconds) {
        try {
            buttonL = device.getButtonState().buttonL;
            buttonR = device.getButtonState().buttonR;
            sendEvent(buttonL, buttonR);

            if (milliseconds < 1) {
                device.setStateChangedCallbackConfiguration(false);
            } else {
                device.setStateChangedCallbackConfiguration(true);
            }
        } catch (TimeoutException | NotConnectedException ignored) {
            sendEvent(DEVICE_TIMEOUT, 404L);
        }
        return this;
    }

    private void sendEvent(final int buttonL, final int buttonR) {
        if (this.buttonL != buttonL) {
            this.buttonL = buttonL;
            sendEvent(BUTTON_PRESSED, buttonL == 1 ? 0L : 1L);
            sendEvent(BUTTON, buttonL == 1 ? 10L : 11L);
        }

        if (this.buttonR != buttonR) {
            this.buttonR = buttonR;
            sendEvent(BUTTON_PRESSED, buttonR == 1 ? 0L : 1L);
            sendEvent(BUTTON, buttonR == 1 ? 20L : 21L);
        }
    }
}
