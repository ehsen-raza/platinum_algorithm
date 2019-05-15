import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.concurrent.TimeUnit;

public class Assignment1 {

   RemoteWebDriver chrDriver;

    @BeforeClass
    public void Setup_Browser() {
        WebDriverManager.chromedriver().setup();
        chrDriver = new ChromeDriver();
    }

    @Test(groups = "a")
    public void navigate_home_page()
    {

        chrDriver.manage().window().maximize();
        chrDriver.get("http://automationpractice.com/index.php");
        String expectedTitle = "My Store";
        String actualTitle = chrDriver.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle);
    }

    @Test(groups = "b", dependsOnGroups = "a")
    public void verify_user_registration() {
        // click on Sign in link//
        chrDriver.findElementByClassName("login").click();
        //Enter 'email address in field for new account creation//
        chrDriver.findElementById("email_create").sendKeys("angilina122@gmail.com");
        // click 'create an account' button
        chrDriver.findElementById("SubmitCreate").click();
        chrDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //select title//
        chrDriver.findElementById("id_gender1").click();
        //enter first name//
       chrDriver.findElementById("customer_firstname").sendKeys("Angilina");
        //enter last name//
        chrDriver.findElementById("customer_lastname").sendKeys("william");
        //enter email address//
        //chrDriver.findElementById("email").sendKeys("angilinawilliam@hotmail.com");
        //enter passord//
        chrDriver.findElementById("passwd").sendKeys("password");
        //enter dob//
        chrDriver.findElementById("days").sendKeys("15");
        chrDriver.findElementById("months").sendKeys("April");
        chrDriver.findElementById("years").sendKeys("2008");
        //choose option signup for newsletter//
        chrDriver.findElementById("newsletter").click();
        //enter mailing address//
        chrDriver.findElementById("firstname").sendKeys("angilina");
        chrDriver.findElementById("lastname").sendKeys("william");
        chrDriver.findElementById("company").sendKeys("contour");
        chrDriver.findElementById("address1").sendKeys("7th floor city tower");
        chrDriver.findElementById("city").sendKeys("Lahore");
        chrDriver.findElementById("id_state").sendKeys("california");
        chrDriver.findElementById("postcode").sendKeys("04234");
        chrDriver.findElementById("id_country").sendKeys("Pakistan");
        chrDriver.findElementById("other").sendKeys("just testing");
        chrDriver.findElementById("phone").sendKeys("23094893204890");
        chrDriver.findElementById("phone_mobile").sendKeys("03246949743");
        chrDriver.findElementById("alias").clear();
        chrDriver.findElementById("alias").sendKeys("angilinawilliam@gmail.com");
        chrDriver.findElementById("submitAccount").click();
        //logout from application to login again
        chrDriver.findElementByClassName("logout").click();

        // login with invalid credentials
        chrDriver.findElementByClassName("login").click();
        chrDriver.findElementById("email").sendKeys("angilinawilliam122@gmil.com");
        chrDriver.findElementById("passwd").sendKeys("password1");
        chrDriver.findElementById("SubmitLogin").click();

        //login with newly created user info

        chrDriver.findElementByClassName("login").click();
        chrDriver.findElementById("email").sendKeys("angilinawilliam122@gmil.com");
        chrDriver.findElementById("passwd").sendKeys("password");
        chrDriver.findElementById("SubmitLogin").click();
    }
}