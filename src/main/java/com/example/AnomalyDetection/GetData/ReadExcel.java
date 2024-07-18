package com.example.AnomalyDetection.GetData;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class ReadExcel {
    public static List<String[]> getData(String path, int keepRowsWithTheseNumberOfCells) {

        List<String[]> data = new ArrayList<>();
        try {
            FileInputStream file = new FileInputStream(new File(path));
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            boolean isFirstRow = true;
            for (Row row : sheet) {
                if (!isFirstRow) {
                    int count = 0;
                    for (Cell ignored : row) {
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
                                System.out.println("Excel sheet is in wrong format");
                        }
                    }
                    data.add(cur);
                } else isFirstRow = false;

            }
            return data;
        } catch (Exception e) {
            System.out.println("Errors while reading the excel sheet");
            return data;
        }
    }
}
