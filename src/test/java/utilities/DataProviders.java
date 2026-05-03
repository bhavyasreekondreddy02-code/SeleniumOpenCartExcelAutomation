package utilities;

import java.io.IOException;
import org.testng.annotations.DataProvider;

public class DataProviders {

    // DataProvider 1
    @DataProvider(name = "LoginData")
    public String[][] getData() throws IOException {
        String path = ".\\testData\\opencart_logintestdata.xlsx"; // taking xl file from testData
        ExcelUtility xlutil = new ExcelUtility(path); // creating an object for ExcelUtility

        int totalrows = xlutil.getRowCount("Sheet1");
        int totalcols = xlutil.getCellCount("Sheet1", 1);

        // created two-dimensional array to store data
        String[][] logindata = new String[totalrows][totalcols];

        // read the data from Excel and store in array
        for (int i = 1; i <= totalrows; i++) { // i = rows outer for loop
            for (int j = 0; j < totalcols; j++) { // j = columns inner for loop
                logindata[i - 1][j] = xlutil.getCellData("Sheet1", i, j);
            }
        }
        return logindata; // returning two-dimensional array
    }
}

//dataprovider 2
//all dataproviders are created in this utilitlity only
//we cant use same dataprovider for different test  cases