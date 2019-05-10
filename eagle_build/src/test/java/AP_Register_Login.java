import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class AP_Register_Login {

    private RemoteWebDriver chrmDriver;
    private String firstName;
    private String lastName;
    private String email;
    private String passwd;

    @BeforeClass
    @Parameters("browser")
    public void Setup_Driver(String browser) throws Exception {

        if(browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            chrmDriver = new ChromeDriver();
        }
        else if(browser.equalsIgnoreCase("firefox")){
            WebDriverManager.firefoxdriver().setup();
            chrmDriver = new FirefoxDriver();
        }
        else{
            //If no browser passed throw exception
            throw new Exception("Browser is not correct");
        }

        firstName = "M";
        lastName = "D";
        email = "MyNewEmailAddress@myMail.com";
        passwd = "Thisisfun...";
    }

    @Test(priority = 0)
    public void Navigate_Home() {

        chrmDriver.get("http://www.automationpractice.com/index.php?");
        assert HelperFnc_Compare_Text(chrmDriver.getTitle(), "My Store");
    }

    @Test(priority = 1, dependsOnMethods = "Navigate_Home")
    public void Navigate_Login(){

        chrmDriver.findElement(By.className("header_user_info")).click();
        assert HelperFnc_Compare_Text(chrmDriver.getTitle(), "Login - My Store");
    }

    @Test(priority = 2, dependsOnMethods = "Navigate_Login", enabled = false)
    public void Register_User(){

        assert HelperFnc_Compare_Text( HelperFnc_Page_Heading().getText(), "Authentication");

        chrmDriver.findElement(By.id("email_create")).sendKeys(email);
        chrmDriver.findElement(By.id("SubmitCreate")).click();

        assert HelperFnc_Contains_Text( chrmDriver.findElement(By.id("create_account_error")).getText(), "error");
        //Failure

        assert HelperFnc_Compare_Text( HelperFnc_Page_Heading().getText(), "Create an account");
        //Success
    }

    @Test(priority = 3, dependsOnMethods = "Register_User", enabled = false)
    public void Register_User_Personal_Info(){

        chrmDriver.findElement(By.id("id_gender1")).click();
        chrmDriver.findElement(By.id("customer_firstname")).sendKeys(firstName);
        chrmDriver.findElement(By.id("customer_lastname")).sendKeys(lastName);

        chrmDriver.findElement(By.id("email")).sendKeys(email);
        chrmDriver.findElement(By.id("id_gender1")).click();
        chrmDriver.findElement(By.id("passwd")).sendKeys(passwd);

        chrmDriver.findElement(By.id("days")).sendKeys("7");
        chrmDriver.findElement(By.id("months")).sendKeys("April");
        chrmDriver.findElement(By.id("years")).sendKeys("1998");


        chrmDriver.findElement(By.id("uniform-newsletter")).click();
        chrmDriver.findElement(By.id("uniform-optin")).click();

        chrmDriver.findElement(By.id("firstname")).click();
        chrmDriver.findElement(By.id("lastname")).click();
        chrmDriver.findElement(By.id("company")).sendKeys("COMPANY");

        chrmDriver.findElement(By.id("address1")).sendKeys("Street address, P.O. Box, Company name, etc.");
        //chrmDriver.findElement(By.id("address2")).sendKeys("Apartment, suite, unit, building, floor, etc...");
        //hidden field

        chrmDriver.findElement(By.id("city")).sendKeys("New York");
        chrmDriver.findElement(By.id("id_state")).sendKeys("New York");
        chrmDriver.findElement(By.id("postcode")).sendKeys("1001");
        chrmDriver.findElement(By.id("id_country")).click();

        chrmDriver.findElement(By.id("other")).sendKeys("Additional Information Sample Text");
        chrmDriver.findElement(By.id("id_state")).sendKeys("New York");

        chrmDriver.findElement(By.id("phone")).sendKeys("165843543");
        chrmDriver.findElement(By.id("phone_mobile")).sendKeys("9941358423512");

        chrmDriver.findElement(By.id("alias")).clear(); //clear default value
        chrmDriver.findElement(By.id("alias")).sendKeys("a.k.a");

        chrmDriver.findElement(By.id("submitAccount")).click();

        assert HelperFnc_Contains_Text( HelperFnc_Error().getText(), "error");
        //Failure

        assert HelperFnc_Compare_Text( chrmDriver.findElement(By.className("logout")).getText(), "sign out");
        //Success
    }

    @Test(priority = 4, dependsOnMethods = "User_Login_Success", enabled = false)
    public void User_Logout(){

        chrmDriver.findElement(By.className("logout")).click();
        assert HelperFnc_Compare_Text( HelperFnc_Page_Heading().getText(), "Authentication");
    }

    @Test(priority = 5, dependsOnMethods = "Navigate_Login", enabled = false)
    public void User_Login_Success(){

        chrmDriver.findElement(By.id("email")).sendKeys(email);
        chrmDriver.findElement(By.id("passwd")).sendKeys(passwd);

        assert !HelperFnc_Contains_Text( HelperFnc_Error().getText(), "error");
        //Failure
    }

    @Test(priority = 6, dependsOnMethods = "Navigate_Login", retryAnalyzer = RetryAnalyzer.class)
    public void User_Login_Failure(){

        chrmDriver.findElement(By.id("email")).sendKeys(email);
        chrmDriver.findElement(By.id("passwd")).sendKeys("Incorrectpassword");

        assert HelperFnc_Contains_Text( HelperFnc_Error().getText(), "error");
        //Failure
    }

    @AfterClass(alwaysRun = true)
    public void TearDown(){

        chrmDriver.close();
        chrmDriver.quit();
    }




    public  Boolean HelperFnc_Compare_Text(String value1, String value2){

        return value1.equalsIgnoreCase(value2);
    }

    public  Boolean HelperFnc_Contains_Text(String value1, String value2){

        return value1.contains(value2);
    }

    public WebElement HelperFnc_Page_Heading(){

        return chrmDriver.findElement(By.className("page-heading"));
    }

    public WebElement HelperFnc_Error(){

        return chrmDriver.findElement(By.className("alert-danger"));
    }

}
