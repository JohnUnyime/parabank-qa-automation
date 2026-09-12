package com.parabank.tests;

import com.parabank.pages.LoginPage;
import com.parabank.pages.TransferFundsPage;
import com.parabank.utils.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TransferFundsTest extends BaseTest {

    @Test
    public void testSuccessfulTransfer_TC_T01() {
        login();

        TransferFundsPage transferPage = new TransferFundsPage(driver, wait);
        transferPage.goToTransferFunds();
        transferPage.selectFromAccountByIndex(0); // first account in dropdown
        transferPage.selectToAccountByIndex(1);   // second account in dropdown
        transferPage.enterAmount("20");
        transferPage.clickTransfer();

        Assert.assertTrue(transferPage.isTransferSuccessful(),
                "Transfer did not complete successfully for a valid amount");
    
        // Wait explicitly for EITHER the confirmation text OR enough time for navigation
        boolean success = transferPage.isTransferSuccessful();

        System.out.println("### URL after transfer: " + driver.getCurrentUrl());
        System.out.println("### Page text: " + driver.findElement(org.openqa.selenium.By.tagName("body")).getText());
        System.out.println("### isTransferSuccessful() returned: " + success);

        Assert.assertTrue(success, "Transfer did not complete successfully for a valid amount");
    }
    @Test
    public void testTransferExceedsBalance_TC_T02() {
        login();

        TransferFundsPage transferPage = new TransferFundsPage(driver, wait);
        transferPage.goToTransferFunds();
        transferPage.selectFromAccountByIndex(0);
        transferPage.selectToAccountByIndex(1);
        transferPage.enterAmount("999999");
        transferPage.clickTransfer();

        Assert.assertFalse(transferPage.isTransferSuccessful(),
                "DEFECT SCRUM-7: Transfer was accepted despite amount exceeding available balance");
    
        // This assertion documents the CORRECT expected behavior, so this test
        // will legitimately FAIL until the underlying application bug is fixed.
    }

    @Test
    public void testNegativeAmountReversesDirection_TC_T05() {
        login();

        TransferFundsPage transferPage = new TransferFundsPage(driver, wait);
        transferPage.goToTransferFunds();
        transferPage.selectFromAccountByIndex(0);
        transferPage.selectToAccountByIndex(1);
        transferPage.enterAmount("-50");
        transferPage.clickTransfer();

        boolean success = transferPage.isTransferSuccessful();
        System.out.println("### TC-T05 isTransferSuccessful() returned: " + success);

        Assert.assertFalse(success,
                "DEFECT SCRUM-8: Negative amount was accepted instead of being rejected");
    }
}