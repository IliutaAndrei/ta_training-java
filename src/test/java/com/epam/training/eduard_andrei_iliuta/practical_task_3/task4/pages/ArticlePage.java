package com.epam.training.eduard_andrei_iliuta.practical_task_3.task4.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ArticlePage extends AbstractPage {

    @FindBy(css = "#main h1")
    private WebElement articleTitle;

    public ArticlePage(WebDriver webDriver) {
        super(webDriver);
    }

    public String getArticleTitle() {
        return wait.until(ExpectedConditions.visibilityOf(articleTitle))
                .getText();
    }
}
