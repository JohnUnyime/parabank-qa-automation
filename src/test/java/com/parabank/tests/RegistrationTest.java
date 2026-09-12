package com.parabank.tests;

import com.parabank.pages.RegistrationPage;
import com.parabank.utils.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.UUID;

public class RegistrationTest extends BaseTest {

    @Test
    public void testExistingUsername_TC_R04() {
        RegistrationPage regPage = new RegistrationPage(driver, wait);
        regPage.goToRegisterPage();
        regPage.fillRegistrationForm(
                "John", "Unyime", "45 Freedom Way", "Lagos", "LA", "100001",
                "08011112222", "12345678900",
                "Firmin",  // must match config.properties username
                "Pass@2026", "Pass@2026"
        );
        regPage.clickRegister();

        String actualError = regPage.getErrorMessage();
        Assert.assertEquals(actualError, "This username already exists.",
                "Error message did not match expected text for existing username");
    }

    @Test
    public void testValidRegistration_TC_R05() {
        RegistrationPage regPage = new RegistrationPage(driver, wait);
        regPage.goToRegisterPage();

        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        String uniqueUsername = "qa" + uniqueId;
        String uniqueSsn = String.format("%09d", Math.abs(uniqueId.hashCode()) % 1_000_000_000);

        regPage.fillRegistrationForm(
                "John", "Unyime", "45 Freedom Way", "Lagos", "LA", "100001",
                "08011112222", uniqueSsn,
                uniqueUsername,
                "Pass@2026", "Pass@2026"
        );
        regPage.clickRegister();

        Assert.assertTrue(regPage.isRegistrationSuccessful(),
                "Registration was not successful for valid, unique details");
    }
}