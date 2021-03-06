package berlin.yuna.tinkerforgesensor.logic;

import berlin.yuna.tinkerforgesensor.model.type.SensorEvent;
import berlin.yuna.tinkerforgesensor.model.type.ValueType;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

import static berlin.yuna.tinkerforgesensor.model.type.ValueType.KEY_PRESSED;
import static berlin.yuna.tinkerforgesensor.model.type.ValueType.KEY_RELEASED;
import static berlin.yuna.tinkerforgesensor.model.type.ValueType.MOUSE_CLICK_COUNT;
import static berlin.yuna.tinkerforgesensor.model.type.ValueType.MOUSE_DRAGGED;
import static berlin.yuna.tinkerforgesensor.model.type.ValueType.MOUSE_ENTERED;
import static berlin.yuna.tinkerforgesensor.model.type.ValueType.MOUSE_EXITED;
import static berlin.yuna.tinkerforgesensor.model.type.ValueType.MOUSE_MOVED;
import static berlin.yuna.tinkerforgesensor.model.type.ValueType.MOUSE_MOVE_X;
import static berlin.yuna.tinkerforgesensor.model.type.ValueType.MOUSE_MOVE_Y;
import static berlin.yuna.tinkerforgesensor.model.type.ValueType.MOUSE_PRESSED;
import static berlin.yuna.tinkerforgesensor.model.type.ValueType.MOUSE_RELEASED;
import static berlin.yuna.tinkerforgesensor.model.type.ValueType.MOUSE_WHEEL_MOVED;
import static java.util.Objects.requireNonNull;

public class HumanInput extends JFrame implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    public static final int LABEL_HEIGHT = 24;
    final JLabel keyCode = new JLabel();
    final JLabel keyTextCode = new JLabel();
    final JLabel keyChar = new JLabel();
    final JLabel keyType = new JLabel();
    final JLabel keyMods = new JLabel();
    final JLabel keyAction = new JLabel();
    final JLabel keyLocation = new JLabel();

    final JLabel mouseType = new JLabel();
    final JLabel mouseX = new JLabel();
    final JLabel mouseY = new JLabel();
    final JLabel mouseMods = new JLabel();
    final JLabel mouseClickCount = new JLabel();
    final JLabel mouseButton = new JLabel();
    public final List<Consumer<SensorEvent>> sensorEventConsumerList = new CopyOnWriteArrayList<>();

    public static void main(final String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(javax.swing.plaf.nimbus.NimbusLookAndFeel.class.getTypeName());
        new HumanInput();
    }

    public HumanInput() {
        setIcon();
        setTitle(getClass().getSimpleName());
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final Container content = getContentPane();
        final Box keyPanel = Box.createVerticalBox();
        keyPanel.setBorder(BorderFactory.createTitledBorder("Keyboard"));
        keyPanel.add(getHPanel("KeyCode", keyCode));
        keyPanel.add(getHPanel("KeyTextCode", keyTextCode));
        keyPanel.add(getHPanel("KeyChar", keyChar));
        keyPanel.add(getHPanel("KeyType", keyType));
        keyPanel.add(getHPanel("KeyMods", keyMods));
        keyPanel.add(getHPanel("KeyAction", keyAction));
        keyPanel.add(getHPanel("KeyLocation", keyLocation));

        final Box mousePanel = Box.createVerticalBox();
        mousePanel.setBorder(BorderFactory.createTitledBorder("Mouse"));
        mousePanel.add(getHPanel("MouseType", mouseType));
        mousePanel.add(getHPanel("MouseX", mouseX));
        mousePanel.add(getHPanel("MouseY", mouseY));
        mousePanel.add(getHPanel("MouseMods", mouseMods));
        mousePanel.add(getHPanel("MouseClickCount", mouseClickCount));
        mousePanel.add(getHPanel("MouseButton", mouseButton));
        mousePanel.add(getHPanel("", new JLabel()));

        final Box hPanel = Box.createHorizontalBox();
        hPanel.setBorder(BorderFactory.createEtchedBorder());
        final int length = keyPanel.getComponents().length;
        setMinimumSize(new Dimension(650, LABEL_HEIGHT * (length + 1)));
        hPanel.add(keyPanel);
        hPanel.add(mousePanel);
        content.add(hPanel);
        pack();
        setVisible(true);
    }

    private void setIcon() {
        final Image image = new ImageIcon(requireNonNull(getClass().getClassLoader().getResource("humanInput.png"))).getImage();
//        Application.getApplication().setDockIconImage(image);
        setIconImage(image);
    }

    private Box getHPanel(final String key, final JLabel value) {
        final Box panel = Box.createHorizontalBox();
        final JLabel label = new JLabel(key);
        final Dimension size = new Dimension(150, LABEL_HEIGHT);
        label.setSize(size);
        label.setMaximumSize(size);
        label.setMinimumSize(size);
        value.setSize(size);
        value.setMaximumSize(size);
        value.setMinimumSize(size);
        value.setBackground(Color.WHITE);
        value.setText(" ");
        panel.add(label);
        panel.add(value);
        value.setEnabled(false);
        return panel;
    }

    public void keyPressed(final KeyEvent event) {
        onKeyEvent(KEY_PRESSED, event);
    }

    public void keyReleased(final KeyEvent event) {
        onKeyEvent(KEY_RELEASED, event);
    }

    public void keyTyped(final KeyEvent event) {
//        onKeyEvent(KEY_PRESSED, event);
    }

    @Override
    public void mouseClicked(final MouseEvent event) {
        onMouseEvent(MOUSE_CLICK_COUNT, event);
    }

    @Override
    public void mousePressed(final MouseEvent event) {
        onMouseEvent(MOUSE_PRESSED, event);
    }

    @Override
    public void mouseReleased(final MouseEvent event) {
        onMouseEvent(MOUSE_RELEASED, event);
    }

    @Override
    public void mouseEntered(final MouseEvent event) {
        onMouseEvent(MOUSE_ENTERED, event);
    }

    @Override
    public void mouseExited(final MouseEvent event) {
        onMouseEvent(MOUSE_EXITED, event);
    }

    @Override
    public void mouseDragged(final MouseEvent event) {
        onMouseEvent(MOUSE_DRAGGED, event);
    }

    @Override
    public void mouseMoved(final MouseEvent event) {
        onMouseEvent(MOUSE_MOVED, event);
    }

    @Override
    public void mouseWheelMoved(final MouseWheelEvent event) {
        onMouseEvent(MOUSE_WHEEL_MOVED, event);
    }

    private void onMouseEvent(final ValueType type, final MouseEvent event) {
        mouseType.setText(type.toString());
        mouseMods.setText(String.valueOf(event.getModifiers()));
        sendEventToConsumer(mouseX, MOUSE_MOVE_X, (long) event.getX());
        sendEventToConsumer(mouseY, MOUSE_MOVE_Y, (long) event.getY());
        sendEventToConsumer(mouseClickCount, MOUSE_CLICK_COUNT, (long) event.getClickCount());
        if (type.isMouseClickCount()) {
            sendEventToConsumer(mouseButton, MOUSE_PRESSED, 0L);
        } else {
            sendEventToConsumer(mouseButton, MOUSE_PRESSED, (long) event.getButton());
        }

    }

    private void onKeyEvent(final ValueType type, final KeyEvent event) {
        final int code = event.getKeyCode();
        keyTextCode.setText(KeyEvent.getKeyText(code));
        keyType.setText(type.toString());
        keyMods.setText(KeyEvent.getModifiersExText(event.getModifiersEx()));
        keyAction.setText(String.valueOf(event.isActionKey()));
        keyLocation.setText(keyboardLocation(event.getKeyLocation()));

        sendEventToConsumer(keyCode, type, (long) code);
    }

    private String keyboardLocation(final int keyLocation) {
        switch (keyLocation) {
            case KeyEvent.KEY_LOCATION_RIGHT:
                return "Right";
            case KeyEvent.KEY_LOCATION_LEFT:
                return "Left";
            case KeyEvent.KEY_LOCATION_NUMPAD:
                return "NumPad";
            case KeyEvent.KEY_LOCATION_STANDARD:
                return "Standard";
            case KeyEvent.KEY_LOCATION_UNKNOWN:
            default:
                return "Unknown [" + keyLocation + "]";
        }
    }

    private void sendEventToConsumer(final JLabel label, final ValueType type, final Long value) {
        final String labelValue = String.valueOf(value);
        label.setText(labelValue);
        for (Consumer<SensorEvent> consumer : sensorEventConsumerList) {
            consumer.accept(new SensorEvent(null, value, type));
        }
    }
}