package com.example.Selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends  BasePage{
    private static final By TITLE = By.id("login-title");
    private static final By EMAIL = By.id("email");
    private static final By PASSWORD = By.id("password");
    private static final By SIGN_IN = By.cssSelector("button[type='submit']");
    protected LoginPage(WebDriver driver) {
        super(driver);
    }

    public String validateTitle(){
        return text(TITLE);
    }

    public HomePage signInWith(String email,String password){
        visible(EMAIL);
        type(EMAIL,email);
        visible(PASSWORD);
        type(PASSWORD,password);
        click(SIGN_IN);
        return new HomePage(driver);
    }


}
