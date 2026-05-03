package testcase;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.MyaccountPage;
import pageObjects.loginpage;
import testbase.BaseClass;

public class TC002_LoginTest extends BaseClass
{
	@Test(groups = {"Sanity","Master"})
	public void test_login()
	{
		logger.info("****Starting TC002_LoginTest****");

     try {
        //Homepage
	    HomePage hp = new HomePage(driver);
        hp.clickMyAccount();
        logger.info("Clicked on MyAccount Link ...");
        hp.clickLogin();
        logger.info("Clicked on login Link...");
        
        //Loginpage
        loginpage lp = new loginpage(driver); // to call login page class
        lp.setEmail(p.getProperty("email"));
        lp.setPassword(p.getProperty("password"));
        lp.clickLogin();
        
        //MyAccountPage
        MyaccountPage macc = new MyaccountPage(driver);
        boolean targetpage = macc.isMyAccountPageExist();
        Assert.assertTrue(targetpage);
        //Assert.assertEquals(targetpage, "Login failed");
     }
        catch (Exception e) 
    	{
    	
    	                                   
    	Assert.fail();
    	
}
        
        logger.info("****Finish TC001_AccountRegistrationTest****");

	}

}
