package com.ebay.cart.Base;

import org.openqa.selenium.Dimension;

import java.util.List;

import static java.util.Arrays.asList;

public enum Devices {

    iphoneX("mobile", new Dimension(375, 812), asList("IphoneX","phone","mobile")),
    iphone8("mobile", new Dimension(414, 736), asList("Iphone8","phone","mobile")),
    iPad("tablet", new Dimension(768, 1024), asList("iPad","phone","mobile")),
    desktop("desktop", new Dimension(1024, 800), asList("desktop","desktop","desktop")),
    S7("mobile", new Dimension(360, 640), asList("S7","phone","mobile"));

    private final String name;
    private final Dimension screenSize;
    private final List<String> tags;

    private Devices(String name, Dimension screenSize, List<String> tags){

        this.name = name;
        this.screenSize = screenSize;
        this.tags = tags;
    }
    public String getName() {
        return name;
    }

    public Dimension getScreenSize() {
        return screenSize;
    }

    public List<String> getTags() {
        return tags;
    }
    public String toString() {
        return String.format("%s %dx%d", name, screenSize.width, screenSize.height);
    }
}
