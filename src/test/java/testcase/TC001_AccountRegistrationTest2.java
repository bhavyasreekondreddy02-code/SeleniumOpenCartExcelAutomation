package testcase;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testbase.BaseClass;
import utilities.DataGenerator;

public class TC001_AccountRegistrationTest2 extends BaseClass {

    @Test(groups = {"Master","Regression"})
    public void verify_account_registration() {
        try {
            logger.info("**** Starting TC001_AccountRegistrationTest2 ****");

            // Navigate to Registration Page
            HomePage hp = new HomePage(getDriver());
            hp.clickMyAccount();
            hp.clickRegister();

            AccountRegistrationPage regPage = new AccountRegistrationPage(getDriver());

            logger.info("Filling registration form with random data...");

            // Generate random data
            String firstname = DataGenerator.randomString(5);
            String lastname = DataGenerator.randomString(6);
            String email = DataGenerator.randomEmail();
            String mobile = DataGenerator.randomMobile();
            String password = DataGenerator.randomPassword();

            logger.info("Test Data -> FirstName: " + firstname + ", LastName: " + lastname + ", Email: " + email);

            // Fill form
            regPage.setFirstName(firstname);
            regPage.setLastName(lastname);
            regPage.setEmail(email);
            regPage.setTelephone(mobile);
            regPage.setPassword(password);
            regPage.setConfirmPassword(password);

            regPage.setPrivacyPolicy();
            regPage.clickContinue();

            // Validate confirmation message
            logger.info("Validating account registration confirmation message...");
            String confMsg = regPage.getConfirmationMsg();
            logger.info("Actual confirmation message: " + confMsg);

            Assert.assertEquals(confMsg, "Your Account Has Been Created!",
                    "Account registration failed. Actual message: " + confMsg);

        } catch (Exception e) {
            logger.error("Exception during account registration: " + e.getMessage(), e);
            Assert.fail("Exception during account registration: " + e.getMessage());
        }

        logger.info("**** Finish TC001_AccountRegistrationTest2 ****");
    }
}
