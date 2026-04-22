package com.fluent.pom.home;

import com.fluent.base.BasePage;
import io.appium.java_client.AppiumBy;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class HomePage extends BasePage {

    // ─────────────────────────── Locators ──────────────────────────────────

    private static final By HOME_CONTAINER = AppiumBy.id("com.fluenthealth.app:id/home_fragment");
    private static final By SETTINGS       = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.View\").instance(1)");

    // ─────────────────────────── Actions ───────────────────────────────────

    @Step("Tap home container")
    public HomePage tapHomeContainer() {
        log.info("Tapping home container");
        tap(HOME_CONTAINER);
        return this;
    }

    @Step("Tap settings")
    public HomePage tapSettings() {
        log.info("Tapping settings");
        tap(SETTINGS);
        return this;
    }

    @Step("Logout")
    public void logout() {
        log.info("Logging out");
        tapHomeContainer();
        tapSettings();
        driver().findElement(AppiumBy.androidUIAutomator(
            "new UiScrollable(new UiSelector().scrollable(true))" +
            ".scrollIntoView(new UiSelector().resourceId(\"com.fluenthealth.app:id/text_view_profile_settings_logout\"))"
        )).click();
    }

    // ─────────────────────────── Assertions ────────────────────────────────

    public boolean isDisplayed() {
        return isVisible(HOME_CONTAINER);
    }
}
