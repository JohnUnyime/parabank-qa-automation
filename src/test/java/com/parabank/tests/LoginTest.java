package com.parabank.tests;

import com.parabank.pages.LoginPage;
import com.parabank.utils.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void testValidLogin_TC_L01() {
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.enterUsername("Firmin");
        loginPage.enterPassword("Firm1234");
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.isWelcomeMessageDisplayed(), "Welcome message was not displayed after login");
    }

    @Test
    public void testInvalidUsername_TC_L02() {
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.enterUsername("wrongUsername123");
        loginPage.enterPassword("definitelyWrongPass999");
        loginPage.clickLogin();

        System.out.println("### TC-L02 URL: " + driver.getCurrentUrl());
        System.out.println("### TC-L02 Page text: " + driver.findElement(org.openqa.selenium.By.tagName("body")).getText());

        String actualError = loginPage.getErrorMessage();
        Assert.assertEquals(actualError, "The username and password could not be verified.",
                "Error message did not match expected text for invalid username");
    }

    @Test
    public void testInvalidPassword_TC_L03() {
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.enterUsername("Firmin");
        loginPage.enterPassword("wrongPassword123");
        loginPage.clickLogin();

        System.out.println("### TC-L03 URL: " + driver.getCurrentUrl());
        System.out.println("### TC-L03 Page text: " + driver.findElement(org.openqa.selenium.By.tagName("body")).getText());

        String actualError = loginPage.getErrorMessage();
        Assert.assertEquals(actualError, "The username and password could not be verified.",
                "Error message did not match expected text for invalid password");
    }
}