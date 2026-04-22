package com.fluent.extensions;

import com.fluent.driver.DriverFactory;
import com.fluent.utils.ScreenshotUtils;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * JUnit 5 Extension that handles:
 *  - Logging test start / end
 *  - Screenshot capture on test failure (attached to Allure)
 *  - Driver teardown after each test (safety net — BaseTest also calls quitDriver)
 *
 * Register via @ExtendWith(TestLifecycleExtension.class) on BaseTest.
 */
public class TestLifecycleExtension
        implements BeforeEachCallback, AfterEachCallback, TestWatcher {

    private static final Logger log = LoggerFactory.getLogger(TestLifecycleExtension.class);

    // ─────────────────────────── BeforeEach ────────────────────────────────

    @Override
    public void beforeEach(ExtensionContext context) {
        log.info("━━━ START: {} ━━━", testDisplayName(context));
    }

    // ─────────────────────────── AfterEach ─────────────────────────────────

    @Override
    public void afterEach(ExtensionContext context) {
        // Driver quit is handled by BaseTest @AfterEach — this is a safety net
        if (DriverFactory.isDriverActive()) {
            log.warn("Driver still active after test '{}' — quitting via extension safety net",
                testDisplayName(context));
            DriverFactory.quitDriver();
        }
        log.info("━━━ END:   {} ━━━", testDisplayName(context));
    }

    // ─────────────────────────── TestWatcher ───────────────────────────────

    @Override
    public void testSuccessful(ExtensionContext context) {
        log.info("PASS ✔ {}", testDisplayName(context));
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        log.error("FAIL ✘ {} — {}", testDisplayName(context), cause.getMessage());
        ScreenshotUtils.captureAndAttach(sanitise(testDisplayName(context)));
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        log.warn("ABORTED ⚠ {} — {}", testDisplayName(context), cause.getMessage());
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        log.info("DISABLED ⊘ {} — {}", testDisplayName(context), reason.orElse("no reason"));
    }

    // ─────────────────────────── Helpers ───────────────────────────────────

    private String testDisplayName(ExtensionContext context) {
        return context.getTestClass().map(Class::getSimpleName).orElse("UnknownClass")
            + "#" + context.getDisplayName();
    }

    /** Strips characters that are invalid in file names. */
    private String sanitise(String name) {
        return name.replaceAll("[^a-zA-Z0-9_\\-]", "_");
    }
}
