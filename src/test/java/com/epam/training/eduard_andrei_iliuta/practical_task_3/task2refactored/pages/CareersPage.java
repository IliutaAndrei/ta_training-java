package com.epam.training.eduard_andrei_iliuta.practical_task_3.task2refactored.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CareersPage extends AbstractPage {

    @FindBy(css = "a.button-body[href*='start-your-search-here']")
    private WebElement startSearchButton;


    public CareersPage(WebDriver webDriver) {
        super(webDriver);
    }

    public JobsPage clickStartSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(startSearchButton)).click();
        return new JobsPage(webDriver);
    }
}
