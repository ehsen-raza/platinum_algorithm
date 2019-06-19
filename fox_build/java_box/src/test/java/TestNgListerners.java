import Service.ReportManager;
import org.testng.*;

public class TestNgListerners implements ITestListener, ISuiteListener
{
    public static ReportManager reportManager = null;

    public static ReportManager getReportManager(){
        return reportManager;
    }
    @Override
    public void onTestStart(ITestResult iTestResult)
    {
        System.out.println("****************Test Started**********" + iTestResult.getTestName());
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult)
    {
        System.out.println("****************Test was Successful **********" + iTestResult.getTestName());
    }

    @Override
    public void onTestFailure(ITestResult iTestResult)
    {

        System.out.println("****************Test Failed **********" + iTestResult.getTestName());
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult)
    {
        System.out.println("****************Test Skipped **********" + iTestResult.getTestName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult)
    {
        System.out.println("****************Failure within Success Percentage **********" + iTestResult.getTestName());
    }

    @Override
    public void onStart(ITestContext iTestContext)
    {

    }

    @Override
    public void onFinish(ITestContext iTestContext)
    {

    }

    @Override
    public void onStart(ISuite iSuite) {
        reportManager = new ReportManager();
    }

    @Override
    public void onFinish(ISuite iSuite) {

    }
}
