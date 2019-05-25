import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;


public class retry_API implements IRetryAnalyzer
{
    private int retryCount = 0;
    private static final int retryLimit = 3;
    public boolean retry(ITestResult result)
    {
        if(retryCount < retryLimit)
        {
            retryCount++;
            return true;
        }

        return false;
    }

}

