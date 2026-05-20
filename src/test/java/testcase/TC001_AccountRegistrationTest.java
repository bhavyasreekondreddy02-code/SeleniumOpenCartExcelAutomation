package testcase;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testbase.BaseClass;
import utilities.JsonDataProviders;

public class TC001_AccountRegistrationTest extends BaseClass {

    @Test(dataProvider = "RegisterDataHybrid", dataProviderClass = JsonDataProviders.class, groups = {"Regression","Master"})
    public void verify_account_registration(String firstname, String lastname, String email, String password, String mobile) {
        try {
            logger.info("**** Starting TC001_AccountRegistrationTest ****");

            HomePage hp = new HomePage(getDriver());
            hp.clickMyAccount();
            hp.clickRegister();

            AccountRegistrationPage regPage = new AccountRegistrationPage(getDriver());

            // Generate unique email
            String uniqueEmail = email.split("@")[0] + System.currentTimeMillis() + "@" + email.split("@")[1];
            logger.info("Generated unique email: " + uniqueEmail);

            // Fill registration form
            regPage.setFirstName(firstname);
            regPage.setLastName(lastname);
            regPage.setEmail(uniqueEmail);
            regPage.setTelephone(mobile);
            regPage.setPassword(password);
            regPage.setConfirmPassword(password);
            regPage.setPrivacyPolicy();
            regPage.clickContinue();

            // Validate confirmation
            String confMsg = regPage.getConfirmationMsg();
            logger.info("Actual confirmation message: " + confMsg);

            Assert.assertTrue(confMsg.toLowerCase().contains("account has been created"),
                    "Account registration failed. Actual message: " + confMsg);

        } catch (Exception e) {
            String screenshotPath = captureScreen("verify_account_registration");
            logger.error("Exception during account registration: " + e.getMessage() + 
                         ". Screenshot: " + screenshotPath, e);
            Assert.fail("Exception during account registration: " + e.getMessage());
        }

        logger.info("**** Finish TC001_AccountRegistrationTest ****");
    }
}
