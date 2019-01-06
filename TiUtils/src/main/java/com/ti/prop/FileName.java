package com.ti.prop;

import java.nio.file.Path;
import java.nio.file.Paths;

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



}
