package testbase;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

public class BaseClass {

    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public Logger logger = LogManager.getLogger(BaseClass.class); // ✅ initialized at declaration
    public Properties p;

    public static WebDriver getDriver() {
        return driver.get();
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"os","browser"})
    public void setUp(String os, String browser) throws Exception {
        try (FileReader file = new FileReader("./src/test/resources/Config.properties")) {
            p = new Properties();
            p.load(file);
        }

        // Optionally reassign logger to current test class
        logger = LogManager.getLogger(this.getClass());

        String executionEnv = p.getProperty("execution_env");
        String appurl = p.getProperty("appurl");
        int waitTime = Integer.parseInt(p.getProperty("implicit_wait", "10"));

        if (executionEnv.equalsIgnoreCase("remote")) {
            String hubUrl = p.getProperty("hub_url");
            switch (browser.toLowerCase()) {
                case "edge": driver.set(new RemoteWebDriver(new URL(hubUrl), new EdgeOptions())); break;
                case "chrome": driver.set(new RemoteWebDriver(new URL(hubUrl), new ChromeOptions())); break;
                case "firefox": driver.set(new RemoteWebDriver(new URL(hubUrl), new FirefoxOptions())); break;
                default: logger.error("No matching browser for remote execution"); return;
            }
        } else {
            switch (browser.toLowerCase()) {
                case "edge": driver.set(new EdgeDriver(new EdgeOptions())); break;
                case "chrome": driver.set(new ChromeDriver(new ChromeOptions())); break;
                case "firefox": driver.set(new FirefoxDriver(new FirefoxOptions())); break;
                default: logger.error("No matching browser name"); return;
            }
        }

        getDriver().manage().deleteAllCookies();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(waitTime));
        getDriver().get(appurl);
        getDriver().manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove();
        }
    }

    public String captureScreen(String testName) {
        if (getDriver() == null) {
            if (logger != null) {
                logger.error("Driver is null. Ensure the browser is initialized before capturing screenshots.");
            }
            return null;
        }

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + testName + "_" + timeStamp + ".png";

        try {
            TakesScreenshot ts = (TakesScreenshot) getDriver();
            File sourceFile = ts.getScreenshotAs(OutputType.FILE);
            File targetFile = new File(screenshotPath);
            targetFile.getParentFile().mkdirs();
            Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            if (logger != null) {
                logger.info("Screenshot captured: " + screenshotPath);
            }
            return screenshotPath;
        } catch (Exception e) {
            if (logger != null) {
                logger.error("Failed to capture screenshot: " + e.getMessage(), e);
            }
            return null;
        }
    }
}
