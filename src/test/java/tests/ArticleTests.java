package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

@Epic("Test for article")
public class ArticleTests extends CoreTestCase {

    @Test
    @DisplayName("Swipe article to footer")
    @Description("We input search-word, open article and swipe to footer ")
    @Step("Starting test testSwipeArticle")
    @Severity(value = SeverityLevel.MINOR)
    public void testSwipeArticle() {
        String search_text = "Appium";
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search_text);
        searchPageObject.waitForTextInSearchResultAndClick(search_text);
        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.swipeToFooter();

    }

    @Test
    @DisplayName("Assert title description")
    @Description("We open article and compare title description with expected one")
    @Step("Starting test testAssertTitleDescription")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testAssertTitleDescription(){

        if (Platform.getInstance().isMW()){
            return;
        }
        String word = "Java";
        String title_descry = "Indonesian island";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(word);
        searchPageObject.waitForTextInSearchResultDescriptionAndClick(title_descry);
        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.articleHasTitleDescry(title_descry);
    }

    @Test
    @DisplayName("Compare article title with expected one")
    @Step("Starting step testAssertTitle")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testAssertTitle(){
        String word = "Appium";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(word);
        searchPageObject.waitForTextInSearchResultAndClick(word);
        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        String article = articlePageObject.getArticleTitle();
//        articlePageObject.takeStringShot("article_page");
        Assert.assertEquals(word, article);
    }
}
