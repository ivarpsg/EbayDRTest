package com.ebay.cart.pages;

import com.ebay.cart.Base.BrowserManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;


import static org.assertj.core.api.Assertions.assertThat;
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
    //Input fields
    @FindBy(id = "firstname")
    private WebElement FirstName;
    @FindBy(id = "lastname")
    private WebElement Lastname;
    @FindBy(id = "email")
    private WebElement Email;
    @FindBy(id = "phone")
    private WebElement Phone;
    @FindBy(id = "businessName")
    private WebElement BusinessName;
    @FindBy(id = "businessAddress")
    private WebElement BusinessAddress;
    @FindBy(id = "suburb")
    private WebElement Suburb;
    @FindBy(id = "postcode")
    private WebElement Postcode;




    public void searchItem(String itemName){
        await("Waiting for page to load").atMost(25,TimeUnit.SECONDS)
                .until(searchboxTxt::isDisplayed);
        //.untilAsserted(()->assertThat(isElementPresent(searchboxTxt)).isTrue());
        searchboxTxt.sendKeys(itemName);
        searchBtn.click();
    }

    public HomePage setCustomer(String customer) {
        //this.IamaDropdown.click();
        if(customer.contains("Not")){
            this.searchboxTxt.click();
        }else{
            this.searchBtn.click();
        }
        return this;
    }

    public HomePage setFirstName(String firstName) {
        this.FirstName.click();
        this.FirstName.sendKeys(firstName);
        return this;
    }

    public HomePage setLastName(String lastName) {
        this.Lastname.click();
        this.Lastname.sendKeys(lastName);
        return this;
    }

    public HomePage setEmail(String email) {
        this.Email.sendKeys(email);
        return this;
    }
    public HomePage setPhone(String phone) {
        this.Phone.sendKeys(phone);
        return this;
    }
    public HomePage setBusinessName(String BusinessName) {
        this.BusinessName.sendKeys(BusinessName);
        return this;
    }
    public HomePage setBusinessAddress(String BusinessAddress) {
        this.BusinessAddress.sendKeys(BusinessAddress);
        return this;
    }
    public HomePage setSuburb(String Suburb) {
        this.Suburb.sendKeys(Suburb);
        return this;
    }
    public HomePage setPostcode(String Postcode) {
        this.Postcode.sendKeys(Postcode);
        return this;
    }
    public HomePage setJobRole(String jobRole){
        //pickOptionfrmDropdown(JobRoleDropdown,JobRoleOptions,jobRole);
        return this;
    }

    public HomePage setCompanySize(String CompanySize){
        //pickOptionfrmDropdown(CompanysizeDropdown,Companysizes,CompanySize);
        return this;
    }


    public HomePage setState(String State){
       // pickOptionfrmDropdown(StateDropdown,States,State);
        return this;
    }

    public void pickOptionfrmDropdown(WebElement dropdown, List<WebElement> options, String optiontext){
        dropdown.click();
        int flag=1;
        for (WebElement opt:options) {
            if(opt.getText().equalsIgnoreCase(optiontext)){
                JavascriptExecutor jse = (JavascriptExecutor)driver;
                jse.executeScript("arguments[0].click()", opt);
                flag++;
                break;
            }
        }
        assertThat(flag).isEqualTo(2);
    }

}
