import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        WebElement element;

        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","AndroidTestDevice");
        capabilities.setCapability("platformVersion","8.0.0");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app","C:\\TestAutomation\\Appium\\appium-automation\\apks\\org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        element = driver.findElement
                (By.xpath("//*[@resource-id='org.wikipedia:id/fragment_onboarding_skip_button']"));
        element.click();

    }

    @After
    public void tearDown(){
        driver.quit();
    }

    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }
    private WebElement waitForElementPresent(By by, String error_message){
        return waitForElementPresent(by,error_message,5);
    }
    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }
    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }
    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }
    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    private void assertElementHasText(By by, String text, String error_message){
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
        action.press(x, start_y).waitAction(timeOfSwipe).moveTo(x,end_y).release().perform();
    }

    protected void swipeUpQuik(){
        swipeUp(200);
    }

    protected void swipeUpToFindElement(By by, String error_message, int maxSwipes){
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

    private int getAmountOfElements(By by){
        List elements = driver.findElements(by);
        return elements.size();
    }

    private void assertElementPresent(By by, String error_messages){
        int count = getAmountOfElements(by);
        Assert.assertEquals(error_messages + "Elements locator:" + by.toString(), count, 1);
    }

    @Test
    public void inputHasTextTest(){
        String locator = "//*[@resource-id='org.wikipedia:id/search_container']//*[@class='android.widget.TextView']";
        String text = "Search Wikipedia";

        WebElement element = waitForElementPresent(
                By.xpath(locator),
                "Can't find input field",
                5);

        assertElementHasText(
                By.xpath(locator),
                text,
                "Input field doesn't contain text '" + text + "'\n"
        );
    }

    @Test
    public void searchOneWordTest() {
        String word = "Appium";
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Can't find Search Wikipedia by Xpath",
                5);
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                word,
                "Can't find Search field",
                5);
        WebElement element=waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']"),
                "Results list doesn't found");
        Assert.assertTrue(
                "Word '" + word + "' doesn't found",
                element.findElements(By.xpath("//*[@class='android.widget.TextView']")).size()>1);
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_close_btn']"),
                "Can't find Close Button",
                5);
        waitForElementNotPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']"),
                "Search result didn't disappear",
                1);
    }
    @Test
    public void testSwipeArticle() {
        String word = "Appium";
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Can't find Search Wikipedia",
                5);
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                word,
                "Can't find Search field",
                5);
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "Can't find text: " + word,
                5);

        swipeUpToFindElement(By.xpath("//*[@resource-id='pcs-footer-container-legal']"),
                "Can't find the end of the article",
                20);

    }
    @Test
    public void testSaveFirstArticleToMyList() {
        String word = "Java";
        String article_title_one = "'Object-oriented programming language'";
        String article_title_two = "'Indonesian island'";
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Can't find Search Wikipedia",
                5);
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                word,
                "Can't find Search field",
                5);
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[contains(@text," + article_title_one +")]"),
                "Can't find " + article_title_one,
                5);
        waitForElementPresent(
                By.xpath("//*[@text="+article_title_one+"]"),
                "Can't pass to page with " + article_title_one,
                5);
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/article_menu_bookmark']"),
                "Can't find button to open article_menu_bookmark",
                5);
        waitForElementAndClick(
                By.xpath("//*[@text='ADD TO LIST']"),
                "Can't find button option Add to another reading list",
                10);
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Create new')]"),
                "Can't find Create new field",
                5);
        waitForElementAndSendKeys(
                By.xpath("//*[@resource-id='org.wikipedia:id/text_input'][@text='Name of this list']"),
                "Java list",
                "Can't find name input field",
                5);
        waitForElementAndClick(
                By.xpath("//*[@class='android.widget.Button'][@text='OK']"),
                "Can't find OK button",
                5);
        waitForElementAndClick(
                By.xpath("//*[@content-desc='Navigate up']"),
                "Can't find Back button",
                5);
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[contains(@text," + article_title_two +")]"),
                "Can't find " + article_title_two,
                5);
        waitForElementPresent(
                By.xpath("//*[@text="+article_title_two+"]"),
                "Can't pass to page with " + article_title_two,
                5);
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/article_menu_bookmark']"),
                "Can't find button to open article_menu_bookmark",
                5);
        waitForElementAndClick(
                By.xpath("//*[@text='ADD TO LIST']"),
                "Can't find button option Add to another reading list",
                10);
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/item_title'][@text='Java list']"),
                "Can't find Java list",
                5);
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_toolbar_button_show_overflow_menu']"),
                "Can't find toolbar button",
                5);
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/overflow_feed'][@text='Home' or @text='Explore']"),
                "Can't find Explore button",
                5);
        waitForElementAndClick(
                By.xpath("//*[@content-desc='My lists']"),
                "Can't find My lists",
                5);
        waitForElementAndClick(
                By.xpath("//*[@text='Java list']"),
                "Can't find Java list in My lists",
                5);
        waitForElementPresent(
                By.xpath(("//*[contains(@text,'2 of 2 articles available offline')]")),
                "List does not contain 2 articles");
        waitForElementAndClick(
                By.xpath("//*[@text='Java list']"),
                "Can't find Java list in My lists",
                5);
        waitForElementAndClick(
                By.xpath("//*[@text=" + article_title_one + "]"),
                "Can't select " + article_title_one,
                5);
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/article_menu_bookmark']"),
                "Can't find button to open article_menu_bookmark",
                5);
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/title'][@text='Remove from Java list']"),
                "Can't find button option Remove from reading list",
                5);
        waitForElementAndClick(
                By.xpath("//*[@content-desc='Navigate up']"),
                "Can't find Back button",
                5);
        waitForElementPresent(
                By.xpath("//*[contains(@text,'1 of 1 article available offline')]"),
                "List does not contain 1 article");
        waitForElementAndClick(
                By.xpath("//*[contains(@text," + article_title_two +")]"),
                "Can't find " + article_title_two,
                5);
        waitForElementPresent(
                By.xpath("//*[@text="+article_title_two+"]"),
                "Can't pass to page with " + article_title_two,
                5);
    }

    @Test
    public void assertTitleTest(){
        String word = "Java";
        String title = "'Indonesian island'";
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Can't find Search Wikipedia",
                5);
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                word,
                "Can't find Search field",
                5);
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[contains(@text," + title +")]"),
                "Can't find " + title,
                5);
        assertElementPresent(
                By.xpath("//*[@resource-id='pcs-edit-section-title-description'][@text=" + title + "]"),
                "Title " + title + " is absent on the page. "
        );
    }
}
