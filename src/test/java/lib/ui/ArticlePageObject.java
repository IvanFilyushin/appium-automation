package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.concurrent.TimeUnit;

abstract public class ArticlePageObject extends MainPageObject{

    protected static String
        FOOTER,
        TITLE,
        TITLE_DESCRY_TPL,
        ARTICLE_MENU_BOOKMARK,
        ADD_TO_LIST_BUTTON,
        CREATE_NEW,
        INPUT_LIST_NAME,
        OK_BUTTON,
        LIST_NAME_TO_SAVE_ARTICLE_TPL,
        SAVED_LIST,
        REMOVE_FROM_LIST,
        OPTIONS_REMOVE_FROM_LIST_BUTTON,
        PAGE_LIST_TITLE_TPL,
        NAVIGATE_UP,
        SHOW_MENU ,
        EXPLORE_BUTTON ,
        LIST_FROM_MY_LIST,
        MY_LISTS,
        WATCH_BUTTON;

    private String login = "IvanFilyushin";
    private String password = "flymetothemoon";

    public ArticlePageObject(RemoteWebDriver driver){
        super(driver);
    }

    public void swipeToFooter(){
        if (Platform.getInstance().isAndroid()) {
            this.swipeUpToFindElement(
                    FOOTER,
                    "Can't find the end of the article",
                    20);
        } else {
            this.scrollWebPageTillElementNotVisible(
                    FOOTER,
                    "Cannot find footer",
                    40);
        }
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

    private static String getListFromMyList(String substring){
        return LIST_FROM_MY_LIST.replace("{LIST_NAME}", substring);
    }

    /* TEMPLATES METHODS */

    public void articleHasTitleDescry(String title){
        String title_xpath = getTitleDescryXpath(title);
        this.waitForElementPresent(title_xpath, "Title " + title + " is absent on the page." , 15);
    }


    public String getArticleTitle(){
        screenshot(this.takeStringShot("article_title"));
        WebElement element = this.waitForElementPresent(TITLE,"Can't find title", 5);
        return element.getText();
    }

    public void addArticleToNewList(String new_list_name){
        if (Platform.getInstance().isAndroid()) {
            this.waitForElementAndClick(
                    ARTICLE_MENU_BOOKMARK,
                    "Can't find button to open article_menu_bookmark",
                    5);
        } else {
            this.waitForElementAndClick(
                    WATCH_BUTTON,
                    "Can't find Watch Button",
                    5);
        }

        if (Platform.getInstance().isMW()){
            AuthorizationPageObject Auth = new AuthorizationPageObject(driver);
            Auth.clickAuthButton();
            Auth.enterLoginData(login, password);
            Auth.submitForm();
        }
        this.waitForElementAndClick(
                ADD_TO_LIST_BUTTON,
                "Can't find button option Add to another reading list",
                10);

        if (Platform.getInstance().isAndroid()) {
            this.waitForElementAndClick(
                    CREATE_NEW,
                    "Can't find Create new field",
                    15);
            this.waitForElementAndSendKeys(
                    INPUT_LIST_NAME,
                    new_list_name,
                    "Can't find name input field",
                    5);
            this.waitForElementAndClick(
                    OK_BUTTON,
                    "Can't find OK button",
                    5);
        }
        this.waitForElementAndClick(
                NAVIGATE_UP,
                "Can't find Back button",
                5);
    }

    public void addArticleToList(String list_name){
        this.waitForElementAndClick(
                ARTICLE_MENU_BOOKMARK,
                "Can't find button to open article_menu_bookmark",
                5);
        this.waitForElementAndClick(
                ADD_TO_LIST_BUTTON,
                "Can't find button option Add to another reading list",
                10);
        String list_name_xpath = getListNameXpath(list_name);
        this.waitForElementAndClick(
                list_name_xpath,
                "Can't find " + list_name,
                5);
        this.waitForElementAndClick(
                SHOW_MENU,
                "Can't find toolbar button",
                5);
        this.waitForElementAndClick(
                EXPLORE_BUTTON,
                "Can't find Explore button",
                5);
    }

    public void goToMyLists() {
        this.waitForElementAndClick(
                MY_LISTS,
                "Can't find My lists",
                5);
    }

    public void selectListFromMyLists(String list_name) {
        String locator = getListFromMyList(list_name);
        this.waitForElementAndClick(
                locator,
                "Can't find " + list_name + " in My lists",
                5);
    }

    public void listContainsArticles(int expected_number_of_articles) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int number_of_articles = getAmountOfElements(SAVED_LIST) - 1;
        assert expected_number_of_articles == number_of_articles: "Number of articles" + number_of_articles + "is wrong";
    }

    public void removeArticleFromList(String article) {
        String xpathToArticle = this.getListXpath(article);
        this.waitForElementPresent(
                xpathToArticle,
                "Can't find " + article,
                5);
        this.swipeElementToLeft(xpathToArticle,"Can't swipe left");
    }

    public void clickArticleFromList(String article) {
        String xpathToArticle = this.getListXpath(article);
        this.waitForElementAndClick(
                xpathToArticle,
                "Can't select " + article,
                5);
    }

    public void removeArticleFromSavedListIfItAdded(){
        if (this.isElementPresent(OPTIONS_REMOVE_FROM_LIST_BUTTON)){
            this.waitForElementAndClick(
                    OPTIONS_REMOVE_FROM_LIST_BUTTON,
                    "Cannot click button to delete Article from List",
                    5);
            this.waitForElementPresent(
                    ADD_TO_LIST_BUTTON,
                    "Cannot find button to add Article to List after removing it",
                    5);
        }
    }
}
