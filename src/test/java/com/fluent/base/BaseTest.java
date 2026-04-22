package com.fluent.base;

import com.fluent.driver.DriverFactory;
import com.fluent.extensions.TestLifecycleExtension;
import com.fluent.pom.Pages;
import com.fluent.utils.ConfigReader;
import com.fluent.utils.PermissionHandler;
import io.appium.java_client.android.AndroidDriver;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for all test classes.
 *
 * Responsibilities:
 *  - Initialise and quit the AndroidDriver around each test
 *  - Register the TestLifecycleExtension (screenshot on failure, logging)
 *
 * Tests must NOT override setUp/tearDown without calling super.
 */
@ExtendWith(TestLifecycleExtension.class)
public abstract class BaseTest {

    protected final Logger log = LoggerFactory.getLogger(getClass());
    protected Pages pages;

    private static final String PLATFORM = "android";

    // ─────────────────────────── Lifecycle ─────────────────────────────────

    @BeforeEach
    void setUp(TestInfo testInfo) {
        log.info("Initialising driver for test: {}", testInfo.getDisplayName());
        DriverFactory.initDriver();
        PermissionHandler.allowIfPresent();
        pages = new Pages();
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        log.info("Tearing down after test: {}", testInfo.getDisplayName());
        DriverFactory.quitDriver();
    }

    // ─────────────────────────── Helpers ───────────────────────────────────

    /**
     * Taps "Allow" on any Android system permission dialog currently on screen.
     * Safe to call at any point — silently skips if no dialog is present.
     * Call this at the start of every test that may show a permission popup
     * before the first screen is visible.
     */
    protected void allowPermissionDialogIfPresent() {
        PermissionHandler.allowIfPresent();
    }

    /**
     * Wipes all app data (pm clear) then relaunches — guarantees a fresh session
     * regardless of no.reset=true in config.
     */
    protected void resetApp() {
        String appPackage = ConfigReader.forPlatform(PLATFORM).get("app.package");
        AndroidDriver androidDriver = DriverFactory.getDriver();
        androidDriver.terminateApp(appPackage);
        androidDriver.executeScript("mobile: clearApp", Map.of("appId", appPackage));
        androidDriver.activateApp(appPackage);
    }
}
