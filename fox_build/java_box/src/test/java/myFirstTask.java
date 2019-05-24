import PageObjects.Login_MyStore;
import Service.ReportManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class myFirstTask
{
    RemoteWebDriver driver;
    String BaseUrl = "http://automationpractice.com/index.php?controller=authentication";
    Random Rand = new Random();
    private int Number = Rand.nextInt(50000);
    private String Emailaddress = "irfan"+Number+"@gmail.com";
    private String Password = "12345";
    ReportManager reportManager = null;
    @Parameters("browser")
    @BeforeMethod

public void launchBrowser(String browser) throws Exception
    {


        if (browser.equalsIgnoreCase("firefox"))
        {
            System.out.println("launching firefox browser");
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
            driver.get(BaseUrl);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.manage().window().maximize();
        }
        else if (browser.equalsIgnoreCase("chrome"))
        {

            System.out.println("launching Chrome browser");
            WebDriverManager.chromedriver().clearPreferences();
            WebDriverManager.chromedriver().version("2.46").setup();
            driver = new ChromeDriver();
            driver.get(BaseUrl);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.manage().window().maximize();

    }
        else
            throw new Exception("\n Browser Not Found : Please Correct Browser Name in XML, it should be chrome or Fixfox");
   }

    @AfterMethod
    public void close()
    {
        driver.close();
        reportManager.EndReport();
    }
    @Test(priority = 0, enabled = true, retryAnalyzer = retry_API.class)
    public void Register()
    {



        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        reportManager = new ReportManager();
        reportManager.InitReport("User Registeration", "Page Loaded ");
        reportManager.TestEnvironment();

        //input email address
        WebElement email = driver.findElement(By.name("email_create"));
        email.clear();
        email.sendKeys(Emailaddress);
        //Click button.
        WebElement Create_Button = driver.findElement(By.name("SubmitCreate"));
        Create_Button.submit();
        //Input First Name
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement First_Name = driver.findElementByXPath("//*[@id=\"customer_firstname\"]");
        First_Name.clear();
        First_Name.sendKeys("irfan");
        //Input Last Name
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement Last_Name = driver.findElementByXPath("//*[@id=\"customer_lastname\"]");
        Last_Name.clear();
        Last_Name.sendKeys("ullah");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //Input password
        WebElement Password1 = driver.findElementByXPath("//*[@id=\"passwd\"]");
        Password1.clear();
        Password1.sendKeys(Password);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //Input Address
        WebElement Address = driver.findElementByXPath("//*[@id=\"address1\"]");
        Address.clear();
        Address.sendKeys("Air Line Society, Lahore");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //Input City.
        WebElement City = driver.findElementByXPath("//*[@id=\"city\"]");
        City.clear();
        City.sendKeys("Lahore");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //Select State
        Select drpState = new Select(driver.findElementByXPath("//*[@id=\"id_state\"]"));
        drpState.selectByVisibleText("Alabama");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        WebElement Zipcode = driver.findElementByXPath("//*[@id=\"postcode\"]");
        Zipcode.clear();
        Zipcode.sendKeys("12345");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement PhoneNumber = driver.findElementByXPath("//*[@id=\"phone_mobile\"]");
        PhoneNumber.clear();
        PhoneNumber.sendKeys("03444442935");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement AliasAddress = driver.findElementByXPath("//*[@id=\"alias\"]");
        AliasAddress.clear();
        AliasAddress.sendKeys("1122 Office Building");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement RegisterButton = driver.findElementByXPath("//*[@id=\"submitAccount\"]/span");
        RegisterButton.click();
        System.out.println("User Created with Email Address : " + Emailaddress);

        try {
            File file = new File("Credentials.txt");
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file,true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            bufferedWriter.newLine();
            bufferedWriter.write(formatter.format(date)+ " : ");
            bufferedWriter.write("\t" + Emailaddress);
            bufferedWriter.write("\t" + Password);
            System.out.println("File Successfully Created and Data is Written");
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        WebElement logout = driver.findElement(By.className("logout"));
        Assert.assertEquals(logout.getText(),"Sign out");
        logout.click();
        System.out.println("User Registeration Completed.");

    }
    @Test(priority = 1, enabled = true, retryAnalyzer = retry_API.class)
    public void login()
    {
        reportManager = new ReportManager();
        reportManager.InitReport("Login Verification ", "Login Verificatoin Started.");
        //reportManager.TestEnvironment();
        Login_MyStore login_myStore = new Login_MyStore(driver);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //WebElement username = driver.findElementByXPath("//*[@id=\"email\"]");
        //username.clear();
        //username.sendKeys(Emailaddress);
        login_myStore.typeRegisteredAccountEmailAddress(Emailaddress);
        System.out.println("Email: " + Emailaddress + "Password : " + Password);
       // WebElement Password2 = driver.findElementByXPath("//*[@id=\"passwd\"]");
        //Password2.clear();
        //Password2.sendKeys(Password);
        login_myStore.typePassword(Password);
        //WebElement Login_Button = driver.findElementByXPath("//*[@id=\"SubmitLogin\"]");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //Login_Button.click();
        login_myStore.clickSigninButton();
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        WebElement logout1 = driver.findElement(By.className("logout"));
        Assert.assertEquals(logout1.getText(),"Sign out");
        logout1.click();
        System.out.println("User Successfully Logged in and Logged Out.");

    }
    @Test(priority = 2, enabled = true, retryAnalyzer = retry_API.class)
    public void Invalid_Credentials()
    {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        reportManager = new ReportManager();
        reportManager.InitReport("Invalid Credentials", "Verifying Invalid Credentials Error Message");
        WebElement username = driver.findElementByXPath("//*[@id=\"email\"]");
        username.clear();
        username.sendKeys(Emailaddress);
        WebElement Password3 = driver.findElementByXPath("//*[@id=\"passwd\"]");
        Password3.clear();
        Password3.sendKeys("Random");
        WebElement Login_Button1 = driver.findElementByXPath("//*[@id=\"SubmitLogin\"]");
        Login_Button1.click();
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        String Actual_Error_Message = driver.findElementByXPath("//*[@id=\"center_column\"]/div[1]/ol/li").getText();
        String Expected_Error_Message = "Authentication failed.";
        Assert.assertEquals(Actual_Error_Message,Expected_Error_Message);
        System.out.println("Test Case Passed : Error Message Verified");
    }
}
