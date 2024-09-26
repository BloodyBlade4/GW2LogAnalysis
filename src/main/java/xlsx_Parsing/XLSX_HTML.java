package xlsx_Parsing;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFDrawing;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class XLSX_HTML {
	static void addHTML(String htmlPath, SXSSFWorkbook workBook, int F_COL, int F_ROW) {
		try {
	        FileInputStream fis = new FileInputStream(htmlPath);
	        byte[] fisData = fis.readAllBytes();
	        fis.close();
        
        	int storageId = workBook.addOlePackage(fisData, "", htmlPath, htmlPath);
        	SXSSFDrawing pat = workBook.getSheetAt(0).createDrawingPatriarch();
        	int picId = workBook.addPicture(getSampleJpg(), Workbook.PICTURE_TYPE_JPEG);
        	ClientAnchor anc = pat.createAnchor(0, 0, 0, 0, F_COL, F_ROW, F_COL+1, F_ROW+1);
        	anc.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
        	pat.createObjectData(anc, storageId, picId);
        }
        catch(IOException e) {
        	System.out.println("Exception for inserting object: " + e);
        	e.printStackTrace();
        }
        catch(Exception ex) {
        	System.out.println("Exception, general " + ex);
        	ex.printStackTrace();
        }
	}
	
	static byte[] getSampleJpg() throws IOException {
		XLSX_HTML.class.getResource("/html_Image.jpg");
		BufferedImage img = ImageIO.read(XLSX_HTML.class.getResource("/html_Image.jpg"));
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(img, "JPG", bos);
        return bos.toByteArray();
    }
}
