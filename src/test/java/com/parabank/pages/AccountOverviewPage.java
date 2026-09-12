package com.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountOverviewPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By accountsOverviewLink = By.cssSelector("a[href='overview.htm']");
    private By accountsTable = By.cssSelector("table");
    private By accountRows = By.cssSelector("table tbody tr");
    private By balanceCells = By.cssSelector("table tbody tr td:nth-child(2)");

    public AccountOverviewPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void goToAccountsOverview() {
        // If already on the Accounts Overview page, do nothing
        if (driver.getCurrentUrl().contains("overview.htm")) {
            return;
        }
        // Otherwise, click the link to navigate there
        wait.until(
                ExpectedConditions.elementToBeClickable(accountsOverviewLink)
        ).click();
        System.out.println("Current URL after registration attempt: " + driver.getCurrentUrl());
        System.out.println("Page source snippet: " + driver.findElement(org.openqa.selenium.By.tagName("body")).getText().substring(0, Math.min(300, driver.findElement(org.openqa.selenium.By.tagName("body")).getText().length())));
    }
    
    
    // Verify accounts table
    public boolean isAccountsTableDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(accountsTable)
        ).isDisplayed();
    }

    // Get number of accounts
    public int getAccountCount() {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(accountsTable)
        );

        return driver.findElements(accountRows).size();
    }

    // Get first account balance
    public String getFirstAccountBalance() {
        WebElement balance = wait.until(
                ExpectedConditions.visibilityOfElementLocated(balanceCells)
        );

        return balance.getText();
    }
}