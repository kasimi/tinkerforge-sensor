package berlin.yuna.tinkerforgesensor.generator;

import berlin.yuna.tinkerforgesensor.model.sensor.bricklet.Sensor;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GeneratorHelper {

    public static List<Class<? extends Sensor>> getSensorVersions(final Class<? extends Sensor> sensor, final List<Class<? extends Sensor>> sensorList) {
        final String className = sensor.getSimpleName();
        final String packageName = sensor.getPackage().getName();
        final String name = getBasicSensorName(className);
        final List<Class<? extends Sensor>> sensorVersionList = sensorList.stream()
                .filter(sensorClass -> packageName.equals(sensorClass.getPackage().getName()))
                .filter(sensorClass -> sensorClass.getSimpleName().startsWith(name))
                .filter(sensorClass -> sensorClass.getSimpleName().length() < name.length() + 3).sorted(Comparator.comparing(Class::getSimpleName)).collect(Collectors.toList());
        Collections.reverse(sensorVersionList);
        return sensorVersionList;
    }

    public static String getBasicSensorName(final String className) {
        return className.charAt(className.length() - 2) == 'V' ? className.substring(0, className.length() - 2) : className;
    }

    public static String firstLetterLow(final String input) {
        return input.substring(0, 1).toLowerCase() + input.substring(1);
    }
}
