package utilities;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class WaitHelper {

    private WebDriver driver;
    private WebDriverWait wait;

    public WaitHelper(WebDriver driver, int timeoutInSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
    }

    // Wait for single element presence
    public WebElement waitForPresence(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    // ✅ Wait for multiple elements presence (list)
    public List<WebElement> waitForPresenceOfAll(By locator) {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    // ✅ Wait until list is non-empty
    public List<WebElement> waitForNonEmptyList(By locator) {
        return wait.until(driver -> {
            List<WebElement> elements = driver.findElements(locator);
            return elements.isEmpty() ? null : elements;
        });
    }

    // Wait for element visibility
    public WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Wait for element to be clickable
    public WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    // Wait for text to be present in element
    public boolean waitForText(By locator, String text) {
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    // ✅ Wait until specific product text appears in results
    public boolean waitForProductText(By locator, String productName) {
        return wait.until(driver -> {
            List<WebElement> elements = driver.findElements(locator);
            return elements.stream()
                    .anyMatch(el -> el.getText().equalsIgnoreCase(productName));
        });
    }
}
