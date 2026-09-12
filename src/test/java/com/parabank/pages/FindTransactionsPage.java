package com.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FindTransactionsPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By findTransactionsLink = By.linkText("Find Transactions");
    private By transactionIdField = By.id("transactionId");
    private By findByIdButton = By.id("findById");
    private By resultContainer = By.id("resultContainer");
    private By transactionRows = By.cssSelector("#transactionBody tr");

    public FindTransactionsPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void goToFindTransactions() {
        wait.until(ExpectedConditions.elementToBeClickable(findTransactionsLink)).click();
    }

    public void searchByTransactionId(String id) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(transactionIdField));
        field.clear();
        field.sendKeys(id);
        driver.findElement(findByIdButton).click();
    }

    public boolean isMatchingTransactionDisplayed() {
        try {
            WebElement result = new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(ExpectedConditions.visibilityOfElementLocated(resultContainer));
            return !driver.findElements(transactionRows).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    // Grabs the ID from the first transaction link's href on the current Account Activity page
    public static String extractIdFromHref(String href) {
        return href.substring(href.indexOf("id=") + 3);
    }
}