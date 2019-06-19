import PageObjects.CreateAccountPage;
import PageObjects.Login_MyStore;
import Service.ReportManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

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


    @Parameters("browser")
    @BeforeClass

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

    @AfterClass
    public void close()
    {
        driver.close();

    }
    @Test(priority = 0, enabled = true, retryAnalyzer = retry_API.class)
    public void Register()
    {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        TestNgListerners.getReportManager().InitReport("User Registeration", "Page Loaded ");
        TestNgListerners.getReportManager().TestEnvironment();
        TestNgListerners.getReportManager().LogStepInfo("Registeration Started");

        CreateAccountPage createAccountPage = new CreateAccountPage(driver);
        Login_MyStore login_myStore = new Login_MyStore(driver);
        //input email address
        login_myStore.typeNewAccountEmailAddress(Emailaddress);
        //Click button.
        login_myStore.clickCreateAccountButton();

        //Input First Name and Last Name and Other Registeration Details
        createAccountPage.InputName("Irfan", "Ullah");
        createAccountPage.InputAddresses("1001B City Towers", "Lahore", "Air Line Society");
        createAccountPage.InputPassword("12345");
        createAccountPage.InputMobilePhone("03444442935");
        createAccountPage.SelectState();
        createAccountPage.ZipCode("12345");
        createAccountPage.ClickRegisterButton();
        TestNgListerners.getReportManager().LogStepInfo("User Registered");
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
        TestNgListerners.getReportManager().LogStepInfo("Credentials added to File");
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        WebElement logout = driver.findElement(By.className("logout"));
        Assert.assertEquals(logout.getText(),"Sign out");
        logout.click();
        System.out.println("User Registeration Completed.");
        TestNgListerners.getReportManager().LogTestStep(true, "User Successfully Registered");
        TestNgListerners.getReportManager().EndReport();

    }
    @Test(priority = 1, dependsOnMethods = "Register", enabled = true, retryAnalyzer = retry_API.class)
    public void login()
    {

        TestNgListerners.getReportManager().InitReport("Login Verification ", "Login Verificatoin Started.");
        TestNgListerners.getReportManager().TestEnvironment();
        Login_MyStore login_myStore = new Login_MyStore(driver);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        login_myStore.typeRegisteredAccountEmailAddress(Emailaddress);
        System.out.println("Email: " + Emailaddress + "Password : " + Password);
        login_myStore.typePassword(Password);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        login_myStore.clickSigninButton();
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        WebElement logout1 = driver.findElement(By.className("logout"));
        Assert.assertEquals(logout1.getText(),"Sign out");
        logout1.click();
        System.out.println("User Successfully Logged in and Logged Out.");
        TestNgListerners.getReportManager().LogTestStep(true,"User Successfully Logged in and Logged Out.");
        TestNgListerners.getReportManager().EndReport();

    }
    @Test(priority = 2, dependsOnMethods = "login", enabled = true, retryAnalyzer = retry_API.class)
    public void Invalid_Credentials()
    {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        TestNgListerners.getReportManager().InitReport("Invalid Credentials", "Verifying Invalid Credentials Error Message");
        TestNgListerners.getReportManager().LogStepInfo("Page Loaded");
        TestNgListerners.getReportManager().TestEnvironment();
        Login_MyStore login_myStore = new Login_MyStore(driver);
        login_myStore.typeRegisteredAccountEmailAddress(Emailaddress);
        login_myStore.typePassword("IncorrectPassword");
        TestNgListerners.getReportManager().LogStepInfo("Wrong Credentails Added");
        login_myStore.clickSigninButton();
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        String Actual_Error_Message = driver.findElementByXPath("//*[@id=\"center_column\"]/div[1]/ol/li").getText();
        String Expected_Error_Message = "Authentication failed.";
        Assert.assertEquals(Actual_Error_Message,Expected_Error_Message);
        System.out.println("Test Case Passed : Error Message Verified");
        TestNgListerners.getReportManager().LogTestStep(true,"Error Message Verified");
        TestNgListerners.getReportManager().EndReport();

    }
}
