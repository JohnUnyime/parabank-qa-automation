package com.parabank.tests;

import com.parabank.pages.LoginPage;
import com.parabank.pages.UpdateContactInfoPage;
import com.parabank.utils.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UpdateContactInfoTest extends BaseTest {
    @Test
    public void testValidUpdate_TC_U01() {
        login();

        UpdateContactInfoPage updatePage = new UpdateContactInfoPage(driver, wait);
        updatePage.goToUpdateContactInfo();
        updatePage.updatePhone("08099998888");
        updatePage.clickUpdateProfile();

        Assert.assertTrue(updatePage.isUpdateSuccessful(),
                "Contact info update did not succeed with valid data");
    }

    @Test
    public void testBlankFirstName_TC_U02() {
        login();

        UpdateContactInfoPage updatePage = new UpdateContactInfoPage(driver, wait);
        updatePage.goToUpdateContactInfo();
        updatePage.clearFirstName();
        updatePage.clickUpdateProfile();

        Assert.assertTrue(updatePage.isFirstNameErrorShown(),
                "Expected 'First name is required' error was not shown");
    }
}
