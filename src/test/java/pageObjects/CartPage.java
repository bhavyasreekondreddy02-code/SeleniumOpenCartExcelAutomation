package pageObjects;

import java.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

public class CartPage extends BasePage {

    public CartPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[@id='cart']//button")
    private WebElement btnCart;

    @FindBy(xpath = "//p[contains(text(),'Your shopping cart is empty!')]")
    private WebElement emptyCartMessage;

    @FindBy(css = "#cart .dropdown-menu td a")
    private List<WebElement> cartProducts;

    public void openCart() {
        btnCart.click();
        waitHelper.waitForVisibility(By.cssSelector("#cart .dropdown-menu"));
        if (!isCartEmpty()) {
            waitHelper.waitForPresence(By.cssSelector("#cart .dropdown-menu td a"));
        }
    }

    public boolean isCartEmpty() {
        try {
            return emptyCartMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isProductInCart(String productName) {
        if (isCartEmpty()) return false;

        waitHelper.waitForPresence(By.cssSelector("#cart .dropdown-menu td a"));
        for (WebElement product : cartProducts) {
            String actualName = product.getText().trim();
            System.out.println("Cart contains: " + actualName);

            // Flexible match: case-insensitive contains
            if (actualName.toLowerCase().contains(productName.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
