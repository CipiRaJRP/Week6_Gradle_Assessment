package com.example.Selenium.data;

import com.example.Selenium.Record.User;
import com.example.Selenium.support.ConfigReader;

public class UserFactory {

    public static User validUser() {
        return new User(
                ConfigReader.get("email"),
                ConfigReader.get("password")
        );
    }
}
