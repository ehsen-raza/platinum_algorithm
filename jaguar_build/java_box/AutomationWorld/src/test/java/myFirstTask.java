import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by eraza on 03/05/2019.
 */
public class myFirstTask {
    RemoteWebDriver chrDriver;
    @BeforeClass
    public void Setup_Browser(){
        WebDriverManager.chromedriver().setup();
        chrDriver = new ChromeDriver();
        chrDriver.manage().window().maximize();
        chrDriver.get("http://automationpractice.com/index.php");
    }

    @Test(priority = 0, enabled = true)
    public void Navigate_A_Site(){
        String strTitle = chrDriver.getTitle();
        assert strTitle.equalsIgnoreCase("My Store");
    }

    @Test (priority = 1, dependsOnMethods = "Navigate_A_Site", enabled = true)
    public void Navigate_A_Site_NEGATIVE(){
        String strTitle = chrDriver.getTitle();
        assert !(strTitle.equalsIgnoreCase("My Store AAAA"));
    }

    @Test (priority = 2)
    public void Verify_Sign_In_Element_Exist(){
        WebElement webElement = chrDriver.findElement(By.cssSelector(".login"));

        System.out.println(webElement.getText());
        assert !(webElement.getText().equalsIgnoreCase("Sign in"));
    }
    @AfterClass
    public void Tear_Down(){
        chrDriver.close();
        chrDriver.quit();
    }
}
