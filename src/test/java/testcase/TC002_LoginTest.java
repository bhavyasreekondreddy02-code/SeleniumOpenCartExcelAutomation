package testcase;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyaccountPage;
import testbase.BaseClass;
import utilities.ExcelDataProviders;

public class TC002_LoginTest extends BaseClass {

	@Test(dataProvider = "LoginDataExcel", dataProviderClass = ExcelDataProviders.class, groups = {"Sanity","Master"})

	public void test_login(String username, String password, String exp) {
	    logger.info("**** Starting TC002_LoginTest ****");
	    logger.info("Executing login test with user: " + username);

	    try {
	        HomePage hp = new HomePage(getDriver());
	        hp.clickMyAccount();
	        hp.clickLogin();

	        LoginPage lp = new LoginPage(getDriver());
	        lp.setEmail(username);
	        lp.setPassword(password);
	        lp.clickLogin();

	        MyaccountPage macc = new MyaccountPage(getDriver());
	        boolean accountPage = macc.isMyAccountPageExist();

	        if (exp.equalsIgnoreCase("valid")) {
	            Assert.assertTrue(accountPage, "Expected valid login but failed for user: " + username);
	            if (accountPage) {
	                macc.clickLogout();
	            }
	        } else if (exp.equalsIgnoreCase("invalid")) {
	            if (accountPage) {
	                macc.clickLogout();
	                Assert.fail("Expected invalid login but succeeded for user: " + username);
	            } else {
	                String warningMsg = lp.getWarningMsg();
	                Assert.assertTrue(warningMsg.contains("Warning: No match for E-Mail Address and/or Password."),
	                    "Expected invalid login warning, but got: " + warningMsg);
	            }
	        }

	    } catch (Exception e) {
	        String screenshotPath = captureScreen("test_login");
	        logger.error("Exception during login: " + e.getMessage() + ". Screenshot: " + screenshotPath, e);
	        Assert.fail("Exception during login: " + e.getMessage());
	    }
	
	    logger.info("**** Finish TC002_LoginTest ****");
	}
}
