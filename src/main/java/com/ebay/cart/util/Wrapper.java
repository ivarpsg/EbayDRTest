package com.ebay.cart.util;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class Wrapper {

    protected boolean isElementPresent(WebElement element) {
        boolean value=false;
        try {
            if(element.isDisplayed()){
                value=true;
            }} catch(NoSuchElementException e) {
            e.printStackTrace();
        }
        return value;
    }

    public void select(String string,WebElement element){
        Select select=new Select(element);
        select.selectByVisibleText(string);
    }

    public void select(int i,WebElement element){
        Select select=new Select(element);
        select.selectByIndex(i);
    }

    public void select(WebElement element,String value){
        Select select=new Select(element);
        select.selectByValue(value);
    }
}
