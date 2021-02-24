package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MainPageObject {

    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver) {
        this.driver = driver;
    }

    public WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }
    public WebElement waitForElementPresent(By by, String error_message){
        return waitForElementPresent(by,error_message,5);
    }
    public WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }
    public WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }
    public boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }
    public WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    public void assertElementHasText(By by, String text, String error_message){
        WebElement element = driver.findElement(by);
        String elements_text = element.getText();
        Assert.assertTrue(
                error_message,
                elements_text.contains(text)
        );
    }

    protected void swipeUp(int timeOfSwipe){
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width/2;
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);
        action
                .press(PointOption.point(x, start_y))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(timeOfSwipe)))
                .moveTo(PointOption.point(x, end_y))
                .release()
                .perform();
    }

    protected void swipeUpQuik(){
        swipeUp(200);
    }

    public void swipeUpToFindElement(By by, String error_message, int maxSwipes){
        int already_swiped = 0;
        while (driver.findElements(by).size()==0){
            if (already_swiped>maxSwipes){
                waitForElementPresent(by, "Can't find element by swiping up \n" + error_message, 0);
                return;
            }
            swipeUpQuik();
            ++ already_swiped;
        };
    }

    protected void swipeElementToLeft(By by, String error_message){
        WebElement element = waitForElementPresent(by, error_message, 5);
        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int low_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + low_y) / 2;

        TouchAction action = new TouchAction(driver);
          action
          .press(PointOption.point(right_x, middle_y))
          .waitAction(WaitOptions.waitOptions(Duration.ofMillis(150)))
          .moveTo(PointOption.point(left_x, middle_y))
          .release()
          .perform();
    }

    public int getAmountOfElements(By by){
        List elements = driver.findElements(by);
        return elements.size();
    }

    public void assertElementPresent(By by, String error_messages){
        int count = getAmountOfElements(by);
        Assert.assertEquals(error_messages + "Elements locator:" + by.toString(), count, 1);
    }
}
