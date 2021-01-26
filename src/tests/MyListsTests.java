package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {

    @Test
    public void testSaveArticlesToMyList() {
        String word = "Java";
        String title = "Java (programming language)";
        String article_title_descry_one = "Object-oriented programming language";
        String article_title_descry_two = "Indonesian island";
        String list_name = "Java list";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(word);
        searchPageObject.waitForTextInSearchResultDescriptionAndClick(article_title_descry_one);
        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.articleHasTitleDescry(article_title_descry_one);
        articlePageObject.addArticleToNewList(list_name);
        searchPageObject.waitForTextInSearchResultDescriptionAndClick(article_title_descry_two);
        articlePageObject.addArticleToList(list_name);
        articlePageObject.goToMyLists();
        articlePageObject.selectListFromMyLists(list_name);
        articlePageObject.listContainsArticles(2);
        articlePageObject.selectListFromMyLists(list_name);
        articlePageObject.removeArticleFromList(word);
        articlePageObject.listContainsArticles(1);
        articlePageObject.clickArticleFromList(title);
        articlePageObject.articleHasTitleDescry(article_title_descry_one);
    }

}
