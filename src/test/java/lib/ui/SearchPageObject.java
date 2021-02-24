package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.List;

abstract public class SearchPageObject extends MainPageObject{

    protected static String
            SEARCH_INIT_ELEMENT,
            SEARCH_INPUT,
            SEARCH_RESULT,
            SEARCH_RESULT_ELEMENTS,
            CLOSE_BUTTON ,
            SEARCH_LIST_TITLE_TPL,
            SEARCH_LIST_TITLE_DESCRIPTION_TPL;

    public SearchPageObject(RemoteWebDriver driver){
        super(driver);
    }

    public void initSearchInput(){
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Can't find and click init Search element", 5);
        this.waitForElementPresent(SEARCH_INPUT, "Can't find input search element after clicking init Search element", 5);
    }

    public void typeSearchLine(String search_line){
        this.waitForElementAndSendKeys(SEARCH_INPUT, search_line, "Can't input search line", 5);
    }

    public boolean searchHasResult(){
        WebElement search_result = this.waitForElementPresent(SEARCH_RESULT, "Search Results list doesn't found");
        List <WebElement> elements = search_result.findElements((By.xpath(SEARCH_RESULT_ELEMENTS)));
        return elements.size()>1;
    }

    public void searchHasNoResult(){
        waitForElementNotPresent(SEARCH_RESULT, "Search Results has elements",1);
    }

    public void closeSearch(){
        this.waitForElementAndClick(CLOSE_BUTTON, "Can't find Close Search Button", 5);
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
        this.waitForElementAndClick(search_result_title_xpath, "Can't find search result " + text,5);
    }
    public void waitForTextInSearchResultDescriptionAndClick(String text){
        String search_result_title_xpath = getResultSearchListTitleDescription(text);
        this.waitForElementAndClick(search_result_title_xpath, "Can't find search result description" + text,5);
    }
}
