package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class AccountRegistrationPage extends BasePage {

    public AccountRegistrationPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id="input-firstname")
    private WebElement txtFirstname;

    @FindBy(id="input-lastname")
    private WebElement txtLastname;

    @FindBy(id="input-email")
    private WebElement txtEmail;

    @FindBy(id="input-telephone")
    private WebElement txtTelephone;

    @FindBy(id="input-password")
    private WebElement txtPassword;

    @FindBy(id="input-confirm")
    private WebElement txtConfirmPassword;

    @FindBy(name="agree")
    private WebElement chkPolicy;

    @FindBy(xpath="//input[@value='Continue']")
    private WebElement btnContinue;

    @FindBy(xpath="//h1[normalize-space()='Your Account Has Been Created!']")
    private WebElement msgConfirmation;

    // Actions
    public void setFirstName(String fname) {
        txtFirstname.clear();
        txtFirstname.sendKeys(fname);
    }

    public void setLastName(String lname) {
        txtLastname.clear();
        txtLastname.sendKeys(lname);
    }

    public void setEmail(String email) {
        txtEmail.clear();
        txtEmail.sendKeys(email);
    }

    public void setTelephone(String tel) {
        txtTelephone.clear();
        txtTelephone.sendKeys(tel);
    }

    public void setPassword(String pwd) {
        txtPassword.clear();
        txtPassword.sendKeys(pwd);
    }

    public void setConfirmPassword(String pwd) {
        txtConfirmPassword.clear();
        txtConfirmPassword.sendKeys(pwd);
    }

    public void setPrivacyPolicy() {
        if (!chkPolicy.isSelected()) {
            chkPolicy.click();
        }
    }

    public void clickContinue() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.elementToBeClickable(btnContinue))
            .click();
    }

    public String getConfirmationMsg() {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(msgConfirmation))
                .getText();
        } catch (Exception e) {
            return "Confirmation message not found";
        }
    }
}
