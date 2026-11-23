package com.epam.training.eduard_andrei_iliuta.practical_task_2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class PracticalTask2Test {

    private static final String PAGE_URL = "https://www.epam.com/";
    private static final By ACCEPT_COOKIES_SELECTOR =
            By.id("onetrust-accept-btn-handler");
    private static final By COOKIES_PRIVACY_DIALOG =
            By.cssSelector("[role='dialog'][aria-label='Privacy']");
    private static final By CAREERS_PAGE_SELECTOR =
            By.linkText("Careers");
    private static final By CAREERS_PAGE_PARTIAL =
            By.partialLinkText("Career");
    private static final By START_SEARCH_BUTTON_SELECTOR =
            By.cssSelector("a.button-body[href*='start-your-search-here']");
    private static final By REMOTE_FILTER_LABEL =
            By.xpath("//div[input[@name='vacancy_type-Remote']]/label");
    private static final By LOCATION_INPUT =
            By.cssSelector("[data-testid='country-dropdown'] input[role='combobox']");
    private static final By LOCATION_OPTION =
            By.xpath("//div[@role='option' and @data-testid='dropdown-option']"
                     + "[span[normalize-space(.)='Romania']]");
    private static final By LOCATION_VALUE = By.cssSelector(
            "[data-testid='country-dropdown'] [data-testid='dropdown-value']");
    private static final By SEARCH_KEYWORD_INPUT =
            By.cssSelector("input[data-testid='search-input']");
    private static final By SEARCH_ALL_BUTTON =
            By.name("submit_search_box_button");
    private static final By RESULT_CARDS = By.cssSelector(
            "[data-testid='list-container'] [data-testid='accordion-section-container']");
    private static final By EXPAND_SELECTOR =
            By.xpath("(//*[@data-testid='accordion-section-container'])[1]"
                     + "/descendant::span[@data-testid='accordion-section-header-icon-container']");
    private static final By RESULT_DESCRIPTION =
            By.cssSelector("[data-testid='list-container'] [data-testid='job-card-description']");
    private static final By APPLY_BUTTON =
            By.id("cta_job_apply_unauthorized");
    private static final By SEARCH_BUTTON_TAG =
            By.tagName("button");
    private static final By COOKIE_BANNER_CLASS =
            By.className("onetrust-banner-sdk");
    private WebDriver webDriver;


    @BeforeEach
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        webDriver = new ChromeDriver(options);
        webDriver.get(PAGE_URL);
    }

    @ParameterizedTest
    @CsvSource("Romania, Java")
    public void containsSearchedKeyword(String location, String keyword) {

        Wait<WebDriver> wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        // tagName — validate that buttons exist on the page
        assertFalse(webDriver.findElements(SEARCH_BUTTON_TAG).isEmpty(), "Not empty");

        // accept cookies if is displayed or pass if not
        wait.until(driver -> {
            try {
                WebElement acceptButton = driver.findElement(ACCEPT_COOKIES_SELECTOR);
                if (!acceptButton.isDisplayed() || !acceptButton.isEnabled()) {
                    return false;
                }
                acceptButton.click();
                return true;
            } catch (Exception e) {
                return false;
            }
        });

        wait.until(ExpectedConditions.invisibilityOfElementLocated(COOKIES_PRIVACY_DIALOG));


        assertTrue(webDriver.findElements(COOKIE_BANNER_CLASS).isEmpty()
                   || !webDriver.findElement(COOKIE_BANNER_CLASS).isDisplayed(),
                "Cookies banner still displayed!");

        // partialLinkText — verify Careers link exists
        wait.until(ExpectedConditions.presenceOfElementLocated(CAREERS_PAGE_PARTIAL));

        // find and click Careers link
        WebElement careersButton = wait.until(
                ExpectedConditions.elementToBeClickable(CAREERS_PAGE_SELECTOR));
        careersButton.click();

        // find and click the visible Start your search link
        WebElement startSearchButton = wait.until(
                ExpectedConditions.elementToBeClickable(START_SEARCH_BUTTON_SELECTOR));
        startSearchButton.click();

        // accept cookies on the search page
        WebElement acceptCookiesButtonCareersPage = wait.until(
                ExpectedConditions.elementToBeClickable(ACCEPT_COOKIES_SELECTOR));
        acceptCookiesButtonCareersPage.click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(COOKIES_PRIVACY_DIALOG));

        // check Remote before filling in the search fields because it clears all inputs
        WebElement remoteLabel = wait.until(
                ExpectedConditions.elementToBeClickable(REMOTE_FILTER_LABEL));
        remoteLabel.click();

        wait.until(ExpectedConditions.urlContains("vacancy_type=remote"));

        WebElement locationInput = wait.until(
                ExpectedConditions.elementToBeClickable(LOCATION_INPUT));
        locationInput.click();

        // to simulate keyboard typing to open dropdown
        Actions actions = new Actions(webDriver);
        actions.sendKeys(locationInput, location).perform();

        WebElement locationOption = wait.until(
                ExpectedConditions.elementToBeClickable(LOCATION_OPTION));
        locationOption.click();

        WebElement locationValue = wait.until(
                ExpectedConditions.visibilityOfElementLocated(LOCATION_VALUE));
        assertEquals(location, locationValue.getText().trim(), "Another location value");

        // enter the keyword and click Search
        WebElement searchKeywordInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(SEARCH_KEYWORD_INPUT));
        searchKeywordInput.sendKeys(keyword);

        WebElement searchAllButton = wait.until(
                ExpectedConditions.elementToBeClickable(SEARCH_ALL_BUTTON));
        searchAllButton.click();

        wait.until(ExpectedConditions.urlContains("search="));

        // expand the first result if it is closed
        WebElement firstResult = wait.until(
                ExpectedConditions.visibilityOfElementLocated(RESULT_CARDS));

        WebElement expandButton = firstResult.findElement(EXPAND_SELECTOR);
        expandButton.click();

        // check the description and click Apply
        WebElement resultDescription = firstResult.findElement(RESULT_DESCRIPTION);
        assertTrue(resultDescription.getText().contains(keyword),
                "Description doesn't contains searched keyword");

        WebElement applyButton = wait.until(
                ExpectedConditions.elementToBeClickable(firstResult.findElement(APPLY_BUTTON)));
        applyButton.click();
    }

    @AfterEach
    public final void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}