package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//a[normalize-space()='My Account']")
    private WebElement lnkMyAccount;

    @FindBy(xpath = "//a[normalize-space()='Register']")
    private WebElement lnkRegister;

    @FindBy(xpath = "//a[normalize-space()='Login']")
    private WebElement lnkLogin;

    @FindBy(name = "search")
    private WebElement txtSearch;

    @FindBy(xpath = "//button[@class='btn btn-default btn-lg']")
    private WebElement btnSearch;

    // Click on "My Account"
    public void clickMyAccount() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.elementToBeClickable(lnkMyAccount))
            .click();
    }

    // Click on "Register"
    public void clickRegister() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.elementToBeClickable(lnkRegister))
            .click();
    }

    // Click on "Login"
    public void clickLogin() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.elementToBeClickable(lnkLogin))
            .click();
    }

    // Enter product name in search box (returns HomePage for chaining)
    public HomePage enterSearch(String productName) {
        txtSearch.clear();
        txtSearch.sendKeys(productName);
        return this;
    }

}