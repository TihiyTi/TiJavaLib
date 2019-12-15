package com.ti;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SimpleSheetParser {
    public static List<List<String>> getLisListStringFromSheet(Sheet sheet){
        List<List<String >> listList = new ArrayList<>();
        Iterator<Row> rowIterator = sheet.rowIterator();
        while(rowIterator.hasNext()){
            List<String> stringList = new ArrayList<>();
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()){
                Cell cell = cellIterator.next();
                String s = getStringAnyway(cell);
                stringList.add(s);
            }
            listList.add(stringList);
        }
        return listList;
    }

    public static List<List<Cell>> getListListCellFromSheet(Sheet sheet){
        List<List<Cell>> listList = new ArrayList<>();
        Iterator<Row> rowIterator = sheet.rowIterator();
        while(rowIterator.hasNext()){
            List<Cell> cellList = new ArrayList<>();
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()){
                Cell cell = cellIterator.next();
                cellList.add(cell);
            }
            listList.add(cellList);
        }
        return listList;
    }


    public static List<String> getListString(Row row){
        List<String> stringList = new ArrayList<>();
        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()){
            Cell cell = cellIterator.next();
            String s = getStringAnyway(cell);
            stringList.add(s);
        }
        return stringList;
    }

    private static String getStringAnyway(Cell cell){
        if(cell==null){return "";}
        if(cell.getCellType()!=CellType.NUMERIC){
            return cell.getStringCellValue();
        }else {
            cell.setCellType(CellType.STRING);
            return cell.getStringCellValue();
        }
    }
}
