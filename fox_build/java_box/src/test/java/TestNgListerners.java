import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestNgListerners implements ITestListener
{
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
}
