package Service;

import com.saucelabs.saucerest.SauceREST;
import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by eraza on 17/06/2019.
 */
public class CloudManager {
    static String USERNAME = "irfanullah";
    static String ACCESS_KEY = "003d923b-836f-477e-b585-c650ed94c744";
    private static final String URLAddress = "http://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:80/wd/hub";
    private HttpURLConnection hucConnection; // for use of http request.
    private InputStream isContent; // for read inputstream data from response of api request.
    private BufferedReader brIn; // for buffering the data from response.
    private int iResponseCode; // used for saved response code from api request.
    private StringBuffer strbResponse; //store string data from response.
    private JSONObject jObj;
    private String strLine; // for print / store data from response.
    private JSONParser jsParser; // for Json Parsor from response.
    private Object oObj; // for store json parsor data.
    private JSONArray jaArray; // for json values from response.
    private RemoteWebDriver wdChromeDriver = null;
    private RemoteWebDriver wdSafari = null;

    public void vLaunch_SC(){
        String strPath = ".\\src\\main\\resources\\SauceTunnel\\bin\\SC_Launch.bat";
        Runtime runtime = Runtime.getRuntime();

        try {
            runtime.exec("cmd /c" + strPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String GetTunnelIdentifier(){
        String strComputerName = System.getenv().get("COMPUTERNAME");
        String strUserName = System.getenv().get("USERNAME");
        return strUserName + "@" + strComputerName;
    }

    public String TunnelStatus(String strTunnel_ID) throws Exception {
        try {
            URL url = new URL("https://saucelabs.com/rest/v1/" + USERNAME + "/tunnels/" + strTunnel_ID);

            Base64 base = new Base64();
            String strEncoding = base.encodeAsString((USERNAME + ":" + ACCESS_KEY).getBytes());

            hucConnection = (HttpURLConnection) url.openConnection();
            hucConnection.setRequestMethod("GET");
            hucConnection.setDoOutput(true);
            hucConnection.setRequestProperty("Authorization", "Basic " + strEncoding);

            isContent = hucConnection.getInputStream();
            brIn = new BufferedReader(new InputStreamReader(isContent));

            iResponseCode = hucConnection.getResponseCode();
//            logger.info("\nSending 'GET' request to URL : " + url);
//            logger.info("Response code : " + iResponseCode);
            if (iResponseCode != 200) {
                System.out.println("Invalid Response Code");
                throw new Exception();
            }

            strbResponse = new StringBuffer();

            while ((strLine = brIn.readLine()) != null) {
                //logger.info("Tunnel IDs: " + strLine);
                strbResponse.append(strLine);
            }
            brIn.close();
            jObj= (JSONObject) JSONValue.parse(strbResponse.toString());
            return jObj.get("status").toString();


        } catch (Exception e) {
            e.printStackTrace();
            return "NULL";
        }
    }

    public String GetTunnelID() {
        String strResult="";
        try {
            URL url = new URL("https://saucelabs.com/rest/v1/" + USERNAME + "/tunnels");

            Base64 base = new Base64();
            String strEncoding = base.encodeAsString((USERNAME + ":" + ACCESS_KEY).getBytes());

            hucConnection = (HttpURLConnection) url.openConnection();
            hucConnection.setRequestMethod("GET");
            hucConnection.setDoOutput(true);
            hucConnection.setRequestProperty("Authorization", "Basic " + strEncoding);

            isContent = hucConnection.getInputStream();
            brIn = new BufferedReader(new InputStreamReader(isContent));

            iResponseCode = hucConnection.getResponseCode();
            // logger.info("\nSending 'GET' request to URL : " + url);
            // logger.info("Response code : " + iResponseCode);
            if (iResponseCode != 200) {
                System.out.println("Invalid Response Code");
                throw new EmptyStackException();
            }

            strbResponse = new StringBuffer();

            while ((strLine = brIn.readLine()) != null) {
                //logger.info("Tunnel IDs: " + strLine);
                strbResponse.append(strLine);
            }
            brIn.close();

            // Json Parser used because json response does not contain name of the value, it returns only value
            jsParser = new JSONParser();

            oObj = jsParser.parse(strbResponse.toString());
            jaArray = (JSONArray) oObj;

            System.out.println("Total number of active tunnel(s): " + jaArray.size());
            for (Object aJaArray : jaArray) {
                strResult = aJaArray.toString();

            }

        }catch (Exception e) {
            System.out.println("Problem in Sauce Tunnel Status");
        }
        return strResult;
    }

    public void DeactivateTunnel(String strTunnel_ID)
    {
        try {
            URL url = new URL("https://saucelabs.com/rest/v1/" + USERNAME + "/tunnels/" + strTunnel_ID);

            Base64 base = new Base64();
            String strEncoding = base.encodeAsString((USERNAME + ":" + ACCESS_KEY).getBytes());

            hucConnection = (HttpURLConnection) url.openConnection();
            hucConnection.setRequestMethod("DELETE");
            hucConnection.setDoOutput(true);
            hucConnection.setRequestProperty("Authorization", "Basic " + strEncoding);

            isContent = hucConnection.getInputStream();
            brIn = new BufferedReader(new InputStreamReader(isContent));

            iResponseCode = hucConnection.getResponseCode();
            System.out.println("\nSending 'Delete' request to URL : " + url);
            System.out.println("Response code : " + iResponseCode);
            if (iResponseCode != 200) {
                System.out.println("Invalid Response Code");
                throw new Exception();
            }

            strbResponse = new StringBuffer();

            while ((strLine = brIn.readLine()) != null) {
                //logger.info("Tunnel IDs: " + line);
                strbResponse.append(strLine);
            }
            brIn.close();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public RemoteWebDriver wdChromeEnv(){

        DesiredCapabilities caps = DesiredCapabilities.chrome();
        try {
            caps.setCapability("platform", "Windows 10");
            //caps.setCapability("screenResolution", "1280x1024");
            caps.setCapability("tunnelIdentifier", GetTunnelIdentifier());
            caps.setCapability("version", "74.0");
            caps.setCapability("seleniumVersion", "3.12.0");
            caps.setCapability("idleTimeout", 30);
            caps.setCapability("build", "fox_build_rc");
            wdChromeDriver = new RemoteWebDriver(new URL(URLAddress), caps);
        }
        catch (MalformedURLException exh){
            System.out.println("Chrome Driver Environment Fail");
        }
        return wdChromeDriver;
    }

    public RemoteWebDriver wdSafariEnv(){

        DesiredCapabilities caps = DesiredCapabilities.safari();
        try {
            caps.setCapability("platform", "macOS 10.14");
            //caps.setCapability("screenResolution", "1280x1024");
            caps.setCapability("tunnelIdentifier", GetTunnelIdentifier());
            caps.setCapability("version", "12.0");
            caps.setCapability("seleniumVersion", "3.12.0");
            caps.setCapability("idleTimeout", 30);
            caps.setCapability("build", "fox_build_rc");
            wdSafari = new RemoteWebDriver(new URL(URLAddress), caps);
        }
        catch (MalformedURLException exh){
            System.out.println("Chrome Driver Environment Fail");
        }
        return wdSafari;
    }

    public RemoteWebDriver wdFirefoxEnv(){

        DesiredCapabilities caps = DesiredCapabilities.firefox();
        try {
            caps.setCapability("platform", "Linux");
            //caps.setCapability("screenResolution", "1280x1024");
            caps.setCapability("tunnelIdentifier", GetTunnelIdentifier());
            caps.setCapability("version", "45.0");
            caps.setCapability("seleniumVersion", "3.12.0");
            caps.setCapability("idleTimeout", 30);
            caps.setCapability("build", "fox_build_rc");
            wdSafari = new RemoteWebDriver(new URL(URLAddress), caps);
        }
        catch (MalformedURLException exh){
            System.out.println("Chrome Driver Environment Fail");
        }
        return wdSafari;
    }

    public void vSendTestNameToSauce(String strSessionID,
                                     String strTestName)    {

        SauceREST srSauceObj = new SauceREST(USERNAME, ACCESS_KEY);
        Map<String, Object> mapUpdate = new HashMap<>();
        mapUpdate.put("name", strTestName);
        srSauceObj.updateJobInfo(strSessionID, mapUpdate);
        String strMessage = String.format("SauceOnDemandSessionID=%1$s job-name=%2$s",strSessionID, strTestName);
        System.out.println(strMessage);

    }
}
