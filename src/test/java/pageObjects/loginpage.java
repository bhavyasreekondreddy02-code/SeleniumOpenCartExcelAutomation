package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class loginpage  extends BasePage
{
	  public loginpage(WebDriver driver) {
	    	super (driver);
	        
	    }
	    
	 @FindBy(xpath="//input[@id='input-email']")
	 WebElement txtEmailAddress;

	 
	 @FindBy(xpath="//input[@id='input-password']")
	 WebElement txtPassword;
	 
	 @FindBy(xpath ="//input[@value='Login']")
	 WebElement btnlogin;
	 
	 // Set the Email address"
	 public void  setEmail(String email)
	 {
		 txtEmailAddress.sendKeys(email);
		 
	 }
	 // Click on "Register" 
	 public void  setPassword(String pwd)
	 {
		 txtPassword.sendKeys(pwd);
		 
	 }
	 public void clickLogin()
	 {
		 btnlogin.click();
		 
	 }
	}





