package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.android.AndroidSearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class ArticleTests extends CoreTestCase {

    @Test
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
    public void testAssertTitle(){
        String word = "Appium";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(word);
        searchPageObject.waitForTextInSearchResultAndClick(word);
        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        String article = articlePageObject.getArticleTitle();
        assertEquals(word, article);
    }
}
