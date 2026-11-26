package com.epam.training.eduard_andrei_iliuta.practical_task_3.task2refactored.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends AbstractPage {

    private static final String HOME_PAGE_URL = "https://www.epam.com/";

    @FindBy(id = "onetrust-accept-btn-handler")
    private WebElement acceptCookiesButton;
    @FindBy(css = "[role='dialog'][aria-label='Privacy']")
    private WebElement cookiesPrivacyDialog;
    @FindBy(partialLinkText = "Career")
    private WebElement partialCareers;

    @FindBy(linkText = "Careers")
    private WebElement careersPageButton;

    public HomePage(WebDriver webDriver) {
        super(webDriver);
    }

    public HomePage openPage() {
        webDriver.get(HOME_PAGE_URL);
        return this;
    }

    public HomePage tryAcceptCookies() {
        try {
            wait.until(ExpectedConditions.visibilityOf(acceptCookiesButton));
        } catch (TimeoutException e) {
            return this;
        }

        wait.until(driver -> {
            try {
                if (!acceptCookiesButton.isDisplayed() || !acceptCookiesButton.isEnabled()) {
                    return false;
                }

                acceptCookiesButton.click();
                return true;
            } catch (Exception e) {
                return false;
            }
        });

        wait.until(ExpectedConditions.invisibilityOf(cookiesPrivacyDialog));
        return this;
    }

    public CareersPage clickCareers() {
        wait.until(ExpectedConditions.visibilityOf(partialCareers));
        wait.until(ExpectedConditions.elementToBeClickable(careersPageButton)).click();

        return new CareersPage(webDriver);
    }
}
