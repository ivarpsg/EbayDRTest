package com.ebay.cart.Base;
import org.apache.log4j.Logger;

public class Log {

    private static Logger logger = Logger.getLogger(Log.class.getName());

    public static void info(String message) {

        logger.info(message);

    }

    public static void warn(String message) {

        logger.warn(message);

    }

    public static void error(String message) {

        logger.error(message);

    }
}
