package com.fluent.utils;

import com.fluent.driver.DriverFactory;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

/**
 * Handles Android system permission dialogs automatically.
 *
 * Call allowIfPresent() after driver init and at any point a permission
 * popup may appear mid-flow. Silently skips if no dialog is present.
 *
 * Resource-ids covered:
 *   - com.android.permissioncontroller:id/permission_allow_button
 *   - com.android.permissioncontroller:id/permission_allow_foreground_button
 *   - android:id/button1  (generic positive button fallback)
 */
public final class PermissionHandler {

    private static final Logger log = LoggerFactory.getLogger(PermissionHandler.class);

    private static final By[] ALLOW_LOCATORS = {
        AppiumBy.id("com.android.permissioncontroller:id/permission_allow_button"),
        AppiumBy.id("com.android.permissioncontroller:id/permission_allow_foreground_button"),
        AppiumBy.id("android:id/button1")
    };

    private PermissionHandler() { /* utility class */ }

    /**
     * Taps "Allow" on any visible system permission dialog.
     * Safe to call when no dialog is present — silently skips.
     */
    public static void allowIfPresent() {
        try {
            new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(5))
                .until(driver -> {
                    for (By locator : ALLOW_LOCATORS) {
                        List<WebElement> elements = driver.findElements(locator);
                        if (!elements.isEmpty() && elements.get(0).isDisplayed()) {
                            elements.get(0).click();
                            log.info("Permission dialog dismissed [Allow] via: {}", locator);
                            return true;
                        }
                    }
                    return false;
                });
        } catch (Exception e) {
            log.debug("No permission dialog visible — skipping");
        }
    }
}
