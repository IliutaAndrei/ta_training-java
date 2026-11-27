package com.epam.training.eduard_andrei_iliuta.practical_task_3.task4.pages;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends AbstractPage {
    private static final String HOME_PAGE_URL = "https://www.epam.com/";


    @FindBy(id = "onetrust-accept-btn-handler")
    private WebElement acceptCookiesButton;

    @FindBy(css = "[role='dialog'][aria-label='Privacy']")
    private WebElement cookiesPrivacyDialog;

    @FindBy(css = "span.top-navigation__item-text [href*='insights']")
    private WebElement insightsButton;

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


    public InsightsPage clickInsights() {
        wait.until(ExpectedConditions.elementToBeClickable(insightsButton)).click();
        wait.until(ExpectedConditions.urlContains("insights"));

        return new InsightsPage(webDriver);
    }


}
