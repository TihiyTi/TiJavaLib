package com.ti.prop;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.util.Properties;


//TODO 30.12.2018 Alexey: extract to TiJavaLib
public class NewPropertyService {
    private static Path mainPropertyPath;

    public static void setGlobalPropertyPath(Path path){
        mainPropertyPath = path;
    }

    public static String getGlobalProperty(String key){
        FileInputStream file;
        try {
            file = new FileInputStream(mainPropertyPath.toFile());
            Properties mainProperties = new Properties();
            mainProperties.load(file);
            file.close();
            return mainProperties.getProperty(key);
        } catch (java.io.IOException e) {
//            LOG.info("File "+ mainPropertyFileName+ "  not found at "+ path);
            e.printStackTrace();
        }
        return null;
    }
    public static String getGlobalProperty(String key, String propertyFileName){
        setGlobalPropertyPath(FileName.getPropertyPath(propertyFileName, NewPropertyService.class));
        return getGlobalProperty(key);
    }

}
