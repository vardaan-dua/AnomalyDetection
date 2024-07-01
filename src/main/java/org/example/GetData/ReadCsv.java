package org.example.GetData;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class ReadCsv {
    private static String formatDouble(double value, int decimalPlaces) {
        return String.format("%." + decimalPlaces + "f", value);
    }
    public static List<String[]> getData (String path ) {

        List<String[]> data = new ArrayList<>();
        try {
            FileInputStream file = new FileInputStream(new File(path));
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            int i = 0;
            for (Row row : sheet) {
                int count  =0 ;
                for(Cell cell : row){count++;}
                if(count != 3)continue;
                String[] cur = new String[3];
                int pos =0;
                for (Cell cell : row) {
                    switch (cell.getCellType()) {
                        case STRING:{ cur[pos++]=(cell.getRichStringCellValue().getString()); break;}
                        case NUMERIC: {
                            if (DateUtil.isCellDateFormatted(cell)) {
                                cur[pos++]=(cell.getDateCellValue() + "");
                            } else {
                                cur[pos++]=(cell.getNumericCellValue() + "");
                            }
                            break;
                        }
                        default :
                            System.out.println("gadbad\n");
                    }
                }
                data.add(cur);
                i++;
            }
            return data;
        }
        catch(Exception e)
        {
            System.out.println("fat gaya");
            return data;
        }
//        try (Workbook workbook = WorkbookFactory.create(new FileInputStream(path))) {
//            Sheet sheet = workbook.getSheetAt(0); // Assuming you are reading from the first sheet
//
//            Iterator<Row> rowIterator = sheet.iterator();
//            while (rowIterator.hasNext()) {
//                Row row = rowIterator.next();
//                Iterator<Cell> cellIterator = row.cellIterator();
//
//                List<String> rowData = new ArrayList<>();
//                while (cellIterator.hasNext()) {
//                    Cell cell = cellIterator.next();
//                    switch (cell.getCellType()) {
//                        case STRING:
//                            rowData.add(cell.getStringCellValue());
//                            break;
//                        case NUMERIC:
//                            rowData.add(String.valueOf(cell.getNumericCellValue()));
//                            break;
//                        case BOOLEAN:
//                            rowData.add(String.valueOf(cell.getBooleanCellValue()));
//                            break;
//                        case BLANK:
//                            rowData.add(""); // Handle blank cells
//                            break;
//                        default:
//                            // Handle other cell types if needed
//                            rowData.add(""); // Or throw an exception
//                            break;
//                    }
//                }
//                rows.add(rowData.toArray(new String[0]));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
