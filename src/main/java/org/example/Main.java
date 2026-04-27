package org.example;

import com.applitools.eyes.StdoutLogHandler;
import com.applitools.eyes.appium.Eyes;
import com.applitools.eyes.appium.Target;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

import java.net.URL;
import java.time.Duration;

public class Main {

    public static void main(String[] args) throws Exception {

        String appPath = System.getenv("APP_PATH");
        String apiKey = System.getenv("APPLITOOLS_API_KEY");

        if (appPath == null || apiKey == null) {
            throw new RuntimeException("Set APP_PATH and APPLITOOLS_API_KEY");
        }

        XCUITestOptions options = new XCUITestOptions()
                .setDeviceName("iPhone 14")
                .setPlatformVersion("26.0")
                .setApp(appPath)
                .setAutomationName("XCUITest")
                .setNewCommandTimeout(Duration.ofSeconds(300));

        Eyes.setMobileCapabilities(
                options,
                apiKey,
                "https://eyes.applitools.com"
        );

        IOSDriver driver = new IOSDriver(
                new URL("http://127.0.0.1:4723"),
                options
        );

        Eyes eyes = new Eyes();
        eyes.setLogHandler(new StdoutLogHandler(true));

        try {
            eyes.open(driver, "IOS Application", "NML | iOS | Local");

            eyes.check("Main Page", Target.window().fully());
            System.out.println("Eyes Check Completed");
            eyes.check("System Screenshot", Target.window().useSystemScreenshot());
            System.out.println("System Screenshot Completed");


            eyes.close();
            System.out.println("Eyes Closed");
        } finally {
            eyes.abortIfNotClosed();
            driver.quit();
            System.out.println("Driver Closed");
        }
    }
}