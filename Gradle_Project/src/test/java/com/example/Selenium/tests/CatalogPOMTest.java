package com.example.Selenium.tests;

import com.example.Selenium.pages.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
public class CatalogPOMTest extends BaseTest{
    // Verify that catalog search returns only products
// matching the user's search query.
    @Test
    @DisplayName("POM search query returns only matching catalog titles")
    void searchFindOnlyMatchiongProducts(){
        CatalogPage catalogPage = new CatalogPage(driver)
                .open()
                .searchFor("headphones","Showing 1 product");

        List<String> titles= catalogPage.titles();

        assertAll(
                ()->assertFalse(titles.isEmpty(),"search returned no products"),
                ()->assertTrue(titles.stream()
                   .allMatch((title)->title.toLowerCase()
                   .contains("headphones")),"search result not related to headphones")
        );

        System.out.println(titles);
    }

    //Verify that the user can sort the products in the catalog page based on
    // price:Low to High
    @Test
    @DisplayName("POM sort query based on the price")
    void sortProductsBasedOnthePrice(){
        CatalogPage catalogPage = new CatalogPage(driver)
                .open()
                .sortBy("Price: Low to High");

        List<Integer> prices = catalogPage.prices();
        assertEquals(prices.stream().sorted().toList(),prices);

    }

    // Verify an end-to-end customer purchase journey using
// the Page Object Model workflow.
    @Test
    @DisplayName("POM full journey")
    void full_journey_of_all_stages(){
        CatalogPage catalogPage = new CatalogPage(driver)
                .open()
                .searchFor("headphones");

        ProductPage productPage = catalogPage.openFirstProduct();
            assertTrue(productPage.name().toLowerCase().contains("headphones"));

        CartPage cartPage = productPage.addToCart();
        cartPage.header().cartBadge().expectedCount(1);

        assertAll(
                ()->assertEquals(1,cartPage.lineCount()),
                ()->assertFalse(cartPage.total().isBlank())
        );
        cartPage.switchBackToCatalog().openFirstProduct().addToCart();

        assertAll(
                ()->assertEquals(2,cartPage.lineCount()),
                ()->assertFalse(cartPage.total().isBlank())
        );

        cartPage.header().cartBadge().expectedCount(2);
      String Confirmation = cartPage.proceed().placeOrder().confirmationText();

        assertTrue(Confirmation.toLowerCase().contains("confirmed"));


    }

    //Verify that the user can make a valid login
    @Test
    @DisplayName("Validate the Login")
    void loginPageValidation(){
        HomePage home = new HomePage(driver);
        LoginPage login = home.open();
        String text = login.validateTitle();

        System.out.println(text);
    }
}
