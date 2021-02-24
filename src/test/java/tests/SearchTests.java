package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class SearchTests extends CoreTestCase {

    @Test
    public void testSearchOneWord() {
        String word = "Java";
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(word);
        assertTrue("No search result for '" + word + "'", searchPageObject.searchHasResult());
        searchPageObject.closeSearch();
        searchPageObject.searchHasNoResult();
    }

}
