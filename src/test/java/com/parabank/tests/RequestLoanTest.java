package com.parabank.tests;

import com.parabank.pages.LoginPage;
import com.parabank.pages.RequestLoanPage;
import com.parabank.utils.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RequestLoanTest extends BaseTest {

    @Test
    public void testLoanApproved_TC_LN01() {
        login();

        RequestLoanPage loanPage = new RequestLoanPage(driver, wait);
        loanPage.goToRequestLoan();
        // Small down payment, comfortably within balance
        loanPage.applyForLoan("500", "10");

        Assert.assertTrue(loanPage.isApproved(),
                "Loan was not approved when down payment was well within available balance");
    }

    @Test
    public void testLoanDenied_TC_LN02() {
        login();

        RequestLoanPage loanPage = new RequestLoanPage(driver, wait);
        loanPage.goToRequestLoan();
        // Down payment far exceeds any realistic balance
        loanPage.applyForLoan("50000", "100000");

        Assert.assertFalse(loanPage.isApproved(),
                "Loan was approved despite down payment exceeding available balance");
    }

    @Test
    public void testDownPaymentExceedsLoanAmount_TC_LN03() {
        login();

        RequestLoanPage loanPage = new RequestLoanPage(driver, wait);
        loanPage.goToRequestLoan();
        // Down payment exceeds loan amount, but is still within balance
        loanPage.applyForLoan("10", "50");

        // BUSINESS LOGIC FINDING (not a defect): ParaBank only validates down payment
        // against balance, not against loan amount. This should be approved.
        Assert.assertTrue(loanPage.isApproved(),
                "Expected approval - confirms ParaBank does not validate down payment vs loan amount (documented finding, not a defect)");
    }
}