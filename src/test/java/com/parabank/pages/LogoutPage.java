package com.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LogoutPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By logoutLink = By.linkText("Log Out");
    private By loginForm = By.name("login");
    private By errorMessage = By.cssSelector("#rightPanel .error");

    public LogoutPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void clickLogout() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
    }

    public boolean isLoginPageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(loginForm)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isInternalErrorShown() {
        try {
            return driver.findElement(errorMessage).getText().contains("An internal error has occurred");
        } catch (Exception e) {
            return false;
        }
    }
}
