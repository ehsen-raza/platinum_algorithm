package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;

public class CreateAccountPage
{
    WebDriver driver;
    By First_Name = By.xpath("//*[@id=\"customer_firstname\"]");
    By Last_Name = By.xpath("//*[@id=\"customer_lastname\"]");;
   // By Email_Address;
    By Password = By.xpath("//*[@id=\"passwd\"]") ;
    By Address = By.xpath("//*[@id=\"address1\"]");
    By City = By.xpath("//*[@id=\"city\"]");
    By State = By.xpath("//*[@id=\"id_state\"]");
   // By Country ;
    By ZipCode = By.xpath("//*[@id=\"postcode\"]");
    By Mobile_Phone = By.xpath("//*[@id=\"phone_mobile\"]");
    By Alias_Address = By.xpath("//*[@id=\"alias\"]");
    By Register_button = By.xpath("//*[@id=\"submitAccount\"]/span");

    public CreateAccountPage(WebDriver driver)
    {
        this.driver = driver;
    }

    public void InputName(String FirstName, String LastName)
    {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(First_Name).clear();
        driver.findElement(First_Name).sendKeys(FirstName);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(Last_Name).clear();
        driver.findElement(Last_Name).sendKeys(LastName);
    }
    public void InputPassword(String password)
    {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(Password).sendKeys(password);
    }

    public void InputAddresses(String address, String city, String Alias)
    {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(Address).clear();
        driver.findElement(Address).sendKeys(address);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(Alias_Address).clear();
        driver.findElement(Alias_Address).sendKeys(Alias);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(City).clear();
        driver.findElement(City).sendKeys(city);

    }
    public void SelectState()
    {
        //Select State
        Select drpState = new Select(driver.findElement(State));
        drpState.selectByVisibleText("Alabama");
    }
    public void InputMobilePhone(String MobilePhone)
    {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(Mobile_Phone).clear();
        driver.findElement(Mobile_Phone).sendKeys(MobilePhone);
    }
    public void ZipCode(String Zip_Code)
    {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(ZipCode).clear();
        driver.findElement(ZipCode).sendKeys(Zip_Code);
    }
    public void ClickRegisterButton()
    {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(Register_button).click();
    }



}
