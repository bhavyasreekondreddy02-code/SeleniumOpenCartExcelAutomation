package pageObjects;




import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {
   

    public HomePage(WebDriver driver) {
    	super (driver);
        
    }
    
 @FindBy(xpath="//a[normalize-space()='My Account']")
 WebElement inkMyaccount;

 
 @FindBy(xpath="//a[normalize-space()='Register']")
 WebElement inkRegister;
 
 @FindBy(xpath ="//a[normalize-space()='Login']")
 WebElement inklogin;
 
 // Click on "My Account" 
 public void clickMyAccount()
 {
	 inkMyaccount.click();
	 
 }
 // Click on "Register" 
 public void clickRegister()
 {
	 inkRegister.click();
	 
 }
 public void clickLogin()
 {
	 inklogin.click();
	 
 }
}


   