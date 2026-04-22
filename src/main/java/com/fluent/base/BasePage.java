package com.fluent.base;

import com.fluent.driver.DriverFactory;
import com.fluent.utils.PermissionHandler;
import com.fluent.utils.WaitUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for all Page Object classes.
 *
 * Rules:
 *  - No test logic here — only reusable mobile interaction helpers.
 *  - Every interaction goes through WaitUtils (no raw findElement without a wait).
 */
public abstract class BasePage {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    // ─────────────────────────── Driver access ─────────────────────────────

    protected AppiumDriver driver() {
        return DriverFactory.getDriver();
    }

    // ─────────────────────────── Core interactions ─────────────────────────

    @Step("Tap element: {locator}")
    protected void tap(By locator) {
        log.debug("Tap: {}", locator);
        WaitUtils.waitForClickability(locator).click();
    }

    @Step("Enter text '{text}' into: {locator}")
    protected void enterText(By locator, String text) {
        log.debug("Enter text '{}' into: {}", text, locator);
        WebElement element = WaitUtils.waitForVisibility(locator);
        element.clear();
        element.sendKeys(text);
        hideKeyboardSafely();
    }

    protected void hideKeyboardSafely() {
        try {
            ((AndroidDriver) driver()).hideKeyboard();
            log.debug("Keyboard hidden");
        } catch (Exception e) {
            log.debug("Keyboard not visible or already hidden: {}", e.getMessage());
        }
    }

    protected String getText(By locator) {
        return WaitUtils.waitForVisibility(locator).getText();
    }

    protected boolean isVisible(By locator) {
        try {
            return WaitUtils.waitForVisibility(locator, 5).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected WebElement getElement(By locator) {
        return WaitUtils.waitForVisibility(locator);
    }

    // ─────────────────────────── System dialog handlers ───────────────────

    /**
     * Dismisses any Android system permission dialog by tapping "Allow".
     * Safe to call mid-flow — silently skips if no dialog is present.
     */
    public void allowPermissionDialogIfPresent() {
        PermissionHandler.allowIfPresent();
    }

    // ─────────────────────────── Digit key helpers ────────────────────────────

    private AndroidKey toAndroidKey(char digit) {
        return switch (digit) {
            case '0' -> AndroidKey.DIGIT_0;
            case '1' -> AndroidKey.DIGIT_1;
            case '2' -> AndroidKey.DIGIT_2;
            case '3' -> AndroidKey.DIGIT_3;
            case '4' -> AndroidKey.DIGIT_4;
            case '5' -> AndroidKey.DIGIT_5;
            case '6' -> AndroidKey.DIGIT_6;
            case '7' -> AndroidKey.DIGIT_7;
            case '8' -> AndroidKey.DIGIT_8;
            case '9' -> AndroidKey.DIGIT_9;
            default  -> throw new IllegalArgumentException("Invalid digit: " + digit);
        };
    }

    protected void pressDigits(String value) {
        AndroidDriver androidDriver = (AndroidDriver) driver();
        for (char digit : value.toCharArray()) {
            androidDriver.pressKey(new KeyEvent(toAndroidKey(digit)));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // ─────────────────────────── Scroll helpers ────────────────────────────

    protected WebElement scrollToText(String text) {
        String uiAutomatorSelector = "new UiScrollable(new UiSelector().scrollable(true))"
            + ".scrollIntoView(new UiSelector().text(\"" + text + "\"))";
        return driver().findElement(AppiumBy.androidUIAutomator(uiAutomatorSelector));
    }

    // Clicks a visible option inside any dropdown/popup by its exact display text.
    // Uses XPath so it works with multi-level custom dropdown views.
    protected void tapDropdownOption(String text) {
        log.debug("Tapping dropdown option: {}", text);
        WaitUtils.waitForVisibility(
            By.xpath("//android.widget.TextView[@text='" + text + "']"))
            .click();
    }
}
