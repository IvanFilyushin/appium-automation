package lib.ui;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import io.qameta.allure.Attachment;
import lib.Platform;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

public class MainPageObject {

    protected RemoteWebDriver driver;

    public MainPageObject(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public WebElement waitForElementPresent(String locator, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        By by = this.getLocatorByString(locator);
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }
    public WebElement waitForElementPresent(String locator, String error_message){
        return waitForElementPresent(locator,error_message,5);
    }
    public WebElement waitForElementAndClick(String locator, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.click();
        return element;
    }
    public WebElement waitForElementAndSendKeys(String locator, String value, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }
    public boolean waitForElementNotPresent(String locator, String error_message, long timeoutInSeconds){
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }
    public WebElement waitForElementAndClear(String locator, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    public void assertElementHasText(String locator, String text, String error_message){
        By by = this.getLocatorByString(locator);
        WebElement element = driver.findElement(by);
        String elements_text = element.getText();
        Assert.assertTrue(
                error_message,
                elements_text.contains(text)
        );
    }

    protected void swipeUp(int timeOfSwipe) {
        if (driver instanceof AppiumDriver) {
            AppiumDriver driver = (AppiumDriver) this.driver;

            TouchAction action = new TouchAction(driver);
            Dimension size = driver.manage().window().getSize();
            int x = size.width / 2;
            int start_y = (int) (size.height * 0.8);
            int end_y = (int) (size.height * 0.2);
            action
                    .press(PointOption.point(x, start_y))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(timeOfSwipe)))
                    .moveTo(PointOption.point(x, end_y))
                    .release()
                    .perform();
        } else {
            System.out.println("Method Swipe Up does nothing for Platform");
        }

    }

    protected void swipeUpQuik(){
        swipeUp(200);
    }

    public void swipeUpToFindElement(String locator, String error_message, int maxSwipes){
        By by = this.getLocatorByString(locator);
        int already_swiped = 0;
        while (driver.findElements(by).size()==0){
            if (already_swiped>maxSwipes){
                waitForElementPresent(locator, "Can't find element by swiping up \n" + error_message, 0);
                return;
            }
            swipeUpQuik();
            ++ already_swiped;
        };
    }

    protected void swipeElementToLeft(String locator, String error_message){

        if (driver instanceof AppiumDriver) {
            WebElement element = waitForElementPresent(locator, error_message, 5);
            int left_x = element.getLocation().getX();
            int right_x = left_x + element.getSize().getWidth();
            int upper_y = element.getLocation().getY();
            int low_y = upper_y + element.getSize().getHeight();
            int middle_y = (upper_y + low_y) / 2;
            AppiumDriver driver = (AppiumDriver) this.driver;
            TouchAction action = new TouchAction(driver);
            action
                    .press(PointOption.point(right_x, middle_y))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(150)))
                    .moveTo(PointOption.point(left_x, middle_y))
                    .release()
                    .perform();
        } else {
            System.out.println("Method swipeElementToLeft does nothing for Platform" + Platform.getInstance().getPlatformVar());
        }
    }

    public int getAmountOfElements(String locator){
        By by = this.getLocatorByString(locator);
        List elements = driver.findElements(by);
        return elements.size();
    }

    public void assertElementPresent(String locator, String error_messages){
        int count = getAmountOfElements(locator);
        Assert.assertEquals(error_messages + "Elements locator:" + locator, count, 1);
    }

    private By getLocatorByString(String locator_with_type){
        String[] exploded_locator = locator_with_type.split(Pattern.quote(":"), 2);
        String by_type = exploded_locator[0];
        String locator = exploded_locator[1];

        if (by_type.equals("xpath")){
            return By.xpath(locator);
        }
        else if (by_type.equals("id")){
            return By.id(locator);
        }
        else if (by_type.equals("css")){
            return By.cssSelector(locator);
        }
        else {
            throw new IllegalArgumentException("Cannot get type of locator. Locator" + locator_with_type);
        }
    }

    public boolean isElementLocatedOnTheScreen(String locator){
        int element_location_by_y =
                this.waitForElementPresent(locator,"Cannot find element by locator").getLocation().getY();
        if (Platform.getInstance().isMW()) {
            JavascriptExecutor JavaExecutor = (JavascriptExecutor) driver;
            Object js_result = JavaExecutor.executeScript("return window.pageYOffset");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            element_location_by_y -= Integer.parseInt(js_result.toString());
        }
        int screen_size_by_y = driver.manage().window().getSize().getHeight();
        return element_location_by_y<screen_size_by_y;
    }


    public void scrollWebPageUp(){
        if (Platform.getInstance().isMW()){
            JavascriptExecutor JavaExecutor = (JavascriptExecutor) driver;
            JavaExecutor.executeScript("window.scrollBy(0,250)");
        } else {
            System.out.println("Method scrollWebPageUp does nothing for Platform" + Platform.getInstance().getPlatformVar());
        }
    }

    public void scrollWebPageTillElementNotVisible(String locator, String error_message, int max_scroll){
        int already_scroll = 0;
        WebElement element = this.waitForElementPresent(locator,error_message);

        while (!this.isElementLocatedOnTheScreen(locator)){
            scrollWebPageUp();
            already_scroll++;
            if (already_scroll>max_scroll){
                Assert.assertTrue(error_message, element.isDisplayed());
            }
        }
    }
    public boolean isElementPresent(String locator){
        return getAmountOfElements(locator)>0;
    }

    public void tryClickElementWithFewAttempt(String locator, String error_messages, int max_attempt) {
        int current_attempts = 0;
        boolean need_more_attempt = true;

        while (need_more_attempt) {
            try {
                this.waitForElementAndClick(locator, error_messages, 1);
                need_more_attempt = false;
            } catch (Exception e) {
                if (current_attempts > max_attempt) {
                    this.waitForElementAndClick(locator, error_messages, 1);
                }
            }
            ++current_attempts;
        }
    }
    public String takeStringShot(String name){
        TakesScreenshot ts = (TakesScreenshot) this.driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "/" + name + "screenshot.png";
        try {
            FileUtils.copyFile(source, new File(path));
            System.out.println("The screenshot was taken" + path);
        } catch (Exception e){
            System.out.println("Cannot take screenshot. Error: " + e.getMessage());
        }
        return path;
    }


    @Attachment
    public static byte[] screenshot(String path){
        byte[] bytes = new byte[0];
        try {
                bytes = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            System.out.println("Cannot get bytes from screenshot. Error: " + e.getMessage());
        }
        return bytes;
    }
}


