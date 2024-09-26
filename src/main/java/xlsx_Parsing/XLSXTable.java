package xlsx_Parsing;

import java.util.List;

import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTable;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumn;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumns;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableStyleInfo;

public class XLSXTable {
	public synchronized static void createTable(SXSSFWorkbook workBook, SXSSFSheet sheet, List<String> colNames, int FIRST_DATA_ROW, int COL_COUNT, int LAST_DATA_ROW) {
		
		SXSSFRow totalsRow=sheet.createRow(LAST_DATA_ROW);
		

        AreaReference dataRange = new AreaReference(
			new CellReference(FIRST_DATA_ROW-1, 0), 
			new CellReference(LAST_DATA_ROW, COL_COUNT-1),
			SpreadsheetVersion.EXCEL2007
         );
	        
        CTTable cttable = workBook.getXSSFWorkbook()
             .getSheetAt(0).createTable(dataRange).getCTTable();

         CTTableStyleInfo tableStyle = cttable.addNewTableStyleInfo();
         tableStyle.setName("TableStyleMedium9");
         cttable.setDisplayName("MYTABLE");
         cttable.setName("TABLE");
         cttable.setId(1L);
         cttable.addNewAutoFilter().setRef(dataRange.formatAsString());
         
         CTTableColumns columns = cttable.getTableColumns();
         
         //set the first row, used for the title "Averages".
         CTTableColumn columnTitle = columns.getTableColumnArray(0);  
         columnTitle.setName(colNames.get(0));   
         columnTitle.setId(0 + 1L);
         totalsRow.createCell(0);
         cttable.getTableColumns().getTableColumnList().get(0)
			.setTotalsRowFunction(org.openxmlformats.schemas.spreadsheetml.x2006.main.STTotalsRowFunction.CUSTOM);
         
         short percentStyle = workBook.createDataFormat().getFormat(BuiltinFormats.getBuiltinFormat(0xa));
         
         for(int i=1;i<COL_COUNT;i++) { //Note: i = 1, so as to skip the title of "Averages" 
         	//check to ensure you're within the range of the table and headers. If this fails, excel will manage the repairs to the file.
        	if (i >= columns.getCount() || i >= colNames.size()) {
        		System.out.println("Hey, columns is out of bounds with i=" + i + ", columns count=" + columns.getCount() + ", and colNames size=" + colNames.size());
        		break;
        	}
         	CTTableColumn column = columns.getTableColumnArray(i);  
         	column.setName(colNames.get(i));   
             column.setId(i + 1L);
         	
             //Set the average formula in the totals row. 
 			Cell c = totalsRow.createCell(i);
 			cttable.getTableColumns().getTableColumnList().get(i)
 				.setTotalsRowFunction(org.openxmlformats.schemas.spreadsheetml.x2006.main.STTotalsRowFunction.CUSTOM);
 			if (sheet.getRow(LAST_DATA_ROW-1)!= null && 
 					sheet.getRow(LAST_DATA_ROW-1).getCell(i) != null && 
 					sheet.getRow(LAST_DATA_ROW-1).getCell(i).getCellType() == CellType.NUMERIC) {
 				
 				if (sheet.getRow(LAST_DATA_ROW-1).getCell(i).getNumericCellValue() != 0 &&
 						sheet.getRow(LAST_DATA_ROW-1).getCell(i).getCellStyle().getDataFormat() == percentStyle) 
 					c.setCellStyle(XLSXFromCSV.stylePercentage);
 				else 
 					c.setCellStyle(XLSXFromCSV.styleNumber);
 				
 				AreaReference range = new AreaReference(new CellReference(FIRST_DATA_ROW, i), new CellReference(LAST_DATA_ROW-1, i), null);
 				String r = "Average(" + range.formatAsString() + ")";
 				c.setCellFormula(r);
 			}
 		}
         totalsRow.getCell(0).setCellValue("Averages: ");
         cttable.setTotalsRowCount(1);
	}
	
	public synchronized static void adjustColumnWidth(SXSSFSheet sheet, int COL_COUNT) {
		//autoSizeColumn(int column)
		for (int i = 0; i < COL_COUNT; i++) {
			sheet.autoSizeColumn(i);
		}
	}	
}
