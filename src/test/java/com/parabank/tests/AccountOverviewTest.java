package com.parabank.tests;

import com.parabank.pages.AccountOverviewPage;
import com.parabank.utils.BaseTest;

import org.testng.Assert;
import org.testng.annotations.Test;

public class AccountOverviewTest extends BaseTest {

    @Test
    public void testAccountsAndBalancesDisplayed_TC_A01() {

        login(); // inherited from BaseTest, uses config.properties credentials

        AccountOverviewPage accountPage = new AccountOverviewPage(driver, wait);

        // Navigate to Accounts Overview
        accountPage.goToAccountsOverview();

        // Verify Accounts Overview table
        Assert.assertTrue(
                accountPage.isAccountsTableDisplayed(),
                "Accounts Overview table is not displayed"
        );

        // Verify at least one account exists
        int accountCount = accountPage.getAccountCount();

        Assert.assertTrue(
                accountCount > 0,
                "No accounts are displayed"
        );

        // Get first account balance
        String balance = accountPage.getFirstAccountBalance();

        // Verify balance is displayed
        Assert.assertFalse(
                balance.isBlank(),
                "Account balance is blank"
        );

        // Verify balance contains a number
        String numericBalance = balance.replaceAll("[^0-9.-]", "");

        Assert.assertFalse(
                numericBalance.isBlank(),
                "Account balance does not contain a numeric value"
        );
    }
}