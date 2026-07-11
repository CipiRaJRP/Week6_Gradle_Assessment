package com.example.Selenium.tests;

import com.example.Selenium.pages.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RefactoringTest {

    private static final Logger log =
            LoggerFactory.getLogger(RefactoringTest.class);

    //Common Validation for All the Pages
    private void validatePageObject(Class<?> pageClass) {

        log.info("Validating Page Object: {}", pageClass.getSimpleName());

        // Validate locator fields
        List<Field> locators = Arrays.stream(pageClass.getDeclaredFields())
                .filter(field -> field.getType().equals(By.class))
                .toList();

        assertAll("Locator validation",

                () -> assertTrue(
                        locators.stream()
                                .allMatch(field -> Modifier.isPrivate(field.getModifiers())),
                        "All locators should be private"
                ),

                () -> assertTrue(
                        locators.stream()
                                .allMatch(field -> Modifier.isStatic(field.getModifiers())),
                        "All locators should be static"
                ),

                () -> assertTrue(
                        locators.stream()
                                .allMatch(field -> Modifier.isFinal(field.getModifiers())),
                        "All locators should be final"
                )
        );

        // Validate fluent methods
        List<Method> fluentMethods = Arrays.stream(pageClass.getDeclaredMethods())
                .filter(method -> method.getReturnType().equals(pageClass))
                .toList();

        assertAll("Fluent method validation",

                () -> assertTrue(
                        fluentMethods.stream()
                                .allMatch(method -> Modifier.isPublic(method.getModifiers())),
                        "All fluent methods should be public"
                ),

                () -> assertTrue(
                        fluentMethods.stream()
                                .allMatch(method -> method.getReturnType().equals(pageClass)),
                        "Methods should return the same page type"
                )
        );
    }

    @ParameterizedTest(name = "Validate {0}")
    @ValueSource(classes = {
            HomePage.class,
            LoginPage.class,
            CatalogPage.class,
            ProductPage.class,
            CartPage.class,
            CheckoutPage.class
    })

    @DisplayName("Validate Page Object Standards")
    void validateAllPageObjects(Class<?> pageClass) {
        validatePageObject(pageClass);
    }
}