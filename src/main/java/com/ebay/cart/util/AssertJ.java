package com.ebay.cart.util;

import org.assertj.core.api.AbstractAssert;
import org.openqa.selenium.WebElement;

public class AssertJ extends AbstractAssert<AssertJ, WebElement> {

    public AssertJ(WebElement element) {
        super(element, AssertJ.class);
    }

    public static AssertJ AssertThat(WebElement webElement){
        return new AssertJ(webElement);
    }

    public AssertJ hasAttributeValue(String attr, String value){
        isNotNull();

        //check condition
        if(!actual.getAttribute(attr).equals(value)){
            failWithMessage("Expected element to have attr <%s> value as <%s>. But was not!!", attr, value);
        }

        return this;
    }

    public AssertJ isButton(){
        isNotNull();

        //check condition
        if(!(actual.getTagName().equalsIgnoreCase("button") || actual.getAttribute("type").equalsIgnoreCase("button"))){
            failWithMessage("Expected element to be a button. But was not!!");
        }

        return this;
    }
    public AssertJ isLink(){
        isNotNull();

        //check condition
        if(!actual.getTagName().equalsIgnoreCase("a")){
            failWithMessage("Expected element to be a link. But was not!!");
        }

        return this;
    }

}
