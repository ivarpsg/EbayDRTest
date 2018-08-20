package com.ebay.cart.pages;

import com.ebay.cart.Base.BrowserManager;
import org.awaitility.Duration;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import sun.awt.geom.AreaOp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

public class SearchResultsPage extends BrowserManager {

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver,this);
    }
    //xpath = "//*[@id='srp-river-results-listing1']//a/h3"
    @FindBy(css = "li#srp-river-results-listing1  a > h3")
    WebElement FirstItemLink;
    @FindBy(css = "li#srp-river-results-listing2  a > h3")
    WebElement SecondItemLink;
    @FindBy(css = "li#srp-river-results-listing1  a > h3>span")
    WebElement FirstItemLinkFullTxt;
    @FindBy(css = "li#srp-river-results-listing2  a > h3>span")
    WebElement SecondtItemLinkFullTxt;
    @FindBy(xpath="//div[contains(@class,'actPanel')]//a[contains(text(),'Add to cart')]")
    WebElement AddcartBtn;
    @FindBy(xpath = "//*[contains(@id,'ADDON_')]//button[1]")
    WebElement AddonNoThanks;
    @FindBy(xpath = "//span[contains(text(),'Go to cart')]")
    WebElement GotoCartBtn;
    @FindBy(xpath = "//h1[contains(text(),'Shopping cart')]")
    WebElement Shoppingcartheading;
    @FindAll(@FindBy(xpath ="//span[@class='BOLD']"))
     List<WebElement> AddedItems;
    @FindBy(xpath = "//a/h2[contains(text(),'Buy')]")
    WebElement BuyitNow;


    List<String> Itemnames=new ArrayList<String>();
    @FindBy(xpath = "//span[contains(@class,'tooltip-close')]")
    WebElement searchtooltipclose;



    public void clickFirstelement() throws InterruptedException {
        Thread.sleep(2000);
        String prodname=null;
        try {
            if(isElementPresent(searchtooltipclose)==true){
                searchtooltipclose.click();
            }
        }catch (NoSuchElementException e){
            e.printStackTrace();
        }
        await("Waiting for Buy it Now to load").atMost(25,SECONDS)
                .until(BuyitNow::isDisplayed);
        BuyitNow.click();
        await("Waiting for First item to load").atMost(25,SECONDS)
                .until(FirstItemLink::isDisplayed);
                //.untilAsserted(()->assertThat(isElementPresent(FirstItemLink)).isTrue());

        try{

            if(isElementPresent(FirstItemLinkFullTxt)){
                 Itemnames.add(FirstItemLink.getText().replace(FirstItemLinkFullTxt.getText(),""));
            }
        }catch (NoSuchElementException e){
            Itemnames.add(FirstItemLink.getText());
        }

        FirstItemLink.click();

    }

    public void ClickAddCartButton() throws InterruptedException {
        await("Waiting for Add cart button").atMost(25,SECONDS)
                .until(AddcartBtn::isDisplayed);
                //.untilAsserted(()->assertThat(isElementPresent(AddcartBtn)).isTrue());

            AddcartBtn.click();
            Thread.sleep(4000);
        if(isElementPresent(AddonNoThanks)==true){
            AddonNoThanks.click();
            await("Waiting for View cart button").atMost(10,SECONDS)
                    .until(Shoppingcartheading::isDisplayed);
        }else if(isElementPresent(GotoCartBtn)==true){
            GotoCartBtn.click();
            await("Waiting for View cart button").atMost(10,SECONDS)
            .until(Shoppingcartheading::isDisplayed);
            //.untilAsserted(()->assertThat(isElementPresent(Shoppingcartheading)).isTrue());
            }else{
            await("Waiting for View cart button").atMost(15,SECONDS)
                    .until(Shoppingcartheading::isDisplayed);
                    //.untilAsserted(()->assertThat(isElementPresent(Shoppingcartheading)).isTrue());
        }
    }

    public void verifyItemsAdded(){

        List<String> itemsInList=new ArrayList<>();
        for(WebElement element:AddedItems){

            itemsInList.add(element.getText());
        }
        //assertThat(itemsInList.containsAll(Itemnames)).isTrue();
        assertThat(itemsInList).containsAll(Itemnames);

    }

}
