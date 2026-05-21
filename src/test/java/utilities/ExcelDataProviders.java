package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.*;
import org.testng.annotations.DataProvider;

public class ExcelDataProviders {

    private Object[][] readExcelData(String filePath, String sheetName) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = WorkbookFactory.create(fis);
        Sheet sheet = workbook.getSheet(sheetName);

        int rowCount = sheet.getLastRowNum();
        int colCount = sheet.getRow(0).getLastCellNum();

        Object[][] data = new Object[rowCount][colCount];

        for (int i = 1; i <= rowCount; i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < colCount; j++) {
                Cell cell = row.getCell(j);
                data[i - 1][j] = (cell == null) ? "" : cell.toString();
            }
        }
        workbook.close();
        fis.close();
        return data;
    }

    // Registration DataProvider (with DataGenerator for unique email + mobile)
    @DataProvider(name = "RegisterDataExcel")
    public Object[][] getRegisterDataExcel() throws IOException {
        Object[][] data = readExcelData("./src/test/resources/testdata/RegisterData.xlsx", "Register");

        for (int i = 0; i < data.length; i++) {
            // Email column assumed at index 2
            data[i][2] = DataGenerator.generateUniqueEmail(data[i][2].toString());
            // Mobile column assumed at index 4
            data[i][4] = DataGenerator.randomNumber(10);
        }
        return data;
    }
//login dataprovider
    @DataProvider(name = "LoginDataExcel")
    public Object[][] getLoginDataExcel() throws IOException {
        return readExcelData("./src/test/resources/testdata/LoginData.xlsx", "Login");
    }
}