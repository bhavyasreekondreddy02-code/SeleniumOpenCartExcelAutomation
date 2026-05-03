package testcase;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testbase.BaseClass;
import utilities.DataGenerator;


public class TC001_AccountRegistrationTest extends BaseClass{
    
  

    @Test(groups = {"Regression","Master"})
    public void verify_account_registration() {
    	try {
    	logger.info("****Starting TC001_AccountRegistrationTest****");
        HomePage hp = new HomePage(driver);
        
        hp.clickMyAccount();
        logger.info("Clicked on MyAccount Link ...");
        hp.clickRegister();
        logger.info("Clicked on Register Link...");

        AccountRegistrationPage regPage = new AccountRegistrationPage(driver);
       
        logger.info("Providing customer data for registration...");
        // Generate random test data using DataGenerator
        String firstName = DataGenerator.randomString(5);
        String lastName = DataGenerator.randomString(7);
        String email = DataGenerator.randomEmail();
        String phone = DataGenerator.randomNumber(10);
        String password = DataGenerator.randomAlphaNumeric(8);

        // Fill registration form
        regPage.setFirstName(firstName);
        regPage.setLastName(lastName);
        regPage.setEmail(email);
        regPage.setTelephone(phone);
        regPage.setPassword(password);
        regPage.setConfirmPassword(password);

        regPage.setPrivacyPolicy();
        regPage.clickContinue();

        // Validate confirmation message
        logger.info("Validating account registration confirmstion message...");
        String confMsg = regPage.getConfirmationnMsg();
        System.out.println("Actual confirmation message: " + confMsg);
        if(confMsg.equals("Your Account Has Been Created!"))
        {
        	Assert.assertTrue(true);
        }
        else
        {
    	   logger.error("Test failed.. ");
        	logger.debug("Debug logs..");
        	Assert.assertTrue(false);
        	
        }
      //Assert.assertEquals(confMsg, "Your Account Has Been Created!!!");- hard assertion
    }
    catch (Exception e) 
    	{
    	
    	                                   
    	Assert.fail();
    	
}
    	logger.info("****Finish TC001_AccountRegistrationTest****");
    }
}

