package com.ebay.cart.pages;

import com.ebay.cart.Base.BrowserManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

public class HomePage extends BrowserManager {

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver,this);
    }

    @FindBy(id = "gh-ac")
    WebElement searchboxTxt;
    @FindBy(id="gh-cat")
     WebElement categoryDropdown;
    @FindBy(id="gh-btn")
    WebElement searchBtn;


    public void searchItem(String itemName){
        await("Waiting for page to load").atMost(10,TimeUnit.SECONDS)
                .until(searchBtn::isDisplayed);
        searchboxTxt.sendKeys(itemName);
        searchBtn.click();
    }
    public void searchItem(String itemName,String category){
        await("Waiting for page to load").atMost(10,TimeUnit.SECONDS)
                .until(searchBtn::isDisplayed);
        searchboxTxt.sendKeys(itemName);
        select(categoryDropdown,category);
        searchBtn.click();
    }
}
