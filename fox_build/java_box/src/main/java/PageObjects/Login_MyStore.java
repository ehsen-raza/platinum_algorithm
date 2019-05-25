package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Login_MyStore
{
    WebDriver driver;

    By Create_Account_Email_Adress = By.name("email_create");
    By Create_Account_Button = By.name("SubmitCreate");
    By Already_Registered_Email_Address = By.xpath("//*[@id=\"email\"]");
    By Regsitered_User_Password = By.xpath("//*[@id=\"passwd\"]");
    By Singin_button = By.xpath("//*[@id=\"SubmitLogin\"]");
    public Login_MyStore( WebDriver driver)
    {
        this.driver = driver;
    }

    public void typeNewAccountEmailAddress(String EmailAddress)
    {
        driver.findElement(Create_Account_Email_Adress).clear();
        driver.findElement(Create_Account_Email_Adress).sendKeys(EmailAddress);
    }
    public void clickCreateAccountButton()
    {
        driver.findElement(Create_Account_Button).click();
    }

    public void typeRegisteredAccountEmailAddress (String Email)
    {
        driver.findElement(Already_Registered_Email_Address).clear();
        driver.findElement(Already_Registered_Email_Address).sendKeys(Email);
    }
    public void typePassword(String Password)
    {
        driver.findElement(Regsitered_User_Password).clear();
        driver.findElement(Regsitered_User_Password).sendKeys(Password);
    }
    public void clickSigninButton()
    {
        driver.findElement(Singin_button).click();
    }

}
