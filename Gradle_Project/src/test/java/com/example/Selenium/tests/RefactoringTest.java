package com.example.Selenium.tests;

import com.example.Selenium.pages.CartPage;
import com.example.Selenium.pages.CatalogPage;
import com.example.Selenium.pages.CheckoutPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static java.lang.reflect.Modifier.*;
import static org.junit.jupiter.api.Assertions.*;
public class RefactoringTest {
    private static final Logger log =
            LoggerFactory.getLogger(RefactoringTest.class);

    // Verify that CatalogPage follows the framework's Page Object
// model for locator visibility and method design.
    @Test
    @DisplayName("Refracting Test case catalog")
    void refractoringForCatalog(){

         log.info("REfactoring the Catalog Page");

        List<Field> catalogLocators = Arrays.stream(CatalogPage.class.getDeclaredFields())
                .filter(field -> field.getType().equals(By.class))
                .toList();
        List<Integer> catalogModifiers = catalogLocators.stream()
                .map(modifier -> modifier.getModifiers())
                .toList();

        assertAll(
                ()->assertTrue(catalogModifiers.stream()
                        .allMatch(Private-> isPrivate(Private))),

                ()-> assertTrue(catalogModifiers.stream()
                        .allMatch(Static -> isStatic(Static))),

                ()-> assertTrue(catalogModifiers.stream()
                        .allMatch(Final ->isFinal(Final))));

        List<Method> catalogMethodModifiers = Arrays.stream(CatalogPage.class.getMethods())
                .filter(method -> method.getReturnType().equals(CatalogPage.class))
                .toList();
        for(Method method:catalogMethodModifiers){
            System.out.println(method);
        }
        assertAll(
                ()->assertTrue(catalogMethodModifiers.stream()
                        .allMatch(Public-> isPublic(Public.getModifiers()))),

                ()->assertTrue(catalogMethodModifiers.stream()
                        .allMatch(returnType ->returnType.getReturnType().equals(CatalogPage.class)))
        );
    }

    // Verify that CartPage follows the framework's Page Object
// model for locator visibility and method design.
    @Test
    @DisplayName("Refracting Test case cart")
    void refractoringForCart(){
        log.info("REfactoring the Cart Page");


        List<Field> catalogLocators = Arrays.stream(CartPage.class.getDeclaredFields())
                .filter(field -> field.getType().equals(By.class))
                .toList();
        List<Integer> catalogModifiers = catalogLocators.stream()
                .map(modifier -> modifier.getModifiers())
                .toList();
        assertAll(
                ()->assertTrue(catalogModifiers.stream()
                        .allMatch(Private-> isPrivate(Private))),

                ()-> assertTrue(catalogModifiers.stream()
                        .allMatch(Static -> isStatic(Static))),

                ()-> assertTrue(catalogModifiers.stream()
                        .allMatch(Final ->isFinal(Final))));

        List<Method> catalogMethodModifiers = Arrays.stream(CartPage.class.getMethods())
                .filter(method -> method.getReturnType().equals(CartPage.class))
                .toList();

        for(Method method:catalogMethodModifiers){
            System.out.println(method);
        }
        assertAll(
                ()->assertTrue(catalogMethodModifiers.stream()
                        .allMatch(Public-> isPublic(Public.getModifiers()))),

                ()->assertTrue(catalogMethodModifiers.stream()
                        .allMatch(returnType ->returnType.getReturnType().equals(CatalogPage.class)))
        );
    }

    // Verify that Checkout follows the framework's Page Object
// model for locator visibility and method design.
    @Test
    @DisplayName("Refracting Test case checkout")
    void refractoringForCheckout(){

        log.info("REfactoring the Checkout Page");


        List<Field> catalogLocators = Arrays.stream(CheckoutPage.class.getDeclaredFields())
                .filter(field -> field.getType().equals(By.class))
                .toList();
        List<Integer> catalogModifiers = catalogLocators.stream()
                .map(modifier -> modifier.getModifiers())
                .toList();
        //This is Field Modifier Assertion
        assertAll(
                ()->assertTrue(catalogModifiers.stream()
                        .allMatch(Private-> isPrivate(Private))),

                ()-> assertTrue(catalogModifiers.stream()
                        .allMatch(Static -> isStatic(Static))),

                ()-> assertTrue(catalogModifiers.stream()
                        .allMatch(Final ->isFinal(Final))));

        List<Method> catalogMethodModifiers = Arrays.stream(CheckoutPage.class.getMethods())
                .filter(method -> method.getReturnType().equals(CheckoutPage.class))
                .toList();
        for(Method method:catalogMethodModifiers){
            System.out.println(method);
        }
        assertAll(
                ()->assertTrue(catalogMethodModifiers.stream()
                        .allMatch(Public-> isPublic(Public.getModifiers()))),

                ()->assertTrue(catalogMethodModifiers.stream()
                        .allMatch(returnType ->returnType.getReturnType().equals(CheckoutPage.class)))
        );
    }
}
