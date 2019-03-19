package com.ebay.cart.util;

import com.ebay.cart.Base.BrowserManager;
import cucumber.api.Scenario;
import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.company.Company;
import io.codearte.jfairy.producer.person.Person;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.SoftAssertions;
import org.awaitility.Awaitility;
import org.awaitility.Duration;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class util{

    public static Scenario scenario;
    public String Tm="\u2122";
    public String apho="\u2019";




    WebDriver driver=BrowserManager.driver;

    public static Properties properties;
    public static Properties prop;
    public Fairy fairy = Fairy.create();
    public Person person = fairy.person();
    public Company company=fairy.company();
    public SoftAssertions softly = new SoftAssertions();
    SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_hhmmss");
    Date curDate = new Date();
    public String currentDate = sdf.format(curDate);

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


    public void moveMouseAndClick(WebElement element){
        Actions builder = new Actions(driver);
        builder.moveToElement(element).click().build().perform();
    }

    public void readpropfile(String env){
        try {
            properties = new Properties();
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "//src//main//resources//config//"+env+".properties");
            properties.load(fis);
        } catch (Exception e) {
            System.out.println("Error in initializing Properties file");
        }
    }

    public void select(WebElement element,String text){
        Select select=new Select(element);
        select.selectByVisibleText(text);
    }
    public void select(WebElement element,int index){
        Select select=new Select(element);
        select.selectByIndex(index);
    }
    public void select(String value,WebElement element){
        Select select=new Select(element);
        select.selectByValue(value);
    }
    public void deselectAll(WebElement element){
        Select deselect=new Select(element);
        deselect.deselectAll();
    }
    public String randomalphanumeric(int limit){
        String generatedString = RandomStringUtils.randomAlphanumeric(limit);
        System.out.println(generatedString);
        return generatedString;
    }
    public String randomString(int limit){
        String generatedString = RandomStringUtils.randomAlphabetic(limit);
        System.out.println(generatedString);
        return generatedString;
    }
    public int randomNumber(){

        int budget=fairy.baseProducer().randomBetween(500,10000);
        return budget;
    }
    public List<String> GetTextList(List<WebElement> element)
    {
        List<String> list=new ArrayList<String>();
        for (WebElement opt:element) {
            list.add(opt.getText());
        }
        return list;
    }

    public boolean verifyUrl(String url) {
        String urlRegex = "^(http|https)://[-a-zA-Z0-9+&@#/%?=~_|,!:.;]*[-a-zA-Z0-9+@#/%=&_|]";
        Pattern pattern = Pattern.compile(urlRegex);
        Matcher m = pattern.matcher(url);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public static void readConfigfile(){
        try {
            prop = new Properties();
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "//src//main//resources//config//config.properties");
            prop.load(fis);
        } catch (Exception e) {
            System.out.println("Error in initializing Properties file");
        }
    }

    public void waitDefault(){
        Awaitility.setDefaultTimeout(new Duration(10,TimeUnit.SECONDS));

    }


}
