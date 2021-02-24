package lib;

import io.appium.java_client.AppiumDriver;
import junit.framework.TestCase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class CoreTestCase extends TestCase {

    protected RemoteWebDriver driver;
    private static final String SkipButton = "//*[@resource-id='org.wikipedia:id/fragment_onboarding_skip_button']";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        driver = Platform.getInstance().getDriver();
        this.openWIkiForMobileWeb();
        if (Platform.getInstance().isAndroid()) {
            WebElement element;
            element = driver.findElement
                    (By.xpath(SkipButton));
            element.click();
        }

    }

    @Override
    protected void tearDown() throws Exception {
        driver.quit();
        super.tearDown();
    }

    private void openWIkiForMobileWeb(){
        if (Platform.getInstance().isMW()){
            driver.get("https://en.m.wikipedia.org");
        } else {
            System.out.println("Method openWIkiForMobileWeb do nothing for Platform" + Platform.getInstance().getPlatformVar());
        }
    }


}
