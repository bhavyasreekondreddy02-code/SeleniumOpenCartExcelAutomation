package testcase;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.SearchPage;
import testbase.BaseClass;
import utilities.JsonDataProviders;

public class TC003_SearchTest extends BaseClass {

	
	@Test(dataProvider = "SearchData", dataProviderClass = JsonDataProviders.class)
	public void searchProductTest(String productName, String category) {
	    SearchPage searchPage = new SearchPage(getDriver());

	    searchPage.searchProduct(productName);

	    Map<String, String> productMap = searchPage.getProductTitleCategoryMap();
	    System.out.println("Product Map: " + productMap);

	    Assert.assertTrue(productMap.containsKey(productName),
	            "Product not found in search results: " + productName);

	    String actualCategory = productMap.get(productName);
	    Assert.assertTrue(actualCategory.toLowerCase().contains(category.toLowerCase()),
	            "Category mismatch for " + productName + " → Expected: " + category + ", Found: " + actualCategory);
	}
}