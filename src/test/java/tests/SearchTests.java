package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

public class SearchTests extends CoreTestCase {

    @Test
    @Description("Search a word in WIKI")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testSearchOneWord() {
        String word = "Java";
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(word);
        Assert.assertTrue("No search result for '" + word + "'", searchPageObject.searchHasResult());
        searchPageObject.closeSearch();
        searchPageObject.searchHasNoResult();
    }

}
