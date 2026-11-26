package com.epam.training.eduard_andrei_iliuta.practical_task_3.task2refactored.pages;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;


public class JobsPage extends AbstractPage {

    @FindBy(id = "onetrust-accept-btn-handler")
    private WebElement acceptCookiesButton;

    @FindBy(css = "[role='dialog'][aria-label='Privacy']")
    private WebElement cookiesPrivacyDialog;

    @FindBy(xpath = "//div[input[@name='vacancy_type-Remote']]/label")
    private WebElement remoteLabel;

    @FindBy(css = "[data-testid='country-dropdown'] input[role='combobox']")
    private WebElement locationInput;

    @FindBy(css = "[data-testid='country-dropdown'] [role='option']")
    private List<WebElement> locationOptions;

    @FindBy(css = "input[data-testid='search-input']")
    private WebElement searchKeywordInput;

    @FindBy(name = "submit_search_box_button")
    private WebElement searchAllButton;

    @FindBy(css = "[data-testid='list-container'] [data-testid='accordion-section-container']")
    private WebElement resultCard;

    @FindBy(xpath = "(//*[@data-testid='list-container']"
                    + "//*[@data-testid='accordion-section-container'])[1]"
                    + "//span[@data-testid='accordion-section-header-icon-container']")
    private WebElement expandButton;

    @FindBy(css = "[data-testid='list-container'] [data-testid='job-card-description']")
    private WebElement resultDescription;

    @FindBy(id = "cta_job_apply_unauthorized")
    private WebElement applyButton;

    public JobsPage(WebDriver webDriver) {
        super(webDriver);
    }

    public JobsPage tryAcceptCookiesOnJobsPage() {
        try {
            wait.until(ExpectedConditions.visibilityOf(acceptCookiesButton));
        } catch (TimeoutException e) {
            return this;
        }
        wait.until(ExpectedConditions.elementToBeClickable(acceptCookiesButton)).click();
        wait.until(ExpectedConditions.invisibilityOf(cookiesPrivacyDialog));

        return this;
    }

    public JobsPage clickRemoteFilter() {
        wait.until(ExpectedConditions.elementToBeClickable(remoteLabel)).click();
        wait.until(ExpectedConditions.urlContains("vacancy_type=remote"));

        return this;
    }

    public JobsPage selectLocation(String location) {
        wait.until(ExpectedConditions.elementToBeClickable(locationInput)).click();
        Actions actions = new Actions(webDriver);
        actions.sendKeys(locationInput, location).perform();

        wait.until(driver -> {
            for (WebElement option : locationOptions) {
                if (option.isDisplayed() &&
                    option.isEnabled() &&
                    option.getText().trim().equals(location)) {
                    option.click();
                    return true;
                }
            }
            return false;
        });

        return this;
    }

    public JobsPage searchByKeyword(String keyword) {
        wait.until(ExpectedConditions.visibilityOf(searchKeywordInput))
                .sendKeys(keyword);
        wait.until(ExpectedConditions.elementToBeClickable(searchAllButton)).click();
        wait.until(ExpectedConditions.urlContains("search="));

        return this;
    }

    public JobsPage clickAndExpandFirstResult() {
        wait.until(ExpectedConditions.visibilityOf(resultCard));
        wait.until(ExpectedConditions.elementToBeClickable(expandButton)).click();

        return this;
    }

    public String getResultDescription() {
        return wait.until(ExpectedConditions.visibilityOf(resultDescription)).getText();
    }

    public JobsPage clickApply() {
        wait.until(ExpectedConditions.elementToBeClickable(applyButton)).click();
        return this;
    }
}
