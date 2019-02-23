package berlin.yuna.tinkerforgesensor.model.sensor.bricklet;

import berlin.yuna.tinkerforgesensor.model.exception.NetworkConnectionException;
import com.tinkerforge.BrickletAirQuality;
import com.tinkerforge.Device;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

import static berlin.yuna.tinkerforgesensor.model.SensorRegistry.CALLBACK_PERIOD;
import static berlin.yuna.tinkerforgesensor.model.sensor.bricklet.Sensor.LedStatusType.LED_STATUS;
import static berlin.yuna.tinkerforgesensor.model.sensor.bricklet.Sensor.LedStatusType.LED_STATUS_HEARTBEAT;
import static berlin.yuna.tinkerforgesensor.model.sensor.bricklet.Sensor.LedStatusType.LED_STATUS_OFF;
import static berlin.yuna.tinkerforgesensor.model.sensor.bricklet.Sensor.LedStatusType.LED_STATUS_ON;
import static berlin.yuna.tinkerforgesensor.model.type.ValueType.AIR_PRESSURE;
import static berlin.yuna.tinkerforgesensor.model.type.ValueType.DEVICE_TIMEOUT;
import static berlin.yuna.tinkerforgesensor.model.type.ValueType.HUMIDITY;
import static berlin.yuna.tinkerforgesensor.model.type.ValueType.IAQ_INDEX;
import static berlin.yuna.tinkerforgesensor.model.type.ValueType.TEMPERATURE;

/**
 * Measures IAQ index, temperature, humidity and air pressure
 * <b>Values</b>
 * <br />AIR Quality[IAQ]  = n
 * <br />AIR_PRESSURE[mbar] = n / 1000.0
 * <br />TEMPERATURE[°C] = n / 100.0
 * <br />HUMIDITY[%RH] = n / 100.0
 * <br /><a href="https://www.tinkerforge.com/en/doc/Hardware/Bricklets/Air_Quality.html">Official doku</a>
 */
public class AirQuality extends Sensor<BrickletAirQuality> {

    public AirQuality(final Device device, final Sensor parent, final String uid) throws NetworkConnectionException {
        super((BrickletAirQuality) device, parent, uid, true);
    }

    @Override
    protected Sensor<BrickletAirQuality> initListener() throws TimeoutException, NotConnectedException {
        device.setAllValuesCallbackConfiguration(CALLBACK_PERIOD * 8, false);
        device.addAllValuesListener((iaqIndex, iaqIndexAccuracy, temperature, humidity, airPressure) ->
        {
            sendEvent(IAQ_INDEX, (long) iaqIndex);
            sendEvent(TEMPERATURE, (long) temperature);
            sendEvent(HUMIDITY, (long) humidity);
            sendEvent(AIR_PRESSURE, (long) airPressure * 10);
        });
        return this;
    }

    @Override
    public Sensor<BrickletAirQuality> value(Object value) {
        return this;
    }

    @Override
    public Sensor<BrickletAirQuality> ledStatus(Integer value) {
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

    @Override
    public Sensor<BrickletAirQuality> ledAdditional(Integer value) {
        return this;
    }
}