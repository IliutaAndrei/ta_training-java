package com.epam.training.eduard_andrei_iliuta.practical_task_3.task4.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.stream.IntStream;

public class InsightsPage extends AbstractPage {

    @FindBy(css = "div.slider__navigation > button.slider__right-arrow.slider-navigation-arrow")
    private WebElement swipeRightButton;

    @FindBy(css = "#main > .content-container > div:first-child " +
                  ".owl-item.active .single-slide__content-container .text p")
    private WebElement titleOfArticle;

    @FindBy(css = "#main > .content-container > div:first-child .owl-item.active a.slider-cta-link")
    private WebElement readMoreButton;

    public InsightsPage(WebDriver webDriver) {
        super(webDriver);
    }

    public InsightsPage swipeCarouselRight(int times) {
        IntStream.range(0, times)
                .forEach(i -> wait.until(
                        ExpectedConditions.elementToBeClickable(swipeRightButton)).click()
                );
        return this;
    }

    public String getArticleTitle() {
        return wait.until(ExpectedConditions.visibilityOf(titleOfArticle)).getText();
    }

    public ArticlePage clickReadMore() {
        wait.until(ExpectedConditions.elementToBeClickable(readMoreButton)).click();

        return new ArticlePage(webDriver);
    }
}
