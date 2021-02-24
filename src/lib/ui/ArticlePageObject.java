package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

public class ArticlePageObject extends MainPageObject{

    private static final String
        FOOTER = "//*[@resource-id='pcs-footer-container-legal']",
        TITLE = "//*[@resource-id='pcs']//*[@class='android.view.View']//*",
        TITLE_DESCRY_TPL = "//*[@resource-id='pcs-edit-section-title-description'][@text='{SUBSTRING}']",
        ARTICLE_MENU_BOOKMARK = "//*[@resource-id='org.wikipedia:id/article_menu_bookmark']",
        ADD_TO_LIST_BUTTON = "//*[@text='ADD TO LIST']",
        CREATE_NEW = "//*[contains(@text,'Create new')]",
        INPUT_LIST_NAME = "//*[@resource-id='org.wikipedia:id/text_input'][@text='Name of this list']",
        OK_BUTTON = "//*[@class='android.widget.Button'][@text='OK']",
        LIST_NAME_TO_SAVE_ARTICLE_TPL = "//*[@resource-id='org.wikipedia:id/item_title'][@text='{LIST_NAME}']",
        SAVED_LIST = "//*[@resource-id='org.wikipedia:id/reading_list_contents']//*[@class='android.view.ViewGroup']",
        REMOVE_FROM_LIST = "//*[@resource-id='org.wikipedia:id/title'][contains(@text,'Remove from ')]",
        PAGE_LIST_TITLE_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='{SUBSTRING}']",
        NAVIGATE_UP = "//*[@content-desc='Navigate up']";

    public ArticlePageObject(AppiumDriver driver){
        super(driver);
    }

    public void swipeToFooter(){
        this.swipeUpToFindElement(By.xpath(FOOTER), "Can't find the end of the article",20);
    }

    /* TEMPLATES METHODS */
    private static String getTitleDescryXpath(String substring){
        return TITLE_DESCRY_TPL.replace("{SUBSTRING}", substring);
    }
    private static String getListXpath(String substring){
        return PAGE_LIST_TITLE_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getListNameXpath(String substring){
        return LIST_NAME_TO_SAVE_ARTICLE_TPL.replace("{LIST_NAME}", substring);
    }

    /* TEMPLATES METHODS */

    public void articleHasTitleDescry(String title){
        String title_xpath = getTitleDescryXpath(title);
        this.waitForElementPresent(By.xpath(title_xpath), "Title " + title + " is absent on the page." , 15);
    }


    public String getArticleTitle(){
        WebElement element = this.waitForElementPresent(By.xpath(TITLE),"Can't find title", 5);
        return element.getText();
    }

    public void addArticleToNewList(String new_list_name){
        this.waitForElementAndClick(
                By.xpath(ARTICLE_MENU_BOOKMARK),
                "Can't find button to open article_menu_bookmark",
                5);
        this.waitForElementAndClick(
                By.xpath(ADD_TO_LIST_BUTTON),
                "Can't find button option Add to another reading list",
                10);
        this.waitForElementAndClick(
                By.xpath(CREATE_NEW),
                "Can't find Create new field",
                5);
        this.waitForElementAndSendKeys(
                By.xpath(INPUT_LIST_NAME),
                new_list_name,
                "Can't find name input field",
                5);
        this.waitForElementAndClick(
                By.xpath(OK_BUTTON),
                "Can't find OK button",
                5);
        this.waitForElementAndClick(
                By.xpath(NAVIGATE_UP),
                "Can't find Back button",
                5);
    }

    public void addArticleToList(String list_name){
        this.waitForElementAndClick(
                By.xpath(ARTICLE_MENU_BOOKMARK),
                "Can't find button to open article_menu_bookmark",
                5);
        this.waitForElementAndClick(
                By.xpath(ADD_TO_LIST_BUTTON),
                "Can't find button option Add to another reading list",
                10);
        String list_name_xpath = getListNameXpath(list_name);
        this.waitForElementAndClick(
                By.xpath(list_name_xpath),
                "Can't find " + list_name,
                5);
        this.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_toolbar_button_show_overflow_menu']"),
                "Can't find toolbar button",
                5);
        this.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/overflow_feed'][@text='Home' or @text='Explore']"),
                "Can't find Explore button",
                5);
    }

    public void goToMyLists() {
        this.waitForElementAndClick(
                By.xpath("//*[@content-desc='My lists']"),
                "Can't find My lists",
                5);
    }

    public void selectListFromMyLists(String list_name) {
        this.waitForElementAndClick(
                By.xpath("//*[@text='" + list_name + "']"),
                "Can't find " + list_name + " in My lists",
                5);
    }

    public void listContainsArticles(int expected_number_of_articles) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int number_of_articles = getAmountOfElements(By.xpath(SAVED_LIST)) - 1;
        assert expected_number_of_articles == number_of_articles: "Number of articles" + number_of_articles + "is wrong";
    }

    public void removeArticleFromList(String article) {
        String xpathToArticle = this.getListXpath(article);
        this.waitForElementPresent(
                By.xpath(xpathToArticle),
                "Can't find " + article,
                5);
        this.swipeElementToLeft(By.xpath(xpathToArticle),"Can't swipe left");
    }

    public void clickArticleFromList(String article) {
        String xpathToArticle = this.getListXpath(article);
        this.waitForElementAndClick(
                By.xpath(xpathToArticle),
                "Can't select " + article,
                5);
    }
}
