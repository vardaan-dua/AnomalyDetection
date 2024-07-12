package com.example.AnomalyDetection.GetData;



import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class ReadExcel {
    private static String formatDouble(double value, int decimalPlaces) {
        return String.format("%." + decimalPlaces + "f", value);
    }
    public static List<String[]> getData (String path , int keepRowsWithTheseNumberOfCells) {

        List<String[]> data = new ArrayList<>();
        try {
            FileInputStream file = new FileInputStream(new File(path));
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            int rowNumber = 0;
            boolean isFirstRow =true;
            for (Row row : sheet) {
                if(!isFirstRow) {
                    int count = 0;
                    for (Cell cell : row) {
                        count++;
                    }
                    if (count != keepRowsWithTheseNumberOfCells) continue;
                    String[] cur = new String[keepRowsWithTheseNumberOfCells];
                    int pos = 0;
                    for (Cell cell : row) {
                        switch (cell.getCellType()) {
                            case STRING: {
                                cur[pos++] = (cell.getRichStringCellValue().getString());
                                break;
                            }
                            case NUMERIC: {
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    cur[pos++] = (cell.getDateCellValue() + "");
                                } else {
                                    cur[pos++] = (cell.getNumericCellValue() + "");
                                }
                                break;
                            }
                            default:
                                System.out.println("gadbad\n");
                        }
                    }
                    data.add(cur);
                    rowNumber++;
                }
                else isFirstRow = false;

            }
            return data;
        }
        catch(Exception e)
        {
            System.out.println("fat gaya oyeee");
            return data;
        }
    }
}
