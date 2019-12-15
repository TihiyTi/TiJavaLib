package com.ti;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.*;

public class ExcelUtility {

    public static Sheet getMainSheet(InputStream inputStream) {
        Workbook book = getWorkbook(inputStream);
        return book != null ? book.getSheetAt(0) : null;
    }
    public static Sheet getMainSheet(File file) {
        Workbook book = getWorkbook(file);
        return book != null ? book.getSheetAt(0) : null;
    }
    public static Sheet getMainSheet(String fullFileName) {
        Workbook book = getWorkbook(fullFileName);
        return book != null ? book.getSheetAt(0) : null;
    }

    private static Workbook getWorkbook(InputStream inputStream) {
        try {
            return WorkbookFactory.create(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private static Workbook getWorkbook(String fullFileName){
        try {
            FileInputStream fileIn = new FileInputStream(fullFileName);
            return getWorkbook(fileIn);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    private static Workbook getWorkbook(File file){
        try {
            return getWorkbook(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

//    public static Workbook mergeTable(File ... files){


//    }
}
