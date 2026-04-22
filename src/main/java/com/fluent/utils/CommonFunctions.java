package com.fluent.utils;

import com.fluent.driver.DriverFactory;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

/**
 * Reusable utility actions that are not tied to any specific screen.
 */
public final class CommonFunctions {

    private static final Logger log = LoggerFactory.getLogger(CommonFunctions.class);

    private static final String APP_PACKAGE =
        ConfigReader.forPlatform("android").get("app.package");

    private static final int DEFAULT_MAX_SWIPES = 10;

    private CommonFunctions() { /* utility class */ }

    // ─────────────────────────── App lifecycle ─────────────────────────────

    /**
     * Resets the app by terminating and re-launching it.
     * Preserves the Appium session — no driver restart required.
     */
    public static void resetApp() {
        AndroidDriver driver = (AndroidDriver) DriverFactory.getDriver();
        log.info("Resetting app: terminating '{}'", APP_PACKAGE);
        driver.terminateApp(APP_PACKAGE);
        log.info("Resetting app: activating '{}'", APP_PACKAGE);
        driver.activateApp(APP_PACKAGE);
        log.info("App reset complete");
    }

    // ─────────────────────────── Scroll helpers ────────────────────────────

    /**
     * Scrolls down the screen until the element matching {@code locator} is visible.
     * Throws if not found within {@code maxSwipes} swipes.
     */
    public static void scrollDownTillElement(By locator, int maxSwipes) {
        scroll(locator, "down", maxSwipes);
    }

    /** Scrolls down with the default swipe limit (10). */
    public static void scrollDownTillElement(By locator) {
        scroll(locator, "down", DEFAULT_MAX_SWIPES);
    }

    /**
     * Scrolls up the screen until the element matching {@code locator} is visible.
     * Throws if not found within {@code maxSwipes} swipes.
     */
    public static void scrollUpTillElement(By locator, int maxSwipes) {
        scroll(locator, "up", maxSwipes);
    }

    /** Scrolls up with the default swipe limit (10). */
    public static void scrollUpTillElement(By locator) {
        scroll(locator, "up", DEFAULT_MAX_SWIPES);
    }

    /**
     * Scrolls down until the element is visible, using the default swipe limit.
     * Equivalent to {@link #scrollDownTillElement(By)}.
     */
    public static void scrollTillElementVisible(By locator) {
        scroll(locator, "down", DEFAULT_MAX_SWIPES);
    }

    // ─────────────────────────── Private internals ─────────────────────────

    private static void scroll(By locator, String direction, int maxSwipes) {
        AndroidDriver driver = DriverFactory.getDriver();
        for (int i = 0; i < maxSwipes; i++) {
            if (isVisible(driver, locator)) {
                log.info("Element found after {} swipe(s) {}", i, direction);
                return;
            }
            swipe(driver, direction);
            log.debug("Swipe {}/{} ({})", i + 1, maxSwipes, direction);
        }
        if (!isVisible(driver, locator)) {
            throw new RuntimeException(
                "Element not visible after " + maxSwipes + " " + direction + " swipe(s): " + locator
            );
        }
    }

    private static boolean isVisible(AndroidDriver driver, By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    /**
     * Performs a single swipe gesture.
     * "down" scrolls the page down (finger moves bottom → top).
     * "up"   scrolls the page up   (finger moves top → bottom).
     */
    private static void swipe(AndroidDriver driver, String direction) {
        Dimension size = driver.manage().window().getSize();
        int centerX = size.width / 2;
        int startY, endY;

        if ("down".equals(direction)) {
            startY = (int) (size.height * 0.75);
            endY   = (int) (size.height * 0.25);
        } else {
            startY = (int) (size.height * 0.25);
            endY   = (int) (size.height * 0.75);
        }

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence sequence = new Sequence(finger, 0);
        sequence.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerX, startY));
        sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        sequence.addAction(finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(), centerX, endY));
        sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(List.of(sequence));
    }
}
