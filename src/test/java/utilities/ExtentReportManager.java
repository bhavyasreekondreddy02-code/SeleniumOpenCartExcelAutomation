package utilities;

import java.awt.Desktop;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testbase.BaseClass;

public class ExtentReportManager extends BaseClass implements ITestListener {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private String repName;

    @Override
    public void onStart(ITestContext testContext) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Test-Report-" + timeStamp + ".html";

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("./reports/" + repName);
        sparkReporter.config().setDocumentTitle("OpenCart Automation Report");
        sparkReporter.config().setReportName("OpenCart Functional Testing");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // System info
        extent.setSystemInfo("Application", "OpenCart");
        extent.setSystemInfo("Module", "Admin");
        extent.setSystemInfo("Sub Module", "Customers");
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");

        // Parameters from testng.xml
        String os = testContext.getCurrentXmlTest().getParameter("os");
        if (os != null) extent.setSystemInfo("Operating System", os);

        String browser = testContext.getCurrentXmlTest().getParameter("browser");
        if (browser != null) extent.setSystemInfo("Browser", browser);

        List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
        if (!includedGroups.isEmpty()) {
            extent.setSystemInfo("Groups", includedGroups.toString());
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        extentTest.assignCategory(result.getMethod().getGroups());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, result.getName() + " executed successfully");
        // ❌ No screenshot on success
    }

    @Override
    public void onTestFailure(ITestResult result) {
        if (test.get() == null) {
            ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
            extentTest.assignCategory(result.getMethod().getGroups());
            test.set(extentTest);
        }
        test.get().log(Status.FAIL, result.getName() + " failed");
        if (result.getThrowable() != null) {
            test.get().log(Status.INFO, result.getThrowable().getMessage());
        }
        // ✅ Screenshot only on failure
        attachScreenshot(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (test.get() == null) {
            ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
            extentTest.assignCategory(result.getMethod().getGroups());
            test.set(extentTest);
        }
        test.get().log(Status.SKIP, result.getName() + " skipped");
        if (result.getThrowable() != null) {
            test.get().log(Status.INFO, result.getThrowable().getMessage());
        }
        // ❌ No screenshot on skipped
    }

    @Override
    public void onFinish(ITestContext testContext) {
        if (extent != null) {
            extent.flush();
        }
        try {
            File reportFile = new File(System.getProperty("user.dir") + "/reports/" + repName);
            Desktop.getDesktop().browse(reportFile.toURI()); // auto-open in browser
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void attachScreenshot(ITestResult result) {
        try {
            // ✅ Use active driver from BaseClass
            String imgPath = captureScreen(result.getName());
            if (imgPath != null) {
                test.get().addScreenCaptureFromPath(imgPath);
            }
        } catch (Exception e) {
            test.get().log(Status.WARNING, "Screenshot not attached: " + e.getMessage());
        }
    }
}

        /*
        try {
            URL url = new URL("file:///" + System.getProperty("user.dir") + "\\reports\\" + repName);

            // Create the email message
            //sending email with the report as attachment
            ImageHtmlEmail email = new ImageHtmlEmail();
            email.setDataSourceResolver(new DataSourceUrlResolver(url));
            email.setHostName("smtp.googlemail.com"); //only for gmail
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("bhavsautomation06@gmail.com", "Bhavs@123"));
            email.setSSLOnConnect(true);
            email.setFrom("bhavsautomation06@gmail.com"); // Sender
            email.setSubject("Test Results");
            email.setMsg("Please find Attached Report...");
            email.addTo("stellaautomation06@gmail.com"); // Receiver
            //more than one receiver can combined email
            

            // Attach the report
            email.attach(url, "extent report", "please check report...");

            // Send the email
            email.send();
        } catch (Exception e) {
            e.printStackTrace(); // log the error
        }
        */

    