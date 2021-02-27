package lib;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;

public class CoreTestCase{

    protected RemoteWebDriver driver;
    private static final String SkipButton = "//*[@resource-id='org.wikipedia:id/fragment_onboarding_skip_button']";

    @Before
    @Step("Run driver and session")
    public void setUp() throws Exception {
        driver = Platform.getInstance().getDriver();
        this.createAllurePropertyFile();
        this.openWIkiForMobileWeb();
        if (Platform.getInstance().isAndroid()) {
            WebElement element;
            element = driver.findElement
                    (By.xpath(SkipButton));
            element.click();
        }
    }

    @After
    @Step("Remove driver and session")
    public void tearDown(){
        driver.quit();
    }

    private void openWIkiForMobileWeb(){
        if (Platform.getInstance().isMW()){
            driver.get("https://en.m.wikipedia.org");
        } else {
            System.out.println("Method openWIkiForMobileWeb do nothing for Platform" + Platform.getInstance().getPlatformVar());
        }
    }

    private void createAllurePropertyFile(){
        String path = System.getProperty("allure.results.directory");
        try {
            Properties props = new Properties();
            FileOutputStream fos = new FileOutputStream(path + "/environment.properties");
            props.setProperty("Environment", Platform.getInstance().getPlatformVar());
            props.store(fos,"See: https://github.com/allure-framework/allure-app/wiki/Environment");
            fos.close();
        } catch (Exception e) {
            System.out.println("IO error when writing Allure properties file");
            e.printStackTrace();
        }
    }
}
