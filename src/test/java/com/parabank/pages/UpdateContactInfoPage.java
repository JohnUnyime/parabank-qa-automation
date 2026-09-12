package com.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class UpdateContactInfoPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By updateLink = By.linkText("Update Contact Info");
    private By firstName = By.id("customer.firstName");
    private By lastName = By.id("customer.lastName");
    private By address = By.id("customer.address.street");
    private By city = By.id("customer.address.city");
    private By state = By.id("customer.address.state");
    private By zipCode = By.id("customer.address.zipCode");
    private By phone = By.id("customer.phoneNumber");
    private By updateButton = By.cssSelector("input[value='Update Profile']");
    private By resultDiv = By.id("updateProfileResult");
    private By firstNameError = By.id("firstName-error");

    public UpdateContactInfoPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void goToUpdateContactInfo() {
        wait.until(ExpectedConditions.elementToBeClickable(updateLink)).click();
    }

    // Waits for the async getCustomer() AJAX call to finish populating the form
    private void waitForFormToLoad() {
        wait.until(d -> !d.findElement(firstName).getAttribute("value").isEmpty());
    }

    public void updateFirstName(String value) {
        waitForFormToLoad();
        WebElement fn = driver.findElement(firstName);
        fn.clear();
        fn.sendKeys(value);
    }

    public void clearFirstName() {
        waitForFormToLoad();
        driver.findElement(firstName).clear();
    }

    public void updatePhone(String value) {
        waitForFormToLoad();
        WebElement ph = driver.findElement(phone);
        ph.clear();
        ph.sendKeys(value);
    }

    public void clickUpdateProfile() {
        driver.findElement(updateButton).click();
    }

    public boolean isUpdateSuccessful() {
        try {
            WebElement result = new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(ExpectedConditions.visibilityOfElementLocated(resultDiv));
            return result.getText().contains("Your updated address and phone number have been added");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isFirstNameErrorShown() {
        try {
            WebElement error = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(firstNameError));
            return error.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
