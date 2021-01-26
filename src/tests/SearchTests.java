package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class SearchTests extends CoreTestCase {

    @Test
    public void testSearchOneWord() {
        String word = "Java";
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(word);
        assertTrue("No search result for '" + word + "'", searchPageObject.searchHasResult());
        searchPageObject.closeSearch();
        searchPageObject.searchHasNoResult();
    }

}
