package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import testbase.BaseClass;
import java.util.List;





public class ExtentReportManager  extends BaseClass implements ITestListener {

    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public ExtentTest test;
    String repName;

    
    
    public void onStart(ITestContext testContext) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Test-Report-" + timeStamp + ".html";

        sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);
        sparkReporter.config().setDocumentTitle("opencart Automation Report");
        sparkReporter.config().setReportName("opencart Functional Testing");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // Project details
        extent.setSystemInfo("Application", "OpenCart");
        extent.setSystemInfo("Module", "Admin");
        extent.setSystemInfo("Sub Module", "Customers");
        // Current user details
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");

        // Parameters from testng.xml
        String os = testContext.getCurrentXmlTest().getParameter("os");
            extent.setSystemInfo("Operating System", os);
        

        String browser = testContext.getCurrentXmlTest().getParameter("browser");
            extent.setSystemInfo("Browser", browser);
        

        List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
        if (!includedGroups.isEmpty()) {
            extent.setSystemInfo("Groups", includedGroups.toString());
        }
    }

    //this method will execute when any test case get started
    //listerners will listen to the events and perform the actions based on the events.
    
    public void onTestSuccess(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.PASS, result.getName() + " got successfully executed");
    }

    
    
        
        public void onTestFailure(ITestResult result) {
            test = extent.createTest(result.getTestClass().getName());
            test.assignCategory(result.getMethod().getGroups());
            test.log(Status.FAIL, result.getName() + " got failed");
            test.log(Status.INFO, result.getThrowable().getMessage());

            // Capture screenshot
            try {
            String imgPath = new BaseClass().captureScreen(result.getName());
            test.addScreenCaptureFromPath(imgPath);
            }catch(IOException e) {
            	e.printStackTrace();
                
            }
        }
    
    
    public void onTestSkipped(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.SKIP, result.getName() + " got skipped");
        test.log(Status.INFO, result.getThrowable().getMessage());
        
    }

    //all tests are finished and reports are ready to be generated and opened in browser.
   
    public void onFinish(ITestContext testContext) {
        extent.flush(); //to write all the logs and information to the report file.

        String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;
        File extentReport = new File(pathOfExtentReport);

        try {
            Desktop.getDesktop().browse(extentReport.toURI());//open the report on browser
        } catch (IOException e) {
            e.printStackTrace();
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

    }
}
