package com.ebay.cart.Steps;

import com.amazonaws.services.ec2.AmazonEC2;
import com.ebay.cart.pages.AWSPage;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.ArrayList;
import java.util.List;

public class AwsSteps extends Steps {
    AWSPage awsPage=new AWSPage();
    AmazonEC2 amazonEC2;
    @Given("^user creates client and instance$")
    public void userCreatesClient() throws Throwable {
        AmazonEC2 amazonEC2 = awsPage.createEC2Client("ASIA_PACIFIC_SYDNEY");
        List list = new ArrayList();
        list.add("TestAWS");
        awsPage.createEC2Instance(amazonEC2, "TestInstance", "ami-0442c32465d332b4a", "t2.micro", "Ec2Instance1", 1, 1, "subnet-bde4d7da", list);
        System.out.println("Instance Created");
    }

    @Then("^user deletes EC(\\d+) instances$")
    public void userDeletesECInstances(int arg0) throws Throwable {
        List InstanceId = new ArrayList();
        InstanceId.add("i-039f750f2fb92a8d7");
        awsPage.deleteEC2Instance(amazonEC2, InstanceId);
    }
}
