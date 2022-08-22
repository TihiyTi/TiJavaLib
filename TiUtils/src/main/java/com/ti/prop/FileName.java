package com.ti.prop;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//TODO 30.12.2018 Alexey: extract to TiJavaLib
public class FileName {

    /**
     * Возвращает путь к файлу properties в зависимости от окружения где была запущена программа.
     * Если запуск из Java classes - путь к корню проекта
     * Если запуск из JAR - путь к запускаемому JAR - файлу
     * @param propertyFileName имя файла property (без разширения .properties)
     * @param clazz класс относительно которого определяется путь
     * @return Path путь файла properties
     * @apiNote функция не тестировала для модульной структуры
    */
    public static Path getPropertyPath(String propertyFileName, Class clazz){
        if(isRunFromJar(clazz)){
            String s = clazz.getProtectionDomain().getCodeSource().getLocation().getPath().replaceAll("%20"," ").substring(1);
            Path parent = Paths.get(s).getParent();
            return Paths.get(parent +"/"+ propertyFileName+".properties");
        }else {
            return Paths.get(propertyFileName+".properties");
        }
    }

    public static Path getAbsolutePath(String fileName, Class clazz){
        if(isRunFromJar(clazz)){
            String s = clazz.getProtectionDomain().getCodeSource().getLocation().getPath().replaceAll("%20"," ").substring(1);
            Path parent = Paths.get(s).getParent();
            return Paths.get(parent +"/"+ fileName);
        }else {
            return Paths.get(fileName);
        }
    }

    private static boolean isRunFromJar(){
        Path path = Paths.get(FileName.class.getProtectionDomain().getCodeSource().getLocation().getPath());
//        LOG.info("Execute path: " + path);
        return path.endsWith(".jar");
    }
    private static boolean isRunFromJar(Class clazz){
        String path = FileName.class.getProtectionDomain().getCodeSource().getLocation().getPath();
//        LOG.info("Execute path: " + path);
        return path.endsWith(".jar");
    }

    public static List<Path> getFilesInDir(String defPath) throws IOException {
        List<Path> paths = Files.walk(Paths.get(defPath)).filter(Files::isRegularFile)
                    .collect(Collectors.toList());

//        paths.forEach(System.out::println);
        return paths;
    }

}
