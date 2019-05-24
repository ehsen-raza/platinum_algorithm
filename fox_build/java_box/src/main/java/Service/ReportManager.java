package Service;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ReportManager {

    private ExtentReports extentReports = null;
    private ExtentTest extentTest = null;

    public ReportManager()
    {
        extentReports = new ExtentReports();
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("Report1.html");
        htmlReporter.setAppendExisting(false);
        htmlReporter.config().setChartVisibilityOnOpen(false);
        extentReports.attachReporter(htmlReporter);
    }

    public void TestEnvironment()
    {
        extentReports.setSystemInfo("User : ", System.getProperty("user.name"));
        extentReports.setSystemInfo("OS : ", System.getProperty("os.name"));
        extentReports.setSystemInfo("JDK : ", System.getProperty("java.version"));
    }
    public void InitReport(String strTestName, String strTestDescription)
    {
         extentTest = extentReports.createTest(strTestName, strTestDescription);
    }
    public void LogTestStep(boolean bStatus, String strTestStep)
    {
        if(bStatus)
        {
            extentTest.log(Status.PASS, strTestStep);
            System.out.println("Pass : " + strTestStep);
        }else
        {
            extentTest.log(Status.FAIL, strTestStep);
            System.out.println("Fail : " + strTestStep);
        }
    }
    public void LogStepInfo(String strTestStep)
    {
        if( extentTest !=null)
        {
            extentTest.log(Status.INFO, strTestStep);
            System.out.println(strTestStep);
        }
    }
    public void EndReport()
    {
        if(extentTest!=null)
            extentReports.flush();
    }
}
