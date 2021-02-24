package lib.ui.mobile_web;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWSearchPageObject extends SearchPageObject {
    static {
        SEARCH_INIT_ELEMENT = "id:searchIcon";
        SEARCH_INPUT = "css:form>input[type='search']";
        SEARCH_RESULT = "xpath://ul[@class='page-list thumbs actionable']";
        SEARCH_RESULT_ELEMENTS = "//li";
        CLOSE_BUTTON = "xpath://div[@class='header-action']//button[contains(@class,'cancel')]";
        SEARCH_LIST_TITLE_TPL = "xpath://li[@title='{SUBSTRING}']";
        SEARCH_LIST_TITLE_DESCRIPTION_TPL = "xpath://div[@class='wikidata-description' and text()='{SUBSTRING}']";
    }

    public MWSearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
