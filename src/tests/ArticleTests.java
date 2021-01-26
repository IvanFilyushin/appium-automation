package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class ArticleTests extends CoreTestCase {

    @Test
    public void testSwipeArticle() {
        String search_text = "Appium";
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search_text);
        searchPageObject.waitForTextInSearchResultAndClick(search_text);
        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.swipeToFooter();

    }

    @Test
    public void testAssertTitleDescription(){
        String word = "Java";
        String title_descry = "Indonesian island";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(word);
        searchPageObject.waitForTextInSearchResultDescriptionAndClick(title_descry);
        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.articleHasTitleDescry(title_descry);
    }

    @Test
    public void testAssertTitle(){
        String word = "Appium";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(word);
        searchPageObject.waitForTextInSearchResultAndClick(word);
        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        String article = articlePageObject.getArticleTitle();
        assertEquals(word, article);
    }
}
