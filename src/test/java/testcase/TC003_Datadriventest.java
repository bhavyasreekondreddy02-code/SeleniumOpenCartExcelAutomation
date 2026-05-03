 package testcase;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.MyaccountPage;
import pageObjects.loginpage;
import testbase.BaseClass;

/* positive testing - data is valid and we are able to to login successfully.
  * negitive testing - data is invalid and we are not able to login successfully.
  * 
  */
public class TC003_Datadriventest extends BaseClass
{
	
	@Test(dataProvider = "LoginData", dataProviderClass = utilities.DataProviders.class,groups="datadriven")
	public void test_loginDDT(String email, String pwd, String exp) {
	    logger.info("**** Starting TC003_LoginDDTest ****");

	    try {
	        // Homepage
	        HomePage hp = new HomePage(driver);
	        hp.clickMyAccount();
	        hp.clickLogin();

	        // Login page
	        loginpage lp = new loginpage(driver);
	        lp.setEmail(email);
	        lp.setPassword(pwd);
	        lp.clickLogin();

	        // MyAccount page
	        MyaccountPage macc = new MyaccountPage(driver);
	        boolean targetPage = macc.isMyAccountPageExist();

	        // ✅ Validation logic
	        // data valid  - test successful- testcase passed
	                 //    - test unsuccessful - testcase failed
	        if (exp.equalsIgnoreCase("valid")) {
	            if (targetPage) {
	                macc.clickLogout();
	                Assert.assertTrue(true, "Login successful as expected");
	            } else {
	                Assert.assertTrue(false, "Expected valid login but failed");
	            }
	            // data invalid  - test successful- testcase failed
                //              - test unsuccessful - testcase passed
	        } else if (exp.equalsIgnoreCase("invalid")) {
	            if (targetPage) {
	                macc.clickLogout();
	                Assert.assertTrue(false, "Expected invalid login but succeeded");
	            } else {
	                Assert.assertTrue(true, "Invalid login correctly rejected");
	            }
	        }

	    } catch (Exception e) {
	        Assert.fail("Test failed due to exception: " + e.getMessage());
	    }

	    logger.info("**** Finished TC003_LoginDDTest ****");
	}
}