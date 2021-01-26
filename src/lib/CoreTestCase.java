package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import junit.framework.TestCase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class CoreTestCase extends TestCase {

    protected AppiumDriver driver;
    private static final String
            AppiumUrl = "http://127.0.0.1:4723/wd/hub",
            SkipButton = "//*[@resource-id='org.wikipedia:id/fragment_onboarding_skip_button']";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        WebElement element;

        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","AndroidTestDevice");
        capabilities.setCapability("platformVersion","8.0.0");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app","C:\\TestAutomation\\Appium\\appium-automation\\apks\\org.wikipedia.apk");

        driver = new AndroidDriver(new URL(AppiumUrl), capabilities);
        element = driver.findElement
                (By.xpath(SkipButton));
        element.click();

    }

    @Override
    protected void tearDown() throws Exception {
        driver.quit();
        super.tearDown();
    }



}
