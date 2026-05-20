package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import utilities.WaitHelper;

public class BasePage {
    protected WebDriver driver;
    protected WaitHelper waitHelper;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        // Pass default timeout (e.g., 10 seconds) to WaitHelper
        this.waitHelper = new WaitHelper(driver, 10);
        PageFactory.initElements(driver, this); // initialize @FindBy elements
    }
}
