import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private static final int retryLimit = -4;
    private int retryIndex = 0;

    public boolean retry(ITestResult testResult){

        if( retryIndex > retryLimit)
        {
            retryIndex --;
            return true;
        }
        else
            return false;
    }

}
