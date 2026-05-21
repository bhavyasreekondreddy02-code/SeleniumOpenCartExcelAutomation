package testcase;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testbase.BaseClass;
import utilities.ExcelDataProviders;

public class TC001_AccountRegistrationTest extends BaseClass {

    @Test(dataProvider = "RegisterDataExcel", dataProviderClass = ExcelDataProviders.class, groups = {"Master","Regression"})
    public void verify_account_registration(String firstname, String lastname, String email, String password, String mobile) {
        try {
            logger.info("**** Starting TC001_AccountRegistrationTest ****");

            // Navigate to Registration Page
            HomePage hp = new HomePage(getDriver());
            hp.clickMyAccount();
            hp.clickRegister();

            AccountRegistrationPage regPage = new AccountRegistrationPage(getDriver());

            logger.info("Filling registration form with Excel + DataGenerator data...");

            // Fill form
            regPage.setFirstName(firstname);
            regPage.setLastName(lastname);
            regPage.setEmail(email);       // made unique by DataGenerator in ExcelDataProviders
            regPage.setTelephone(mobile);  // randomized if blank
            regPage.setPassword(password);
            regPage.setConfirmPassword(password);

            regPage.setPrivacyPolicy();
            regPage.clickContinue();

            // Validate confirmation message
            String confMsg = regPage.getConfirmationMsg();
            logger.info("Actual confirmation message: " + confMsg);

            Assert.assertEquals(confMsg, "Your Account Has Been Created!",
                    "Account registration failed. Actual message: " + confMsg);

        } catch (Exception e) {
            logger.error("Exception during account registration: " + e.getMessage(), e);
            Assert.fail("Exception during account registration: " + e.getMessage());
        }

        logger.info("**** Finish TC001_AccountRegistrationTest ****");
    }
}
