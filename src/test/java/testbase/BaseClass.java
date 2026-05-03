package testbase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;


public class BaseClass {
    public static WebDriver driver;
    public Logger logger;
    public Properties p;

    @BeforeClass(groups={"Sanity","Regression","Master"})
    @Parameters({"os","browser"})
    public void setUp(String os, String browser) throws Exception {
        // Load config.properties
        FileReader file = new FileReader("./src/test/resources/config.properties");
        p = new Properties();
        p.load(file);

        logger = LogManager.getLogger(this.getClass());

        String executionEnv = p.getProperty("execution_env");
        String appurl = p.getProperty("appurl");
    //    String hubUrl = p.getProperty("hub_url");

       //gemini 
        if (executionEnv.equalsIgnoreCase("remote")) {
            String hubUrl = p.getProperty("hub_url"); // http://localhost:4444/wd/hub

            if (browser.equalsIgnoreCase("edge")) {
                EdgeOptions options = new EdgeOptions();
                driver = new RemoteWebDriver(new URL(hubUrl), options);
            } else if (browser.equalsIgnoreCase("chrome")) {
                ChromeOptions options = new ChromeOptions();
                driver = new RemoteWebDriver(new URL(hubUrl), options);
            } else if (browser.equalsIgnoreCase("firefox")) {
                FirefoxOptions options = new FirefoxOptions();
                driver = new RemoteWebDriver(new URL(hubUrl), options);
            } else {
                System.out.println("No matching browser for remote execution");
                return;
            }
        } else {
            // Local execution block
            switch (browser.toLowerCase()) {
                case "edge": driver = new EdgeDriver(); break;
                case "chrome": driver = new ChromeDriver(); break;
                case "firefox": driver = new FirefoxDriver(); break;
                default: System.out.println("No matching browser name"); return;
            }
        }
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(appurl);
        driver.manage().window().maximize();

        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(appurl);
        driver.manage().window().maximize();

       /* if (p.getProperty("execution_env").equalsIgnoreCase("remote")) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
  
            // OS
            if (os.equalsIgnoreCase("windows")) {
                capabilities.setPlatform(Platform.WIN10);
            } else if (os.equalsIgnoreCase("mac")) {
                capabilities.setPlatform(Platform.MAC);
            } else {
                System.out.println("No matching OS");
                return;
            }
           

            // Browser
            switch (browser.toLowerCase()) {
                case "edge": capabilities.setBrowserName("MicrosoftEdge"); break;
                case "chrome": capabilities.setBrowserName("chrome"); break;
                case "firefox": capabilities.setBrowserName("firefox"); break;
                default: System.out.println("No matching browser"); return;
            }

            driver = new RemoteWebDriver(new URL("hub_url"), capabilities);
*/
   /* }
} else if (p.getProperty("execution_env").equalsIgnoreCase("local")) {
            switch (browser.toLowerCase()) {
                case "edge": driver = new EdgeDriver(); break;
                case "chrome": driver = new ChromeDriver(); break;
                case "firefox": driver = new FirefoxDriver(); break;
                default: System.out.println("No matching browser name"); return;
            }
        }
        */

        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(appurl);
        driver.manage().window().maximize();
    }

    @AfterClass(groups={"Sanity","Regression","Master"})
    public void tearDown() {
       
            driver.quit();
        
    }

    // Utility method to capture screenshot
    public String captureScreen(String testName) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver;
        File sourceFile = ts.getScreenshotAs(OutputType.FILE);
        String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + testName + "_" + timeStamp + ".png";
        File targetFile = new File(targetFilePath);
        sourceFile.renameTo(targetFile);
        return targetFilePath;
    }
}
