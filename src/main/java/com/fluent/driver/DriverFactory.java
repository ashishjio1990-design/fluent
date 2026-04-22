package com.fluent.driver;

import io.appium.java_client.android.AndroidDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Thread-safe lifecycle manager for AndroidDriver instances.
 * One driver per thread — supports parallel test execution.
 * Driver creation is delegated to AndroidDriverFactory.
 */
public final class DriverFactory {

    private static final Logger log = LoggerFactory.getLogger(DriverFactory.class);

    private static final ThreadLocal<AndroidDriver> DRIVER_THREAD_LOCAL = new ThreadLocal<>();

    private DriverFactory() {}

    // ─────────────────────────── Public API ────────────────────────────────

    public static void initDriver() {
        AndroidDriver driver = AndroidDriverFactory.create();
        DRIVER_THREAD_LOCAL.set(driver);
        log.info("Driver initialised on thread: {}", Thread.currentThread().getName());
    }

    public static AndroidDriver getDriver() {
        AndroidDriver driver = DRIVER_THREAD_LOCAL.get();
        if (driver == null) {
            throw new IllegalStateException(
                "No AndroidDriver for thread [" + Thread.currentThread().getName() + "]. " +
                "Call DriverFactory.initDriver() first."
            );
        }
        return driver;
    }

    public static void quitDriver() {
        AndroidDriver driver = DRIVER_THREAD_LOCAL.get();
        if (driver != null) {
            try {
                driver.quit();
                log.info("Driver quit on thread: {}", Thread.currentThread().getName());
            } catch (Exception e) {
                log.warn("Exception while quitting driver: {}", e.getMessage());
            } finally {
                DRIVER_THREAD_LOCAL.remove();
            }
        }
    }

    public static boolean isDriverActive() {
        return DRIVER_THREAD_LOCAL.get() != null;
    }
}
