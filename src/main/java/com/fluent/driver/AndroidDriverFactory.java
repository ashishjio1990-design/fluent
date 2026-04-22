package com.fluent.driver;

import com.fluent.utils.ConfigReader;
import com.fluent.utils.DeviceDetector;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * Builds a fully configured AndroidDriver using UiAutomator2Options.
 * All capability values are read from android.properties.
 */
public final class AndroidDriverFactory {

    private static final Logger log = LoggerFactory.getLogger(AndroidDriverFactory.class);

    private AndroidDriverFactory() { /* utility class */ }

    /**
     * Creates and returns a new AndroidDriver instance.
     */
    public static AndroidDriver create() {
        ConfigReader config = ConfigReader.forPlatform("android");

        UiAutomator2Options options = buildOptions(config);
        URL serverUrl = resolveServerUrl(config.get("appium.server.url"));

        log.info("Connecting to Appium server at: {}", serverUrl);
        AndroidDriver driver = new AndroidDriver(serverUrl, options);
        log.info("AndroidDriver created. Session ID: {}", driver.getSessionId());

        // Grant runtime permissions that autoGrantPermissions cannot handle (Android 13+ POST_NOTIFICATIONS).
        // This prevents the notification permission dialog from crashing UiAutomator2 mid-test.
        grantRuntimePermissions(driver, config.get("app.package"));

        return driver;
    }

    private static void grantRuntimePermissions(AndroidDriver driver, String appPackage) {
        List<String> permissions = List.of(
            "android.permission.POST_NOTIFICATIONS"
        );
        for (String permission : permissions) {
            try {
                driver.executeScript("mobile: shell", Map.of(
                    "command", "pm",
                    "args",    List.of("grant", appPackage, permission)
                ));
                log.info("Granted permission: {}", permission);
            } catch (Exception e) {
                log.warn("Could not grant permission '{}': {}", permission, e.getMessage());
            }
        }
    }

    // ────────────────────────── Private helpers ─────────────────────────────

    private static UiAutomator2Options buildOptions(ConfigReader config) {
        UiAutomator2Options options = new UiAutomator2Options();

        // Device identification — auto-detect via adb when not set in config
        String udid = config.getOrDefault("udid", "");
        String deviceName = config.getOrDefault("device.name", "");
        String platformVersion = config.getOrDefault("platform.version", "");

        if (udid.isBlank()) {
            DeviceDetector.DeviceInfo detected = DeviceDetector.detect();
            udid = detected.udid();
            if (deviceName.isBlank()) deviceName = detected.model();
            if (platformVersion.isBlank()) platformVersion = detected.platformVersion();
        }

        options.setUdid(udid);
        options.setDeviceName(deviceName.isBlank() ? "Android Device" : deviceName);
        if (!platformVersion.isBlank()) options.setPlatformVersion(platformVersion);

        // App under test — path relative to project root
        String appPath = System.getProperty("user.dir") + "/" + config.get("app.path");
        options.setApp(appPath);

        // App identifiers
        options.setAppPackage(config.get("app.package"));
        options.setAppActivity(config.get("app.activity"));

        // Fixed capabilities
        options.setAutomationName("UiAutomator2");
        options.setPlatformName("Android");

        // Stability options
        options.setAutoGrantPermissions(true);
        options.setNewCommandTimeout(Duration.ofSeconds(
            Long.parseLong(config.getOrDefault("new.command.timeout", "60"))
        ));
        options.setNoReset(Boolean.parseBoolean(config.getOrDefault("no.reset", "false")));
        options.setFullReset(Boolean.parseBoolean(config.getOrDefault("full.reset", "false")));
        options.setCapability("autoAcceptAlerts", true);
        options.setUiautomator2ServerInstallTimeout(Duration.ofMillis(
            Long.parseLong(config.getOrDefault("uiautomator2.server.install.timeout", "60000"))
        ));

        log.debug("UiAutomator2Options: udid={}, device={}, package={}, activity={}",
            udid, deviceName, config.get("app.package"), config.get("app.activity"));

        return options;
    }

    private static URL resolveServerUrl(String raw) {
        try {
            return URI.create(raw).toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Appium server URL: " + raw, e);
        }
    }
}
