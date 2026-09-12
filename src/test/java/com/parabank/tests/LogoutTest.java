package com.parabank.tests;

import com.parabank.pages.LoginPage;
import com.parabank.pages.LogoutPage;
import com.parabank.utils.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LogoutTest extends BaseTest {

    @Test
    public void testLogoutRedirect_TC_O01() {
        login();

        LogoutPage logoutPage = new LogoutPage(driver, wait);
        logoutPage.clickLogout();

        Assert.assertTrue(logoutPage.isLoginPageDisplayed(),
                "User was not redirected to the login page after logout");
    }

    @Test
    public void testBackButtonAfterLogout_TC_O02() {
        login();

        LogoutPage logoutPage = new LogoutPage(driver, wait);
        logoutPage.clickLogout();

        // Simulate the manual steps that reproduced the defect:
        // go back, then attempt to navigate to a protected page
        driver.navigate().back();
        driver.get("https://parabank.parasoft.com/parabank/overview.htm");

        // KNOWN DEFECT (SCRUM-10 / TC-O02): Medium severity — no data leak,
        // but an unhandled internal error is shown instead of a clean redirect to login.
        Assert.assertFalse(logoutPage.isInternalErrorShown(),
                "DEFECT SCRUM-10: Internal error shown instead of graceful redirect after Back button post-logout");
    }
}