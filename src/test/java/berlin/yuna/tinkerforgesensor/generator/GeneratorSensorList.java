package berlin.yuna.tinkerforgesensor.generator;


import berlin.yuna.tinkerforgesensor.model.SensorList;
import berlin.yuna.tinkerforgesensor.model.SensorListBasic;
import berlin.yuna.tinkerforgesensor.model.sensor.bricklet.Sensor;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static javax.lang.model.element.Modifier.PUBLIC;

public class GeneratorSensorList {

    private static final String SUPER_CLASS_NAME = Sensor.class.getSimpleName();

    public static JavaFile generate(final List<Class<? extends Sensor>> sensorList) {
        StackTraceElement trace = Thread.currentThread().getStackTrace()[1];
        String autogenerated = "Autogenerated with [" + trace.getFileName().replace(".java", "") + ":" + trace.getMethodName() + "]";
        Collections.reverse(sensorList);

        //CREATE CLASS
        TypeSpec.Builder sensorListClass = TypeSpec.classBuilder(SensorList.class.getSimpleName())
                .addModifiers(PUBLIC)
                .addTypeVariable(TypeVariableName.get("T", Sensor.class))
                .superclass(ParameterizedTypeName.get(ClassName.get(SensorListBasic.class), TypeVariableName.get("T")))
                .addJavadoc("$N\n", autogenerated);

        //CREATE METHODS
        while (!sensorList.isEmpty()) {
            Class<? extends Sensor> sensor = sensorList.iterator().next();
            String sensorName = sensor.getSimpleName();
            String packageName = sensorList.get(0).getPackage().getName();
            List<Class<? extends Sensor>> sensorVersions = getSensorVersions(sensorList, sensorName, packageName);

            MethodSpec.Builder sensorMethod = MethodSpec.methodBuilder("get" + getBasicSensorName(sensorName))
                    .addModifiers(PUBLIC)
                    .returns(Sensor.class);
            sensorListClass.addMethod(generateMethodContent(sensorVersions, sensorMethod).build());
            //REMOVE SENSORS VARIANTS FROM LIST
            for (Class<? extends Sensor> sensorVersion : sensorVersions) {
                sensorList.remove(sensorVersion);
            }
        }

        //Path sourceFile = getSourceFile(packageName, className);
        return JavaFile.builder(SensorList.class.getPackage().getName(), sensorListClass.build()).build();
    }

    private static MethodSpec.Builder generateMethodContent(final List<Class<? extends Sensor>> sensorVersions, final MethodSpec.Builder methodBuilder) {
        String superClassNameLow = firstLetterLow(SUPER_CLASS_NAME);
        Iterator<Class<? extends Sensor>> versionIterator = sensorVersions.iterator();

        if (sensorVersions.size() == 1) {
            methodBuilder.addStatement("return first($T.class)", next(versionIterator));
        } else {
            methodBuilder.addStatement("$N $N = first($T.class)", SUPER_CLASS_NAME, superClassNameLow, next(versionIterator));
            while (versionIterator.hasNext()) {
                ClassName className = next(versionIterator);
                if (versionIterator.hasNext()) {
                    methodBuilder.addStatement("$N = $N.isPresent() ? $N : first($T.class)", superClassNameLow, superClassNameLow, superClassNameLow, className);
                } else {
                    methodBuilder.addStatement("return $N.isPresent() ? $N : first($T.class)", superClassNameLow, superClassNameLow, className);
                }
            }
        }
        return methodBuilder;
    }

    private static ClassName next(Iterator<Class<? extends Sensor>> versionIterator) {
        Class<? extends Sensor> next = versionIterator.next();
        return ClassName.get(next.getPackage().getName(), next.getSimpleName());
    }

    private static List<Class<? extends Sensor>> getSensorVersions(final List<Class<? extends Sensor>> sensorList, final String
            className, final String packageName) {
        String name = getBasicSensorName(className);
        List<Class<? extends Sensor>> sensorVersionList = sensorList.stream()
                .filter(sensorClass -> packageName.equals(sensorClass.getPackage().getName()))
                .filter(sensorClass -> sensorClass.getSimpleName().startsWith(name))
                .filter(sensorClass -> sensorClass.getSimpleName().length() < name.length() + 3).sorted(Comparator.comparing(Class::getSimpleName)).collect(Collectors.toList());
        Collections.reverse(sensorVersionList);
        return sensorVersionList;
    }

    private static String getBasicSensorName(final String className) {
        return className.charAt(className.length() - 2) == 'V' ? className.substring(0, className.length() - 2) : className;
    }

    private static String firstLetterLow(final String input) {
        return input.substring(0, 1).toLowerCase() + input.substring(1);
    }
}