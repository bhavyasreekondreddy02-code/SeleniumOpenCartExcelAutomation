package pageObjects;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id="input-email")
    private WebElement txtEmailAddress;

    @FindBy(id="input-password")
    private WebElement txtPassword;

    @FindBy(xpath="//input[@value='Login']")
    private WebElement btnLogin;

    @FindBy(xpath="//div[contains(@class,'alert-danger')]")
    private WebElement warningMsg;

    // Enter email address
    public void setEmail(String email) {
        txtEmailAddress.clear();
        txtEmailAddress.sendKeys(email);
    }

    // Enter password
    public void setPassword(String pwd) {
        txtPassword.clear();
        txtPassword.sendKeys(pwd);
    }

    // Click Login button
    public void clickLogin() {
        btnLogin.click();
    }

    // Get warning message after invalid login
    public String getWarningMsg() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOf(warningMsg));
            return warningMsg.getText();
        } catch (TimeoutException | NoSuchElementException e) {
            return null; // No warning appeared
        }
    }
}
