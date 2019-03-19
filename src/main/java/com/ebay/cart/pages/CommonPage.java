package com.ebay.cart.pages;

import com.ebay.cart.Base.BrowserManager;
import junit.framework.TestCase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

public class CommonPage extends BrowserManager {

       public CommonPage() {
        PageFactory.initElements(BrowserManager.driver, this);
    }
    @FindBy(id = "main-heading")
    public WebElement Heading;
    @FindBy(id = "main-description")
    public WebElement HeadingDesc;
    @FindBy(xpath = "//a[contains(text(),'Solution')]")
    public WebElement MBSlink;
    @FindBy(xpath = "//a[contains(text(),'Market Comparison')]")
    public WebElement Test;
    @FindBy(xpath = "//a[contains(text(),'My Business Cases')]")
    public WebElement Testcase;
    @FindBy(xpath = "//*[contains(text(),'Log out')]")
    public WebElement logout;

    public void GoToMBSPage() {
        MBSlink.click();
    }

    public void GotoPage(String pagetitle) {

        await("Wait till page loads")
                .atMost(15,TimeUnit.SECONDS)
                .untilAsserted(() -> assertThat(isElementPresent(logout)).isTrue());
        String conv=pagetitle.toUpperCase();
        if (conv.contains("SOLUTION")){
            MBSlink.click();
            verifyLanding(conv);
        }else if(conv.contains("MARKET")){
            Test.click();
            verifyLanding(conv);
        }else if(conv.contains("CASES")){
            Testcase.click();
            verifyLanding(conv);
        }
    }

    public void logout(){
        logout.click();
    }

    public void verifyLanding(String conv){
        await("Wait till login page loads")
                .atMost(15,TimeUnit.SECONDS)
                .untilAsserted(() -> assertThat(Heading.getText()).containsIgnoringCase(conv));
    }

}
