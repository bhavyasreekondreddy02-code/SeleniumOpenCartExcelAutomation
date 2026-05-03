package utilities;
import java.io.File;
// this are dynamic data and we are going to read from execl sheet and write into excel sheet
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class ExcelUtility {
	public String path;
	public FileInputStream fi;
	public FileOutputStream fo;
	public XSSFWorkbook workbook;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell cell;
	public CellStyle style;

    public ExcelUtility(String path) {
        this.path = path;
    }

    // Get row count
    public int getRowCount(String sheetName) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        int rowcount = sheet.getLastRowNum();
        workbook.close();
        fi.close();
        return rowcount;
    }

    // Get cell count in a row
    public int getCellCount(String sheetName, int rownum) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rownum);
        int cellcount = row.getLastCellNum();
        workbook.close();
        fi.close();
        return cellcount;
    }

    // Get cell data
    public String getCellData(String sheetName, int rownum, int colnum) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rownum);
        cell = row.getCell(colnum);

        DataFormatter formatter = new DataFormatter();
        String data;
        try {
            data = formatter.formatCellValue(cell);//returns the formated value of the cell as string
        } catch (Exception e) {
            data = "";
        }

        workbook.close();
        fi.close();
        return data;
    }

    // Set cell data
    public void setCellData(String sheetName, int rownum, int colnum, String data) throws IOException {
        File xlfile = new File(path);

        // If file does not exist, create a new workbook
        if (!xlfile.exists()) {
            workbook = new XSSFWorkbook();
            fo = new FileOutputStream(path);
            workbook.write(fo);
            workbook.close();
            fo.close();
        }

        // Open the existing file
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);

        // If sheet does not exist, create it
        if (workbook.getSheetIndex(sheetName) == -1) {
            workbook.createSheet(sheetName);
        }
        sheet = workbook.getSheet(sheetName);

        // If row does not exist, create it
        if (sheet.getRow(rownum) == null) {
            sheet.createRow(rownum);
        }
        row = sheet.getRow(rownum);

        // Create cell and set value
        cell = row.createCell(colnum);
        cell.setCellValue(data);

        // Write changes back to file
        fo = new FileOutputStream(path);
        workbook.write(fo);

        // Close resources
        workbook.close();
        fi.close();
        fo.close();
    }

//Fill cell with RED color
public void fillRedColor(String sheetName, int rownum, int colnum) throws IOException {
 fi = new FileInputStream(path);
 workbook = new XSSFWorkbook(fi);
 sheet = workbook.getSheet(sheetName);
 row = sheet.getRow(rownum);
 cell = row.getCell(colnum);

 style = workbook.createCellStyle();
 style.setFillForegroundColor(IndexedColors.RED.getIndex());
 style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

 cell.setCellStyle(style);

 fo = new FileOutputStream(path);
 workbook.write(fo);
 workbook.close();
 fi.close();
 fo.close();
}

//Fill cell with GREEN color
public void fillGreenColor(String sheetName, int rownum, int colnum) throws IOException {
 fi = new FileInputStream(path);
 workbook = new XSSFWorkbook(fi);
 sheet = workbook.getSheet(sheetName);
 row = sheet.getRow(rownum);
 cell = row.getCell(colnum);

 style = workbook.createCellStyle();
 style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
 style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

 cell.setCellStyle(style);

 fo = new FileOutputStream(path);
 workbook.write(fo);
 workbook.close();
 fi.close();
 fo.close();
}
}

