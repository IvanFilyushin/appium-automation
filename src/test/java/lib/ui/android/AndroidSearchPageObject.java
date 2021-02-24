package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidSearchPageObject extends SearchPageObject {
    static {
                SEARCH_INIT_ELEMENT = "xpath://*[contains(@text,'Search Wikipedia')]";
                SEARCH_INPUT = "xpath://*[contains(@text,'Search Wikipedia')]";
                SEARCH_RESULT = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']";
                SEARCH_RESULT_ELEMENTS = "//*[@class='android.widget.TextView']";
                CLOSE_BUTTON = "/xpath:/*[@resource-id='org.wikipedia:id/search_close_btn']";
                SEARCH_LIST_TITLE_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='{SUBSTRING}']";
                SEARCH_LIST_TITLE_DESCRIPTION_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_description'][@text='{SUBSTRING}']";
    }

    public AndroidSearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
