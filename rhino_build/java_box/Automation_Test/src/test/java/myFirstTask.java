import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.*;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class myFirstTask {
    RemoteWebDriver chrDriver;
    private String Emailaddress = "sarmad5butt@contour.com";  // need to change email address everytime to run all three test cases successfully.
    private String LoginPassword = "123456789";

    @BeforeClass
    public void Setup_Browser(){

        /* WebDriverManager.firefoxdriver().setup();
        RemoteWebDriver firDriver = new FirefoxDriver();
        firDriver.get("http://automationpractice.com/index.php");*/

        WebDriverManager.chromedriver().setup();
        chrDriver = new ChromeDriver();
        chrDriver.get("http://automationpractice.com/index.php");
       // chrDriver.manage().window().maximize();

    }

    public void Going_Home(){

        WebElement homeLogo = chrDriver.findElementById("header_logo");
        chrDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        homeLogo.click();

    }

    public void signOutUser (){

        String loggedinTitle = chrDriver.getTitle();
        if(loggedinTitle.contains("My account"))
        {
            WebElement signOut = chrDriver.findElementByClassName("logout");
            signOut.click();
        }

    }



    @Test(priority = 0, enabled = true)
    public void Navigate_A_Site(){

         String strTitle = chrDriver.getTitle();
         assert strTitle.equalsIgnoreCase("My Store");

    }
  /*  @Test(priority = 1,enabled = true,dependsOnMethods = "Navigate_A_Site")
    public void Navigate_A_Site_Negative(){

        String strTitle = chrDriver.getTitle();
        assert !(strTitle.equalsIgnoreCase("My Sthhore"));


    }*/


    @Test (priority = 1,dependsOnMethods = "Navigate_A_Site")
    public void getLoginElement(){

        WebElement signIn = chrDriver.findElement(By.className("login"));
        System.out.println(signIn.getText());
        signIn.click();
        WebElement authLabel = chrDriver.findElement(By.className("page-heading"));
        assert authLabel.isDisplayed();
    }
    @Test (priority =2,dependsOnMethods = "getLoginElement")
    public void createAccount(){

       // Going_Home();
        WebElement  email = chrDriver.findElement(By.id("email_create"));
        email.click();
        chrDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        email.sendKeys(Emailaddress);
        WebElement createAccountButton = chrDriver.findElement(By.id("SubmitCreate"));
        createAccountButton.click();

        }
    @Test (priority = 3,dependsOnMethods = "createAccount")
    public void enterPersonalInfo(){

        WebElement piLabel = chrDriver.findElement(By.className("page-subheading"));
        piLabel.isDisplayed();

        //FirstName
        WebElement fName = chrDriver.findElementById("customer_firstname");
        fName.clear();
        fName.sendKeys("Sarmad");
        chrDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);


        //LastName
        WebElement lName = chrDriver.findElementById("customer_lastname");
        lName.clear();
        lName.sendKeys("Butt");
        chrDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);


        //Password
        WebElement password = chrDriver.findElement(By.id("passwd"));
        password.clear();
        password.sendKeys("123456789");
        chrDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);


        //Address
        WebElement address = chrDriver.findElement(By.id("address1"));
        address.sendKeys("128,54000,Contour");
        chrDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);


        //Input City.
        WebElement city = chrDriver.findElement(By.id("city"));
        city.clear();
        city.sendKeys("Lahore");
        chrDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);


        //Select State
        Select drpState = new Select(chrDriver.findElementById("id_state"));
        drpState.selectByVisibleText("Alabama");

        chrDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        System.out.println("drop down selected");

        //ZipCode
        WebElement zipCode = chrDriver.findElementById("postcode");
        zipCode.clear();
        zipCode.sendKeys("54000");
        chrDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);


        //MobileNumber
        WebElement mNumber = chrDriver.findElementById("phone_mobile");
        mNumber.clear();
        mNumber.sendKeys("03214442927");
        chrDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);


        //Alias Address
        WebElement aAddress = chrDriver.findElementById("alias");
        aAddress.clear();
        aAddress.sendKeys("10th Floor Contour office");
        chrDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);


        //RegisterButton
        WebElement RegisterButton = chrDriver.findElementById("submitAccount");
        chrDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        RegisterButton.click();
        System.out.println("Registration button clicked");
        chrDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        System.out.println("User Created with Email Address : " +Emailaddress );



        String strTitle2 = chrDriver.getTitle();
        assert strTitle2.equalsIgnoreCase("My account - My Store");
        System.out.println("Test Case Passed : User Created");

    }

    @Test (priority = 4,dependsOnMethods = "enterPersonalInfo")
    public void login(){

        signOutUser();
        chrDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Going_Home();
        chrDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        getLoginElement();
        chrDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement username = chrDriver.findElementById("email");
        username.clear();
        username.sendKeys(Emailaddress);
        chrDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement lPassword = chrDriver.findElementById("passwd");
        lPassword.clear();
        lPassword.sendKeys(LoginPassword);
        chrDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement loginButton = chrDriver.findElementById("SubmitLogin");
        loginButton.click();
        chrDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

        //Checking that user is logged in or not
        WebElement loginName = chrDriver.findElementByClassName("account");
        loginName.getText();
        assert loginName.isDisplayed();

        System.out.println("Test Case Passed : User Logged in Successfully");
    }

    @Test (priority = 5)
    public void  invalidUser(){

        signOutUser();
        chrDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Going_Home();
        chrDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        getLoginElement();
        WebElement username = chrDriver.findElementById("email");
        username.clear();
        username.sendKeys(Emailaddress);
        chrDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement lPassword = chrDriver.findElementById("passwd");
        lPassword.clear();
        lPassword.sendKeys("Random");
        chrDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement loginButton = chrDriver.findElementById("SubmitLogin");
        loginButton.click();
        chrDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

        // Checking for Error Message
        WebElement errorMessage = chrDriver.findElementById("center_column");
        String actualMessage = errorMessage.getText();
        System.out.println(actualMessage);
        assert actualMessage.contains("Authentication failed.");
        System.out.println("Test Case Passed : Error Message Verified");
    }

    @AfterClass
    public void CloseBrowser(){

        chrDriver.close();
        chrDriver.quit();
    }

}
