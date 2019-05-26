package berlin.yuna.tinkerforgesensor.model.type;

/**
 * Autogenerated with [GeneratorEnumValueType:generate]
 *  You can add new enumTypes without harm
 */
public enum ValueType {
  ALL(null),

  DUMMY(ALL),

  PING(ALL),

  DEVICE_STATUS(ALL),

  DEVICE_CONNECTED(DEVICE_STATUS),

  DEVICE_RECONNECTED(DEVICE_STATUS),

  DEVICE_DISCONNECTED(DEVICE_STATUS),

  DEVICE_TIMEOUT(DEVICE_STATUS),

  DEVICE_UNKNOWN(DEVICE_STATUS),

  EMERGENCY_SHUTDOWN(DEVICE_STATUS),

  DEVICE_SEARCH(DEVICE_STATUS),

  DEVICE_ALREADY_CONNECTED(DEVICE_STATUS),

  ENVIRONMENT(ALL),

  AIR_PRESSURE(ENVIRONMENT),

  IAQ_INDEX(ENVIRONMENT),

  HUMIDITY(ENVIRONMENT),

  TEMPERATURE(ENVIRONMENT),

  ALTITUDE(ENVIRONMENT),

  TILT(ENVIRONMENT),

  MOTION_DETECTED(ENVIRONMENT),

  DISTANCE(ALL),

  SOUND(ALL),

  SOUND_SPECTRUM_LENGTH(SOUND),

  SOUND_SPECTRUM_OFFSET(SOUND),

  BEEP_ACTIVE(SOUND),

  SOUND_INTENSITY(SOUND),

  TOUCH(ALL),

  TOUCH_1(TOUCH),

  TOUCH_2(TOUCH),

  TOUCH_3(TOUCH),

  TOUCH_4(TOUCH),

  TOUCH_5(TOUCH),

  TOUCH_6(TOUCH),

  TOUCH_7(TOUCH),

  TOUCH_8(TOUCH),

  TOUCH_9(TOUCH),

  TOUCH_10(TOUCH),

  TOUCH_11(TOUCH),

  TOUCH_12(TOUCH),

  TOUCH_PROXIMITY(TOUCH),

  LIGHT(ALL),

  LIGHT_UV(LIGHT),

  LIGHT_UVA(LIGHT),

  LIGHT_UVB(LIGHT),

  LIGHT_LUX(LIGHT),

  COLOR(ALL),

  COLOR_R(COLOR),

  COLOR_G(COLOR),

  COLOR_B(COLOR),

  COLOR_C(COLOR),

  COLOR_RGB(COLOR),

  COLOR_LUX(COLOR),

  COLOR_TEMPERATURE(COLOR),

  IMU(ALL),

  CALIBRATION(IMU),

  ORIENTATION_HEADING(IMU),

  ORIENTATION_ROLL(IMU),

  ORIENTATION_PITCH(IMU),

  ACCELERATION_X(IMU),

  ACCELERATION_Y(IMU),

  ACCELERATION_Z(IMU),

  MAGNETIC_X(IMU),

  MAGNETIC_Y(IMU),

  MAGNETIC_Z(IMU),

  ANGULAR_VELOCITY_X(IMU),

  ANGULAR_VELOCITY_Y(IMU),

  ANGULAR_VELOCITY_Z(IMU),

  EULER_ANGLE_X(IMU),

  EULER_ANGLE_Y(IMU),

  EULER_ANGLE_Z(IMU),

  QUATERNION_W(IMU),

  QUATERNION_X(IMU),

  QUATERNION_Y(IMU),

  QUATERNION_Z(IMU),

  LINEAR_ACCELERATION_X(IMU),

  LINEAR_ACCELERATION_Y(IMU),

  LINEAR_ACCELERATION_Z(IMU),

  GRAVITY_VECTOR_X(IMU),

  GRAVITY_VECTOR_Y(IMU),

  GRAVITY_VECTOR_Z(IMU),

  ENERGY(ALL),

  POWER(ALL),

  VOLTAGE_USB(ENERGY),

  VOLTAGE(ENERGY),

  CURRENT(ENERGY),

  BUTTON(ALL),

  BUTTON_PRESSED(BUTTON),

  ROTARY(BUTTON);

  public final ValueType parent;

  ValueType(final ValueType parent) {
    this.parent = parent;
  }

  public boolean isAll() {
    return this == ALL;
  }

  public boolean containsAll() {
    return this.is(ALL) || (this.parent != null && this.parent.contains(ALL));
  }

  public boolean isDummy() {
    return this == DUMMY;
  }

  public boolean containsDummy() {
    return this.is(DUMMY) || (this.parent != null && this.parent.contains(DUMMY));
  }

  public boolean isPing() {
    return this == PING;
  }

  public boolean containsPing() {
    return this.is(PING) || (this.parent != null && this.parent.contains(PING));
  }

  public boolean isDeviceStatus() {
    return this == DEVICE_STATUS;
  }

  public boolean containsDeviceStatus() {
    return this.is(DEVICE_STATUS) || (this.parent != null && this.parent.contains(DEVICE_STATUS));
  }

  public boolean isDeviceConnected() {
    return this == DEVICE_CONNECTED;
  }

  public boolean containsDeviceConnected() {
    return this.is(DEVICE_CONNECTED) || (this.parent != null && this.parent.contains(DEVICE_CONNECTED));
  }

  public boolean isDeviceReconnected() {
    return this == DEVICE_RECONNECTED;
  }

  public boolean containsDeviceReconnected() {
    return this.is(DEVICE_RECONNECTED) || (this.parent != null && this.parent.contains(DEVICE_RECONNECTED));
  }

  public boolean isDeviceDisconnected() {
    return this == DEVICE_DISCONNECTED;
  }

  public boolean containsDeviceDisconnected() {
    return this.is(DEVICE_DISCONNECTED) || (this.parent != null && this.parent.contains(DEVICE_DISCONNECTED));
  }

  public boolean isDeviceTimeout() {
    return this == DEVICE_TIMEOUT;
  }

  public boolean containsDeviceTimeout() {
    return this.is(DEVICE_TIMEOUT) || (this.parent != null && this.parent.contains(DEVICE_TIMEOUT));
  }

  public boolean isDeviceUnknown() {
    return this == DEVICE_UNKNOWN;
  }

  public boolean containsDeviceUnknown() {
    return this.is(DEVICE_UNKNOWN) || (this.parent != null && this.parent.contains(DEVICE_UNKNOWN));
  }

  public boolean isEmergencyShutdown() {
    return this == EMERGENCY_SHUTDOWN;
  }

  public boolean containsEmergencyShutdown() {
    return this.is(EMERGENCY_SHUTDOWN) || (this.parent != null && this.parent.contains(EMERGENCY_SHUTDOWN));
  }

  public boolean isDeviceSearch() {
    return this == DEVICE_SEARCH;
  }

  public boolean containsDeviceSearch() {
    return this.is(DEVICE_SEARCH) || (this.parent != null && this.parent.contains(DEVICE_SEARCH));
  }

  public boolean isDeviceAlreadyConnected() {
    return this == DEVICE_ALREADY_CONNECTED;
  }

  public boolean containsDeviceAlreadyConnected() {
    return this.is(DEVICE_ALREADY_CONNECTED) || (this.parent != null && this.parent.contains(DEVICE_ALREADY_CONNECTED));
  }

  public boolean isEnvironment() {
    return this == ENVIRONMENT;
  }

  public boolean containsEnvironment() {
    return this.is(ENVIRONMENT) || (this.parent != null && this.parent.contains(ENVIRONMENT));
  }

  public boolean isAirPressure() {
    return this == AIR_PRESSURE;
  }

  public boolean containsAirPressure() {
    return this.is(AIR_PRESSURE) || (this.parent != null && this.parent.contains(AIR_PRESSURE));
  }

  public boolean isIaqIndex() {
    return this == IAQ_INDEX;
  }

  public boolean containsIaqIndex() {
    return this.is(IAQ_INDEX) || (this.parent != null && this.parent.contains(IAQ_INDEX));
  }

  public boolean isHumidity() {
    return this == HUMIDITY;
  }

  public boolean containsHumidity() {
    return this.is(HUMIDITY) || (this.parent != null && this.parent.contains(HUMIDITY));
  }

  public boolean isTemperature() {
    return this == TEMPERATURE;
  }

  public boolean containsTemperature() {
    return this.is(TEMPERATURE) || (this.parent != null && this.parent.contains(TEMPERATURE));
  }

  public boolean isAltitude() {
    return this == ALTITUDE;
  }

  public boolean containsAltitude() {
    return this.is(ALTITUDE) || (this.parent != null && this.parent.contains(ALTITUDE));
  }

  public boolean isTilt() {
    return this == TILT;
  }

  public boolean containsTilt() {
    return this.is(TILT) || (this.parent != null && this.parent.contains(TILT));
  }

  public boolean isMotionDetected() {
    return this == MOTION_DETECTED;
  }

  public boolean containsMotionDetected() {
    return this.is(MOTION_DETECTED) || (this.parent != null && this.parent.contains(MOTION_DETECTED));
  }

  public boolean isDistance() {
    return this == DISTANCE;
  }

  public boolean containsDistance() {
    return this.is(DISTANCE) || (this.parent != null && this.parent.contains(DISTANCE));
  }

  public boolean isSound() {
    return this == SOUND;
  }

  public boolean containsSound() {
    return this.is(SOUND) || (this.parent != null && this.parent.contains(SOUND));
  }

  public boolean isSoundSpectrumLength() {
    return this == SOUND_SPECTRUM_LENGTH;
  }

  public boolean containsSoundSpectrumLength() {
    return this.is(SOUND_SPECTRUM_LENGTH) || (this.parent != null && this.parent.contains(SOUND_SPECTRUM_LENGTH));
  }

  public boolean isSoundSpectrumOffset() {
    return this == SOUND_SPECTRUM_OFFSET;
  }

  public boolean containsSoundSpectrumOffset() {
    return this.is(SOUND_SPECTRUM_OFFSET) || (this.parent != null && this.parent.contains(SOUND_SPECTRUM_OFFSET));
  }

  public boolean isBeepActive() {
    return this == BEEP_ACTIVE;
  }

  public boolean containsBeepActive() {
    return this.is(BEEP_ACTIVE) || (this.parent != null && this.parent.contains(BEEP_ACTIVE));
  }

  public boolean isSoundIntensity() {
    return this == SOUND_INTENSITY;
  }

  public boolean containsSoundIntensity() {
    return this.is(SOUND_INTENSITY) || (this.parent != null && this.parent.contains(SOUND_INTENSITY));
  }

  public boolean isTouch() {
    return this == TOUCH;
  }

  public boolean containsTouch() {
    return this.is(TOUCH) || (this.parent != null && this.parent.contains(TOUCH));
  }

  public boolean isTouch1() {
    return this == TOUCH_1;
  }

  public boolean containsTouch1() {
    return this.is(TOUCH_1) || (this.parent != null && this.parent.contains(TOUCH_1));
  }

  public boolean isTouch2() {
    return this == TOUCH_2;
  }

  public boolean containsTouch2() {
    return this.is(TOUCH_2) || (this.parent != null && this.parent.contains(TOUCH_2));
  }

  public boolean isTouch3() {
    return this == TOUCH_3;
  }

  public boolean containsTouch3() {
    return this.is(TOUCH_3) || (this.parent != null && this.parent.contains(TOUCH_3));
  }

  public boolean isTouch4() {
    return this == TOUCH_4;
  }

  public boolean containsTouch4() {
    return this.is(TOUCH_4) || (this.parent != null && this.parent.contains(TOUCH_4));
  }

  public boolean isTouch5() {
    return this == TOUCH_5;
  }

  public boolean containsTouch5() {
    return this.is(TOUCH_5) || (this.parent != null && this.parent.contains(TOUCH_5));
  }

  public boolean isTouch6() {
    return this == TOUCH_6;
  }

  public boolean containsTouch6() {
    return this.is(TOUCH_6) || (this.parent != null && this.parent.contains(TOUCH_6));
  }

  public boolean isTouch7() {
    return this == TOUCH_7;
  }

  public boolean containsTouch7() {
    return this.is(TOUCH_7) || (this.parent != null && this.parent.contains(TOUCH_7));
  }

  public boolean isTouch8() {
    return this == TOUCH_8;
  }

  public boolean containsTouch8() {
    return this.is(TOUCH_8) || (this.parent != null && this.parent.contains(TOUCH_8));
  }

  public boolean isTouch9() {
    return this == TOUCH_9;
  }

  public boolean containsTouch9() {
    return this.is(TOUCH_9) || (this.parent != null && this.parent.contains(TOUCH_9));
  }

  public boolean isTouch10() {
    return this == TOUCH_10;
  }

  public boolean containsTouch10() {
    return this.is(TOUCH_10) || (this.parent != null && this.parent.contains(TOUCH_10));
  }

  public boolean isTouch11() {
    return this == TOUCH_11;
  }

  public boolean containsTouch11() {
    return this.is(TOUCH_11) || (this.parent != null && this.parent.contains(TOUCH_11));
  }

  public boolean isTouch12() {
    return this == TOUCH_12;
  }

  public boolean containsTouch12() {
    return this.is(TOUCH_12) || (this.parent != null && this.parent.contains(TOUCH_12));
  }

  public boolean isTouchProximity() {
    return this == TOUCH_PROXIMITY;
  }

  public boolean containsTouchProximity() {
    return this.is(TOUCH_PROXIMITY) || (this.parent != null && this.parent.contains(TOUCH_PROXIMITY));
  }

  public boolean isLight() {
    return this == LIGHT;
  }

  public boolean containsLight() {
    return this.is(LIGHT) || (this.parent != null && this.parent.contains(LIGHT));
  }

  public boolean isLightUv() {
    return this == LIGHT_UV;
  }

  public boolean containsLightUv() {
    return this.is(LIGHT_UV) || (this.parent != null && this.parent.contains(LIGHT_UV));
  }

  public boolean isLightUva() {
    return this == LIGHT_UVA;
  }

  public boolean containsLightUva() {
    return this.is(LIGHT_UVA) || (this.parent != null && this.parent.contains(LIGHT_UVA));
  }

  public boolean isLightUvb() {
    return this == LIGHT_UVB;
  }

  public boolean containsLightUvb() {
    return this.is(LIGHT_UVB) || (this.parent != null && this.parent.contains(LIGHT_UVB));
  }

  public boolean isLightLux() {
    return this == LIGHT_LUX;
  }

  public boolean containsLightLux() {
    return this.is(LIGHT_LUX) || (this.parent != null && this.parent.contains(LIGHT_LUX));
  }

  public boolean isColor() {
    return this == COLOR;
  }

  public boolean containsColor() {
    return this.is(COLOR) || (this.parent != null && this.parent.contains(COLOR));
  }

  public boolean isColorR() {
    return this == COLOR_R;
  }

  public boolean containsColorR() {
    return this.is(COLOR_R) || (this.parent != null && this.parent.contains(COLOR_R));
  }

  public boolean isColorG() {
    return this == COLOR_G;
  }

  public boolean containsColorG() {
    return this.is(COLOR_G) || (this.parent != null && this.parent.contains(COLOR_G));
  }

  public boolean isColorB() {
    return this == COLOR_B;
  }

  public boolean containsColorB() {
    return this.is(COLOR_B) || (this.parent != null && this.parent.contains(COLOR_B));
  }

  public boolean isColorC() {
    return this == COLOR_C;
  }

  public boolean containsColorC() {
    return this.is(COLOR_C) || (this.parent != null && this.parent.contains(COLOR_C));
  }

  public boolean isColorRgb() {
    return this == COLOR_RGB;
  }

  public boolean containsColorRgb() {
    return this.is(COLOR_RGB) || (this.parent != null && this.parent.contains(COLOR_RGB));
  }

  public boolean isColorLux() {
    return this == COLOR_LUX;
  }

  public boolean containsColorLux() {
    return this.is(COLOR_LUX) || (this.parent != null && this.parent.contains(COLOR_LUX));
  }

  public boolean isColorTemperature() {
    return this == COLOR_TEMPERATURE;
  }

  public boolean containsColorTemperature() {
    return this.is(COLOR_TEMPERATURE) || (this.parent != null && this.parent.contains(COLOR_TEMPERATURE));
  }

  public boolean isImu() {
    return this == IMU;
  }

  public boolean containsImu() {
    return this.is(IMU) || (this.parent != null && this.parent.contains(IMU));
  }

  public boolean isCalibration() {
    return this == CALIBRATION;
  }

  public boolean containsCalibration() {
    return this.is(CALIBRATION) || (this.parent != null && this.parent.contains(CALIBRATION));
  }

  public boolean isOrientationHeading() {
    return this == ORIENTATION_HEADING;
  }

  public boolean containsOrientationHeading() {
    return this.is(ORIENTATION_HEADING) || (this.parent != null && this.parent.contains(ORIENTATION_HEADING));
  }

  public boolean isOrientationRoll() {
    return this == ORIENTATION_ROLL;
  }

  public boolean containsOrientationRoll() {
    return this.is(ORIENTATION_ROLL) || (this.parent != null && this.parent.contains(ORIENTATION_ROLL));
  }

  public boolean isOrientationPitch() {
    return this == ORIENTATION_PITCH;
  }

  public boolean containsOrientationPitch() {
    return this.is(ORIENTATION_PITCH) || (this.parent != null && this.parent.contains(ORIENTATION_PITCH));
  }

  public boolean isAccelerationX() {
    return this == ACCELERATION_X;
  }

  public boolean containsAccelerationX() {
    return this.is(ACCELERATION_X) || (this.parent != null && this.parent.contains(ACCELERATION_X));
  }

  public boolean isAccelerationY() {
    return this == ACCELERATION_Y;
  }

  public boolean containsAccelerationY() {
    return this.is(ACCELERATION_Y) || (this.parent != null && this.parent.contains(ACCELERATION_Y));
  }

  public boolean isAccelerationZ() {
    return this == ACCELERATION_Z;
  }

  public boolean containsAccelerationZ() {
    return this.is(ACCELERATION_Z) || (this.parent != null && this.parent.contains(ACCELERATION_Z));
  }

  public boolean isMagneticX() {
    return this == MAGNETIC_X;
  }

  public boolean containsMagneticX() {
    return this.is(MAGNETIC_X) || (this.parent != null && this.parent.contains(MAGNETIC_X));
  }

  public boolean isMagneticY() {
    return this == MAGNETIC_Y;
  }

  public boolean containsMagneticY() {
    return this.is(MAGNETIC_Y) || (this.parent != null && this.parent.contains(MAGNETIC_Y));
  }

  public boolean isMagneticZ() {
    return this == MAGNETIC_Z;
  }

  public boolean containsMagneticZ() {
    return this.is(MAGNETIC_Z) || (this.parent != null && this.parent.contains(MAGNETIC_Z));
  }

  public boolean isAngularVelocityX() {
    return this == ANGULAR_VELOCITY_X;
  }

  public boolean containsAngularVelocityX() {
    return this.is(ANGULAR_VELOCITY_X) || (this.parent != null && this.parent.contains(ANGULAR_VELOCITY_X));
  }

  public boolean isAngularVelocityY() {
    return this == ANGULAR_VELOCITY_Y;
  }

  public boolean containsAngularVelocityY() {
    return this.is(ANGULAR_VELOCITY_Y) || (this.parent != null && this.parent.contains(ANGULAR_VELOCITY_Y));
  }

  public boolean isAngularVelocityZ() {
    return this == ANGULAR_VELOCITY_Z;
  }

  public boolean containsAngularVelocityZ() {
    return this.is(ANGULAR_VELOCITY_Z) || (this.parent != null && this.parent.contains(ANGULAR_VELOCITY_Z));
  }

  public boolean isEulerAngleX() {
    return this == EULER_ANGLE_X;
  }

  public boolean containsEulerAngleX() {
    return this.is(EULER_ANGLE_X) || (this.parent != null && this.parent.contains(EULER_ANGLE_X));
  }

  public boolean isEulerAngleY() {
    return this == EULER_ANGLE_Y;
  }

  public boolean containsEulerAngleY() {
    return this.is(EULER_ANGLE_Y) || (this.parent != null && this.parent.contains(EULER_ANGLE_Y));
  }

  public boolean isEulerAngleZ() {
    return this == EULER_ANGLE_Z;
  }

  public boolean containsEulerAngleZ() {
    return this.is(EULER_ANGLE_Z) || (this.parent != null && this.parent.contains(EULER_ANGLE_Z));
  }

  public boolean isQuaternionW() {
    return this == QUATERNION_W;
  }

  public boolean containsQuaternionW() {
    return this.is(QUATERNION_W) || (this.parent != null && this.parent.contains(QUATERNION_W));
  }

  public boolean isQuaternionX() {
    return this == QUATERNION_X;
  }

  public boolean containsQuaternionX() {
    return this.is(QUATERNION_X) || (this.parent != null && this.parent.contains(QUATERNION_X));
  }

  public boolean isQuaternionY() {
    return this == QUATERNION_Y;
  }

  public boolean containsQuaternionY() {
    return this.is(QUATERNION_Y) || (this.parent != null && this.parent.contains(QUATERNION_Y));
  }

  public boolean isQuaternionZ() {
    return this == QUATERNION_Z;
  }

  public boolean containsQuaternionZ() {
    return this.is(QUATERNION_Z) || (this.parent != null && this.parent.contains(QUATERNION_Z));
  }

  public boolean isLinearAccelerationX() {
    return this == LINEAR_ACCELERATION_X;
  }

  public boolean containsLinearAccelerationX() {
    return this.is(LINEAR_ACCELERATION_X) || (this.parent != null && this.parent.contains(LINEAR_ACCELERATION_X));
  }

  public boolean isLinearAccelerationY() {
    return this == LINEAR_ACCELERATION_Y;
  }

  public boolean containsLinearAccelerationY() {
    return this.is(LINEAR_ACCELERATION_Y) || (this.parent != null && this.parent.contains(LINEAR_ACCELERATION_Y));
  }

  public boolean isLinearAccelerationZ() {
    return this == LINEAR_ACCELERATION_Z;
  }

  public boolean containsLinearAccelerationZ() {
    return this.is(LINEAR_ACCELERATION_Z) || (this.parent != null && this.parent.contains(LINEAR_ACCELERATION_Z));
  }

  public boolean isGravityVectorX() {
    return this == GRAVITY_VECTOR_X;
  }

  public boolean containsGravityVectorX() {
    return this.is(GRAVITY_VECTOR_X) || (this.parent != null && this.parent.contains(GRAVITY_VECTOR_X));
  }

  public boolean isGravityVectorY() {
    return this == GRAVITY_VECTOR_Y;
  }

  public boolean containsGravityVectorY() {
    return this.is(GRAVITY_VECTOR_Y) || (this.parent != null && this.parent.contains(GRAVITY_VECTOR_Y));
  }

  public boolean isGravityVectorZ() {
    return this == GRAVITY_VECTOR_Z;
  }

  public boolean containsGravityVectorZ() {
    return this.is(GRAVITY_VECTOR_Z) || (this.parent != null && this.parent.contains(GRAVITY_VECTOR_Z));
  }

  public boolean isEnergy() {
    return this == ENERGY;
  }

  public boolean containsEnergy() {
    return this.is(ENERGY) || (this.parent != null && this.parent.contains(ENERGY));
  }

  public boolean isPower() {
    return this == POWER;
  }

  public boolean containsPower() {
    return this.is(POWER) || (this.parent != null && this.parent.contains(POWER));
  }

  public boolean isVoltageUsb() {
    return this == VOLTAGE_USB;
  }

  public boolean containsVoltageUsb() {
    return this.is(VOLTAGE_USB) || (this.parent != null && this.parent.contains(VOLTAGE_USB));
  }

  public boolean isVoltage() {
    return this == VOLTAGE;
  }

  public boolean containsVoltage() {
    return this.is(VOLTAGE) || (this.parent != null && this.parent.contains(VOLTAGE));
  }

  public boolean isCurrent() {
    return this == CURRENT;
  }

  public boolean containsCurrent() {
    return this.is(CURRENT) || (this.parent != null && this.parent.contains(CURRENT));
  }

  public boolean isButton() {
    return this == BUTTON;
  }

  public boolean containsButton() {
    return this.is(BUTTON) || (this.parent != null && this.parent.contains(BUTTON));
  }

  public boolean isButtonPressed() {
    return this == BUTTON_PRESSED;
  }

  public boolean containsButtonPressed() {
    return this.is(BUTTON_PRESSED) || (this.parent != null && this.parent.contains(BUTTON_PRESSED));
  }

  public boolean isRotary() {
    return this == ROTARY;
  }

  public boolean containsRotary() {
    return this.is(ROTARY) || (this.parent != null && this.parent.contains(ROTARY));
  }

  public boolean is(final ValueType valueType) {
    return this == valueType;
  }

  public boolean contains(final ValueType valueType) {
    return this.is(valueType) || (this.parent != null && this.parent.contains(valueType));
  }
}
