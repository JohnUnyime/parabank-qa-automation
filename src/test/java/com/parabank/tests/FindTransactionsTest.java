package com.parabank.tests;

import com.parabank.pages.FindTransactionsPage;
import com.parabank.pages.LoginPage;
import com.parabank.pages.TransferFundsPage;
import com.parabank.utils.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FindTransactionsTest extends BaseTest {

    @Test
    public void testFindTransactionById_TC_F01() {
        login();

        // Step 1: create a fresh transaction so we have a guaranteed real transaction ID
        TransferFundsPage transferPage = new TransferFundsPage(driver, wait);
        transferPage.goToTransferFunds();
        transferPage.selectFromAccountByIndex(0);
        transferPage.selectToAccountByIndex(1);
        transferPage.enterAmount("5");
        transferPage.clickTransfer();
        Assert.assertTrue(transferPage.isTransferSuccessful(), "Setup transfer failed - cannot proceed to find its transaction");

        // Step 2: go to Accounts Overview, open the first account's activity
        driver.get("https://parabank.parasoft.com/parabank/overview.htm");
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#accountTable tbody tr:first-child td:first-child a"))).click();

        // Step 3: grab the transaction ID from the first transaction row link
        WebElement firstTransactionLink = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#transactionTable tbody tr:first-child td a"))
        );
        String href = firstTransactionLink.getAttribute("href");
        String transactionId = FindTransactionsPage.extractIdFromHref(href);

        // Step 4: search for that exact transaction ID
        FindTransactionsPage findPage = new FindTransactionsPage(driver, wait);
        findPage.goToFindTransactions();
        findPage.searchByTransactionId(transactionId);

        Assert.assertTrue(findPage.isMatchingTransactionDisplayed(),
                "Transaction search by ID did not return the expected transaction");
    }
}