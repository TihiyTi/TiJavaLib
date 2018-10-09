package com.ti;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FileServiceSingleFactory {

    private boolean dateAppend = false;

    public FileService newFileService(String name){
        return newFileService(name, "");
    }

    public FileService newFileService(String name, String pathToFile){
        String nameWithDate = pathToFile + getCurrentDate()+ " " + name;
        return new FileService(nameWithDate);
    }

    public void appendDateToFileName(){
        dateAppend = true;
    }

    private String getCurrentDate(){
        String date = "";
        if(dateAppend){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd HH_mm_ss");
            date = simpleDateFormat.format(new Date());
        }
        return date;
    }

    public void close() {

    }
}
