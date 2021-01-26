package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchPageObject extends MainPageObject{

    private static final String
            SEARCH_INIT_ELEMENT = "//*[contains(@text,'Search Wikipedia')]",
            SEARCH_INPUT = "//*[contains(@text,'Search Wikipedia')]",
            SEARCH_RESULT = "//*[@resource-id='org.wikipedia:id/search_results_list']",
            SEARCH_RESULT_ELEMENTS = "//*[@class='android.widget.TextView']",
            CLOSE_BUTTON = "//*[@resource-id='org.wikipedia:id/search_close_btn']",
            SEARCH_LIST_TITLE_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='{SUBSTRING}']",
            SEARCH_LIST_TITLE_DESCRIPTION_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_description'][@text='{SUBSTRING}']";

    public SearchPageObject(AppiumDriver driver){

        super(driver);
    }

    public void initSearchInput(){
        this.waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT), "Can't find and click init Search element", 5);
        this.waitForElementPresent(By.xpath(SEARCH_INPUT), "Can't find input search element after clicking init Search element", 5);
    }

    public void typeSearchLine(String search_line){
        this.waitForElementAndSendKeys(By.xpath(SEARCH_INPUT), search_line, "Can't input search line", 5);
    }

    public boolean searchHasResult(){
        WebElement search_result = this.waitForElementPresent(By.xpath(SEARCH_RESULT), "Search Results list doesn't found");
        List <WebElement> elements = search_result.findElements((By.xpath(SEARCH_RESULT_ELEMENTS)));
        return elements.size()>1;
    }

    public void searchHasNoResult(){
        waitForElementNotPresent(By.xpath(SEARCH_RESULT), "Search Results has elements",1);
    }

    public void closeSearch(){
        this.waitForElementAndClick(By.xpath(CLOSE_BUTTON), "Can't find Close Search Button", 5);
    }
    /* TEMPLATES METHODS */
    private static String getResultSearchListTitle(String substring){
        return SEARCH_LIST_TITLE_TPL.replace("{SUBSTRING}", substring);
    }
    private static String getResultSearchListTitleDescription(String substring){
        return SEARCH_LIST_TITLE_DESCRIPTION_TPL.replace("{SUBSTRING}", substring);
    }
    /* TEMPLATES METHODS */

    public void waitForTextInSearchResultAndClick(String text){
        String search_result_title_xpath = getResultSearchListTitle(text);
        this.waitForElementAndClick(By.xpath(search_result_title_xpath), "Can't find search result " + text,5);
    }
    public void waitForTextInSearchResultDescriptionAndClick(String text){
        String search_result_title_xpath = getResultSearchListTitleDescription(text);
        this.waitForElementAndClick(By.xpath(search_result_title_xpath), "Can't find search result description" + text,5);
    }
}
