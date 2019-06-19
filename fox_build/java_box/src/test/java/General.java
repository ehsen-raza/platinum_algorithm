import PageObjects.CreateAccountPage;
import PageObjects.Login_MyStore;
import Service.CloudManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by eraza on 17/06/2019.
 */
public class General {
    String BaseUrl = "http://automationpractice.com/index.php?controller=authentication";
    CloudManager cloudManager = new CloudManager();
    RemoteWebDriver driver;
    Random Rand = new Random();
    private int Number = Rand.nextInt(50000);
    private String Emailaddress = "irfan"+Number+"@gmail.com";
    private String Password = "12345";
    String Get_Tunnel_ID;

    @BeforeClass
    public void Expe_Task14() throws Exception {

        cloudManager.vLaunch_SC();
        Thread.sleep(30000);
        this.Get_Tunnel_ID = cloudManager.GetTunnelID();
        System.out.println(cloudManager.TunnelStatus(Get_Tunnel_ID));
    }

    @Test
    public void Exp_Task15(){

            driver = cloudManager.wdFirefoxEnv();
            String sessionID = driver.getSessionId().toString();
            System.out.println("launching chrome browser");
            cloudManager.vSendTestNameToSauce(sessionID, "Register a user");
            driver.get(BaseUrl);
            //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.manage().window().maximize();

            CreateAccountPage createAccountPage = new CreateAccountPage(driver);
            Login_MyStore login_myStore = new Login_MyStore(driver);

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
        System.out.println("User Registration Completed.");
    }
    @AfterClass
    public void closeTunnel(){
        driver.close();
        cloudManager.DeactivateTunnel(Get_Tunnel_ID);
    }
}
