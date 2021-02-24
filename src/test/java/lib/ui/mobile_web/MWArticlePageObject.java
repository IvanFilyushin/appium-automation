package lib.ui.mobile_web;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWArticlePageObject extends ArticlePageObject {
    static {
        FOOTER = "id:footer-places";
        TITLE = "id:section_0";
        TITLE_DESCRY_TPL = "xpath://div[@class='page-heading']/h1[@text='{SUBSTRING}']";
        ARTICLE_MENU_BOOKMARK = "xpath://*[@resource-id='org.wikipedia:id/article_menu_bookmark']";
        ADD_TO_LIST_BUTTON = "xpath://a[@title='Watch']";
        CREATE_NEW = "xpath://*[contains(@text,'Create new')]";
        INPUT_LIST_NAME = "xpath://*[@resource-id='org.wikipedia:id/text_input'][@text='Name of this list']";
        OK_BUTTON = "xpath://*[@class='android.widget.Button'][@text='OK']";
        LIST_NAME_TO_SAVE_ARTICLE_TPL = "xpath://*[@resource-id='org.wikipedia:id/item_title'][@text='{LIST_NAME}']";
        SAVED_LIST = "xpath://*[@resource-id='org.wikipedia:id/reading_list_contents']//*[@class='android.view.ViewGroup']";
        REMOVE_FROM_LIST = "xpath://*[@resource-id='org.wikipedia:id/title'][contains(@text;'Remove from ')]";
        PAGE_LIST_TITLE_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='{SUBSTRING}']";
        NAVIGATE_UP = "xpath://label[@for='main-menu-input']";
        SHOW_MENU = "xpath://*[@resource-id='org.wikipedia:id/page_toolbar_button_show_overflow_menu']";
        EXPLORE_BUTTON = "xpath://*[@resource-id='org.wikipedia:id/overflow_feed'][@text='Home' or @text='Explore']";
        LIST_FROM_MY_LIST = "xpath://*[@text='{LIST_NAME}']";
        MY_LISTS = "xpath://*[@content-desc='My lists']";
        OPTIONS_REMOVE_FROM_LIST_BUTTON = "xpath://a[@title='Remove this page from your watchlist']";
        WATCH_BUTTON = "xpath://a[@title='Watch']";
    }
    public MWArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
