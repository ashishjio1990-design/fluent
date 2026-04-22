package com.fluent.utils;

import com.fluent.driver.DriverFactory;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * Centralised explicit-wait helpers.
 * No Thread.sleep() — all waits are condition-based via WebDriverWait.
 */
public final class WaitUtils {

    private static final Logger log = LoggerFactory.getLogger(WaitUtils.class);

    public static final long DEFAULT_TIMEOUT_SECONDS = 15;

    private WaitUtils() { /* utility class */ }

    public static WebElement waitForVisibility(By locator) {
        return waitForVisibility(locator, DEFAULT_TIMEOUT_SECONDS);
    }

    public static WebElement waitForVisibility(By locator, long timeoutSeconds) {
        log.debug("Waiting {}s for visibility: {}", timeoutSeconds, locator);
        return getWait(timeoutSeconds).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickability(By locator) {
        return waitForClickability(locator, DEFAULT_TIMEOUT_SECONDS);
    }

    public static WebElement waitForClickability(By locator, long timeoutSeconds) {
        log.debug("Waiting {}s for clickability: {}", timeoutSeconds, locator);
        return getWait(timeoutSeconds).until(ExpectedConditions.elementToBeClickable(locator));
    }

    private static WebDriverWait getWait(long timeoutSeconds) {
        AppiumDriver driver = DriverFactory.getDriver();
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
    }
}
