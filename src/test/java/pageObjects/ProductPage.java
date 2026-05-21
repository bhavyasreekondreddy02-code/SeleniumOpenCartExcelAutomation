package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage extends BasePage {

    private By addToCartButton = By.id("button-cart");
    private By alertBox = By.cssSelector("div.alert");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public CartPage clickAddToCart() {
        driver.findElement(addToCartButton).click();

        // Wait for success alert
        waitHelper.waitForVisibility(alertBox);

        String alertText = driver.findElement(alertBox).getText().trim();
        System.out.println("Alert text: " + alertText);

        if (!alertText.toLowerCase().contains("success")) {
            throw new RuntimeException("Add to cart failed: " + alertText);
        }

        return new CartPage(driver);
    }
}
