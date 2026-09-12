package com.parabank.tests;

import com.parabank.pages.BillPayPage;
import com.parabank.pages.LoginPage;
import com.parabank.utils.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BillPayTest extends BaseTest {

    @Test
    public void testSuccessfulPayment_TC_P01() {
        login();

        BillPayPage billPayPage = new BillPayPage(driver, wait);
        billPayPage.goToBillPay();
        billPayPage.fillBillPayForm(
                "Electric Co", "123 Main St", "Lagos", "LA", "100001",
                "08011112222", "99999", "99999", "30"
        );
        billPayPage.clickSendPayment();

        Assert.assertTrue(billPayPage.isPaymentSuccessful(),
                "Bill payment did not complete successfully for valid details");
    }

    @Test
    public void testPaymentExceedsBalance_TC_P03() {
        login();

        BillPayPage billPayPage = new BillPayPage(driver, wait);
        billPayPage.goToBillPay();
        billPayPage.fillBillPayForm(
                "Electric Co", "123 Main St", "Lagos", "LA", "100001",
                "08011112222", "99999", "99999", "5000"
        );
        billPayPage.clickSendPayment();

        // KNOWN DEFECT (SCRUM-9 / TC-P03): ParaBank does NOT validate balance for Bill Pay either
        Assert.assertFalse(billPayPage.isPaymentSuccessful(),
                "DEFECT SCRUM-9: Payment was accepted despite amount exceeding available balance");
    }

    @Test
    public void testAccountNumberMismatch_TC_P06() {
        login();

        BillPayPage billPayPage = new BillPayPage(driver, wait);
        billPayPage.goToBillPay();
        billPayPage.fillBillPayForm(
                "Electric Co", "123 Main St", "Lagos", "LA", "100001",
                "08011112222", "11111", "22222", "10"
        );
        billPayPage.clickSendPayment();

        Assert.assertTrue(billPayPage.isAccountMismatchErrorShown(),
                "Expected 'account numbers do not match' error was not shown");
    }
}