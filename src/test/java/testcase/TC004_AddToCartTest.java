package testcase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.*;
import testbase.BaseClass;
import utilities.CartData;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.util.List;

public class TC004_AddToCartTest extends BaseClass {

    @Test
    public void test_addToCart() throws Exception {
        logger.info("**** Starting TC004_AddToCartTest ****");

        // Load JSON test data
        InputStream is = getClass().getClassLoader().getResourceAsStream("testdata/cartdata.json");
        if (is == null) {
            throw new FileNotFoundException("cartdata.json not found in classpath under testdata/");
        }
        InputStreamReader reader = new InputStreamReader(is);

        Gson gson = new Gson();
        List<CartData> products = gson.fromJson(reader, new TypeToken<List<CartData>>() {}.getType());

        HomePage homePage = new HomePage(getDriver());

        for (CartData product : products) {
            String productName = product.getProductName();

            // Step 1: Search product
            SearchPage searchPage = homePage.enterSearch(productName).clickSearch();
            Assert.assertTrue(searchPage.isProductDisplayed(productName),
                    "Product not found in search results: " + productName);

            // Step 2: Click product → ProductPage
            ProductPage productPage = searchPage.clickOnProduct(productName);

            // Step 3: Add to cart → CartPage
            CartPage cartPage = productPage.clickAddToCart();

            // Step 4: Open cart and validate
            cartPage.openCart();
            boolean inCart = cartPage.isProductInCart(productName);
            Assert.assertTrue(inCart,
                    "Cart validation failed. Expected product: " + productName);
        }

        logger.info("**** Finished TC004_AddToCartTest ****");
    }
}
