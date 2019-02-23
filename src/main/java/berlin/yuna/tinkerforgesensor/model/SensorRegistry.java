package berlin.yuna.tinkerforgesensor.model;

import berlin.yuna.tinkerforgesensor.model.sensor.brick.DC;
import berlin.yuna.tinkerforgesensor.model.sensor.brick.IMU;
import berlin.yuna.tinkerforgesensor.model.sensor.brick.IMU2;
import berlin.yuna.tinkerforgesensor.model.sensor.brick.Master;
import berlin.yuna.tinkerforgesensor.model.sensor.bricklet.AirQuality;
import berlin.yuna.tinkerforgesensor.model.sensor.bricklet.Barometer;
import berlin.yuna.tinkerforgesensor.model.sensor.bricklet.BarometerV2;
import berlin.yuna.tinkerforgesensor.model.sensor.bricklet.ButtonRGB;
import berlin.yuna.tinkerforgesensor.model.sensor.bricklet.Default;
import berlin.yuna.tinkerforgesensor.model.sensor.bricklet.DisplayLcd20x4;
import berlin.yuna.tinkerforgesensor.model.sensor.bricklet.DisplaySegment;
import berlin.yuna.tinkerforgesensor.model.sensor.bricklet.DistanceIR;
import berlin.yuna.tinkerforgesensor.model.sensor.bricklet.DistanceIRV2;
import berlin.yuna.tinkerforgesensor.model.sensor.bricklet.DistanceUS;
import berlin.yuna.tinkerforgesensor.model.sensor.bricklet.Humidity;
import berlin.yuna.tinkerforgesensor.model.sensor.bricklet.HumidityV2;
import berlin.yuna.tinkerforgesensor.model.sensor.bricklet.IO16;
import berlin.yuna.tinkerforgesensor.model.sensor.bricklet.IO16V2;
import berlin.yuna.tinkerforgesensor.model.sensor.bricklet.LightAmbient;
import berlin.yuna.tinkerforgesensor.model.sensor.bricklet.LightAmbientV2;
import berlin.yuna.tinkerforgesensor.model.sensor.bricklet.LightAmbientV3;
import berlin.yuna.tinkerforgesensor.model.sensor.bricklet.LightColor;
import berlin.yuna.tinkerforgesensor.model.sensor.bricklet.LightUv;
import berlin.yuna.tinkerforgesensor.model.sensor.bricklet.LightUvV2;
import berlin.yuna.tinkerforgesensor.model.sensor.bricklet.MotionDetector;
import berlin.yuna.tinkerforgesensor.model.sensor.bricklet.MotionDetectorV2;
import berlin.yuna.tinkerforgesensor.model.sensor.bricklet.RotaryV2;
import berlin.yuna.tinkerforgesensor.model.sensor.bricklet.Sensor;
import berlin.yuna.tinkerforgesensor.model.sensor.bricklet.SoundIntensity;
import berlin.yuna.tinkerforgesensor.model.sensor.bricklet.Speaker;
import berlin.yuna.tinkerforgesensor.model.sensor.bricklet.Temperature;
import berlin.yuna.tinkerforgesensor.model.sensor.bricklet.TemperatureV2;
import berlin.yuna.tinkerforgesensor.model.sensor.bricklet.Tilt;
import com.tinkerforge.BrickDC;
import com.tinkerforge.BrickIMU;
import com.tinkerforge.BrickIMUV2;
import com.tinkerforge.BrickMaster;
import com.tinkerforge.BrickletAirQuality;
import com.tinkerforge.BrickletAmbientLight;
import com.tinkerforge.BrickletAmbientLightV2;
import com.tinkerforge.BrickletAmbientLightV3;
import com.tinkerforge.BrickletBarometer;
import com.tinkerforge.BrickletBarometerV2;
import com.tinkerforge.BrickletColor;
import com.tinkerforge.BrickletDistanceIR;
import com.tinkerforge.BrickletDistanceIRV2;
import com.tinkerforge.BrickletDistanceUS;
import com.tinkerforge.BrickletHumidity;
import com.tinkerforge.BrickletHumidityV2;
import com.tinkerforge.BrickletIO16;
import com.tinkerforge.BrickletIO16V2;
import com.tinkerforge.BrickletLCD20x4;
import com.tinkerforge.BrickletMotionDetector;
import com.tinkerforge.BrickletMotionDetectorV2;
import com.tinkerforge.BrickletPiezoSpeaker;
import com.tinkerforge.BrickletRGBLEDButton;
import com.tinkerforge.BrickletRotaryEncoderV2;
import com.tinkerforge.BrickletSegmentDisplay4x7;
import com.tinkerforge.BrickletSoundIntensity;
import com.tinkerforge.BrickletTemperature;
import com.tinkerforge.BrickletTemperatureV2;
import com.tinkerforge.BrickletTilt;
import com.tinkerforge.BrickletUVLight;
import com.tinkerforge.BrickletUVLightV2;
import com.tinkerforge.Device;
import com.tinkerforge.DummyDevice;
import java.lang.Class;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Autogenerated with [GeneratorSensorRegistry:generate]
 * Registration of {@link Device} and the Wrapper
 * {@link Sensor}
 */
public class SensorRegistry {
  public static final long CALLBACK_PERIOD = 64;

  private static ConcurrentHashMap<Class<? extends Device>, Sensor.SensorFactory> sensorMap = initSensor();

  private static ConcurrentHashMap<Integer, Sensor.DeviceFactory> deviceMap = initDevice();

  public static Sensor.SensorFactory getSensor(final Class<? extends Device> device) {
    return sensorMap.get(device);
  }

  public static Sensor.DeviceFactory getDevice(Integer deviceIdentifier) {
    return deviceMap.get(deviceIdentifier);
  }

  public static List<Class> getDeviceAvailableDevices() {
    return new ArrayList<>(sensorMap.keySet());
  }

  private static ConcurrentHashMap<Class<? extends Device>, Sensor.SensorFactory> initSensor() {
    ConcurrentHashMap<Class<? extends Device>, Sensor.SensorFactory> registry = new ConcurrentHashMap<>();
    registry.put(BrickletAirQuality.class, AirQuality::new);
    registry.put(BrickletBarometer.class, Barometer::new);
    registry.put(BrickletBarometerV2.class, BarometerV2::new);
    registry.put(BrickletRGBLEDButton.class, ButtonRGB::new);
    registry.put(BrickDC.class, DC::new);
    registry.put(DummyDevice.class, Default::new);
    registry.put(BrickletLCD20x4.class, DisplayLcd20x4::new);
    registry.put(BrickletSegmentDisplay4x7.class, DisplaySegment::new);
    registry.put(BrickletDistanceIR.class, DistanceIR::new);
    registry.put(BrickletDistanceIRV2.class, DistanceIRV2::new);
    registry.put(BrickletDistanceUS.class, DistanceUS::new);
    registry.put(BrickletHumidity.class, Humidity::new);
    registry.put(BrickletHumidityV2.class, HumidityV2::new);
    registry.put(BrickIMU.class, IMU::new);
    registry.put(BrickIMUV2.class, IMU2::new);
    registry.put(BrickletIO16.class, IO16::new);
    registry.put(BrickletIO16V2.class, IO16V2::new);
    registry.put(BrickletAmbientLight.class, LightAmbient::new);
    registry.put(BrickletAmbientLightV2.class, LightAmbientV2::new);
    registry.put(BrickletAmbientLightV3.class, LightAmbientV3::new);
    registry.put(BrickletColor.class, LightColor::new);
    registry.put(BrickletUVLight.class, LightUv::new);
    registry.put(BrickletUVLightV2.class, LightUvV2::new);
    registry.put(BrickMaster.class, Master::new);
    registry.put(BrickletMotionDetector.class, MotionDetector::new);
    registry.put(BrickletMotionDetectorV2.class, MotionDetectorV2::new);
    registry.put(BrickletRotaryEncoderV2.class, RotaryV2::new);
    registry.put(BrickletSoundIntensity.class, SoundIntensity::new);
    registry.put(BrickletPiezoSpeaker.class, Speaker::new);
    registry.put(BrickletTemperature.class, Temperature::new);
    registry.put(BrickletTemperatureV2.class, TemperatureV2::new);
    registry.put(BrickletTilt.class, Tilt::new);
    return registry;
  }

  private static ConcurrentHashMap<Integer, Sensor.DeviceFactory> initDevice() {
    ConcurrentHashMap<Integer, Sensor.DeviceFactory> registry = new ConcurrentHashMap<>();
    registry.put(BrickletAirQuality.DEVICE_IDENTIFIER, BrickletAirQuality::new);
    registry.put(BrickletBarometer.DEVICE_IDENTIFIER, BrickletBarometer::new);
    registry.put(BrickletBarometerV2.DEVICE_IDENTIFIER, BrickletBarometerV2::new);
    registry.put(BrickletRGBLEDButton.DEVICE_IDENTIFIER, BrickletRGBLEDButton::new);
    registry.put(BrickDC.DEVICE_IDENTIFIER, BrickDC::new);
    registry.put(DummyDevice.DEVICE_IDENTIFIER, DummyDevice::new);
    registry.put(BrickletLCD20x4.DEVICE_IDENTIFIER, BrickletLCD20x4::new);
    registry.put(BrickletSegmentDisplay4x7.DEVICE_IDENTIFIER, BrickletSegmentDisplay4x7::new);
    registry.put(BrickletDistanceIR.DEVICE_IDENTIFIER, BrickletDistanceIR::new);
    registry.put(BrickletDistanceIRV2.DEVICE_IDENTIFIER, BrickletDistanceIRV2::new);
    registry.put(BrickletDistanceUS.DEVICE_IDENTIFIER, BrickletDistanceUS::new);
    registry.put(BrickletHumidity.DEVICE_IDENTIFIER, BrickletHumidity::new);
    registry.put(BrickletHumidityV2.DEVICE_IDENTIFIER, BrickletHumidityV2::new);
    registry.put(BrickIMU.DEVICE_IDENTIFIER, BrickIMU::new);
    registry.put(BrickIMUV2.DEVICE_IDENTIFIER, BrickIMUV2::new);
    registry.put(BrickletIO16.DEVICE_IDENTIFIER, BrickletIO16::new);
    registry.put(BrickletIO16V2.DEVICE_IDENTIFIER, BrickletIO16V2::new);
    registry.put(BrickletAmbientLight.DEVICE_IDENTIFIER, BrickletAmbientLight::new);
    registry.put(BrickletAmbientLightV2.DEVICE_IDENTIFIER, BrickletAmbientLightV2::new);
    registry.put(BrickletAmbientLightV3.DEVICE_IDENTIFIER, BrickletAmbientLightV3::new);
    registry.put(BrickletColor.DEVICE_IDENTIFIER, BrickletColor::new);
    registry.put(BrickletUVLight.DEVICE_IDENTIFIER, BrickletUVLight::new);
    registry.put(BrickletUVLightV2.DEVICE_IDENTIFIER, BrickletUVLightV2::new);
    registry.put(BrickMaster.DEVICE_IDENTIFIER, BrickMaster::new);
    registry.put(BrickletMotionDetector.DEVICE_IDENTIFIER, BrickletMotionDetector::new);
    registry.put(BrickletMotionDetectorV2.DEVICE_IDENTIFIER, BrickletMotionDetectorV2::new);
    registry.put(BrickletRotaryEncoderV2.DEVICE_IDENTIFIER, BrickletRotaryEncoderV2::new);
    registry.put(BrickletSoundIntensity.DEVICE_IDENTIFIER, BrickletSoundIntensity::new);
    registry.put(BrickletPiezoSpeaker.DEVICE_IDENTIFIER, BrickletPiezoSpeaker::new);
    registry.put(BrickletTemperature.DEVICE_IDENTIFIER, BrickletTemperature::new);
    registry.put(BrickletTemperatureV2.DEVICE_IDENTIFIER, BrickletTemperatureV2::new);
    registry.put(BrickletTilt.DEVICE_IDENTIFIER, BrickletTilt::new);
    return registry;
  }
}
