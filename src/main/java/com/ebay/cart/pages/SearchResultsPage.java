package com.ebay.cart.pages;

import com.ebay.cart.Base.BrowserManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import sun.awt.geom.AreaOp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

public class SearchResultsPage extends BrowserManager {

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver,this);
    }

    @FindBy(xpath = "//*[@id='srp-river-results-listing1']//a/h3")
    WebElement FirstItemLink;
    @FindBy(id="isCartBtn_btn")
    WebElement AddcartBtn;
    @FindBy(xpath = "//*[contains(@id,'ADDON_')]//button[1]")
    WebElement AddonNoThanks;
    @FindBy(xpath = "//span[contains(text(),'Go to cart')]")
    WebElement GotoCartBtn;
    @FindBy(xpath = "//h1[contains(text(),'Shopping cart')]")
    WebElement Shoppingcartheading;
    @FindAll(@FindBy(xpath ="//span[@class='BOLD']"))
     List<WebElement> AddedItems;

    List<String> Itemnames=new ArrayList<String>();




    public void clickFirstelement(){
        await("Waiting for First item to load").atMost(15,TimeUnit.SECONDS)
                .until(FirstItemLink::isDisplayed);
        FirstItemLink.click();
        Itemnames.add(FirstItemLink.getText());
    }

    public void ClickAddCartButton(){
        await("Waiting for Add cart button").atMost(15,TimeUnit.SECONDS)
                .until(AddcartBtn::isDisplayed);
        AddcartBtn.click();
        if(isElementPresent(AddonNoThanks)){

            AddonNoThanks.click();

            await("Waiting for View cart button").atMost(5,TimeUnit.SECONDS)
                    .until(Shoppingcartheading::isDisplayed);

        }else if(GotoCartBtn.isDisplayed()){
            GotoCartBtn.click();
            await("Waiting for View cart button").atMost(5,TimeUnit.SECONDS)
                    .until(Shoppingcartheading::isDisplayed);

        }else{
            assertThat(isElementPresent(Shoppingcartheading)).isTrue();
        }
    }

    public void verifyItemsAdded(){

        List<String> itemsInList=new ArrayList<>();
        for(WebElement element:AddedItems){

            itemsInList.add(element.getText());
        }

        assertThat(Itemnames).containsOnly(String.valueOf(itemsInList));
    }

}
