package xlsx_Parsing;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import components.DialogHelper;

public class XLSXFromCSV {
	private static CellStyle styleSectioning = null;
	public static CellStyle stylePercentage = null;
	public static CellStyle styleNumber = null;
	
	private static void setStyles(SXSSFWorkbook workBook) {
		try {
			stylePercentage = workBook.createCellStyle();
			stylePercentage.setDataFormat(workBook.createDataFormat().getFormat(BuiltinFormats.getBuiltinFormat(0xa)));//wb.createDataFormat().getFormat("0.0%"));
			
			styleNumber = workBook.createCellStyle();
			styleNumber.setDataFormat(workBook.createDataFormat().getFormat(BuiltinFormats.getBuiltinFormat(2)));
			
					
			styleSectioning = workBook.createCellStyle();
			styleSectioning.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
			styleSectioning.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		}
		catch (Exception e) {
			System.out.println(e.getMessage()+"Exception in xlsx styling.");
		}
	}
	
	public synchronized static void csvParseToXLSX(String fileName, int headerRow) {
		SXSSFWorkbook workBook = new SXSSFWorkbook(50);
        
        //Create styles. 
        setStyles(workBook);
		
		try {
	        String csvFileAddress = fileName + ".csv"; //csv file address
	        String xlsxFileAddress = fileName + ".xlsx"; //xlsx file address
	         
        	SXSSFSheet sheet = workBook.createSheet("sheet1");
        	sheet.trackAllColumnsForAutoSizing();
        	
            int FIRST_DATA_ROW = headerRow +2; // +2 because it inserts a blank line for some reason.
            int COL_COUNT = 0;
	        String currentLine=null;
	        //Used by the table to set the table headers. required as the SXSSF workbook may store the data, depending on the size of the workbook, where it is not accessible. 
	        List<String> colNames = new ArrayList<>(); 
	        int RowNum=0;
	        try (BufferedReader br = new BufferedReader(new FileReader(csvFileAddress))) {
				while ((currentLine = br.readLine()) != null) { 
				    String str[] = currentLine.split(",");
				    RowNum++;
				    SXSSFRow currentRow=sheet.createRow(RowNum);
				    int i;
				    
				    for(i=0;i<str.length;i++){
				        Cell c = currentRow.createCell(i);
				        //Manage the header row, because table creation requires the availability of unique names. 
				        if (RowNum == headerRow+1) {
				        	currentRow.getCell(i).setCellType(CellType.STRING);
				        	String n = str[i];
					    	//Ensure unique names. 
					    	if (n.isBlank()) 
					    		n= "-" + i;	
					    	if(colNames.contains(n)) 
					    		n += " " + i;
				    		c.setCellValue((java.lang.String) n);
				    		colNames.add(n);
					    }
				        else {
				        	if (str[i].contains("HTML")) {
				        		c.setCellValue((java.lang.String) " ");
				    			XLSX_HTML.addHTML(str[i], workBook, i, RowNum);
				    		}
				        	else 
				        		c = setCellType(str[i], c);
				        }
				    }
				    if (i-1 > COL_COUNT) //grab the longest col. 
				    	COL_COUNT = i-1; 
				}
			}
	        catch(Exception ex) {
	        	System.out.println(ex.getMessage()+"Exception in try buffered writer");
	        }
	        
	        //create table
	        XLSXTable.createTable(workBook, sheet, colNames, FIRST_DATA_ROW, COL_COUNT, RowNum+1);
	        XLSXTable.adjustColumnWidth(sheet, COL_COUNT);
	        
	       //write to the xlsx file and close the workbook. 
	        int tryCount = 0;
	        while (true) {
		        try (FileOutputStream fileOutputStream =  new FileOutputStream(xlsxFileAddress)) {
			        workBook.write(fileOutputStream);
			        fileOutputStream.close();
			        workBook.close();
			        System.out.println("workbook Closed!");
			        break;
		        }
		        catch (IOException e1) {
		        	if(tryCount++ > 5) {
						DialogHelper.errorMessage("Runtime Error", "Error writing to .xlsx Excel file: " + e1.getMessage() + ".\n This issue appears to continue to persist. Ending the parsing process.");
						return;
					}
					DialogHelper.errorTryAgainMessage("Runtime Error", "Error writing to .xlsx Excel file: " + e1.getMessage() +". \nPress \"Yes\" to try creating the file again. Else, exit or click \"No\".");
		        }
		        catch (Exception e2) {
		        	DialogHelper.errorMessage("Runtime Error", "Unhandled error while writing to .xlsx Excel file: " + e2.getMessage() + ".\n Ending the parsing process.");
					return;
		        }
	        }
	    } catch (Exception ex) {
	        System.out.println(ex.getMessage()+"Exception in csvParseToXLSX");
	        ex.printStackTrace();
	    }
	}
	
	private static Double strToDouble(String str) {
		try {  
			return Double.parseDouble(str);  
		} catch(NumberFormatException e){  
			System.out.println("Error parsing string to double. Str: " + str + ". Error: " + e);
			return 0.0;  
		}  
	}
	
	private static Double strToPercent(String str) {
		Double v = (Double.valueOf(str.substring(0, str.length()-2)) * 0.01);
		return v;
	}
	
	private static Cell setCellType(String str, Cell c) {
		if (str.isBlank()) {
			c.setCellValue((java.lang.String)"-");
			return c;
		}
		if (str.contains("%")) {
			c.setCellStyle(stylePercentage);
			c.setCellValue((Double) strToPercent(str));
			return c;
		}
		if (str.matches("\\s*-?\\d+(\\.\\d+)?\\s*")) {
			c.setCellStyle(styleNumber);
			c.setCellValue((Double) strToDouble(str));
			return c;
		}
		c.setCellValue((java.lang.String) str);
		return c;
	}
}

