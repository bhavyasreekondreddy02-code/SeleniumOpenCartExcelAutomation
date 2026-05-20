package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.*;
import java.util.stream.Collectors;

public class SearchPage extends BasePage {

    private By productTitles = By.cssSelector("div.caption h4 a");
    private By productCategories = By.cssSelector("div.caption p");

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    // ✅ Search product
    public void searchProduct(String productName) {
        driver.findElement(By.name("search")).clear();
        driver.findElement(By.name("search")).sendKeys(productName);
        driver.findElement(By.cssSelector("button.btn.btn-default")).click();
    }

    // ✅ Get all product titles
    public List<String> getAllProductTitles() {
        return driver.findElements(productTitles).stream()
                .map(e -> e.getText().trim())
                .collect(Collectors.toList());
    }

    // ✅ Get all product categories
    public List<String> getAllProductCategories() {
        return driver.findElements(productCategories).stream()
                .map(e -> e.getText().trim())
                .collect(Collectors.toList());
    }

    // ✅ Map product titles to categories (for TC003)
    public Map<String, String> getProductTitleCategoryMap() {
        List<WebElement> titles = driver.findElements(productTitles);
        List<WebElement> categories = driver.findElements(productCategories);

        Map<String, String> productMap = new HashMap<>();
        for (int i = 0; i < titles.size(); i++) {
            productMap.put(titles.get(i).getText().trim(), categories.get(i).getText().trim());
        }
        return productMap;
    }

    // ✅ Check if product is displayed (for TC004)
    public boolean isProductDisplayed(String productName) {
        return driver.findElements(productTitles).stream()
                .anyMatch(e -> e.getText().trim().equalsIgnoreCase(productName));
    }

    // ✅ Click on a product by name (for TC004)
    public ProductPage clickOnProduct(String productName) {
        for (WebElement product : driver.findElements(productTitles)) {
            if (product.getText().trim().equalsIgnoreCase(productName)) {
                product.click();
                return new ProductPage(driver); // return next page object
            }
        }
        throw new RuntimeException("Product not found: " + productName);
    }
}
