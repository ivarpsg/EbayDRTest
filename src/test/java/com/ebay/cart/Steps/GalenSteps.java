package com.ebay.cart.Steps;

import com.ebay.cart.Base.Devices;
import com.ebay.cart.Base.GalenTestBase;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

import java.io.IOException;

public class GalenSteps extends GalenTestBase {

    @When("^user validates layouts for hero banner as expected$")
    public void user_validates_layouts_for_hero_banner_as_expected() throws IOException {
        String homepage = null;
        checkLayout("/specs/HomePage.spec",Devices.valueOf("desktop").getTags(),"homepage");

    }

    @Given("^user validates layouts for hero banner as expected for device Desktop$")
    public void user_validates_layouts_for_hero_banner_as_expected_for_device_Desktop() throws IOException {
        checkLayout("/specs/HomePage.spec",Devices.valueOf("desktop").getTags(),"homepage");
    }

}
