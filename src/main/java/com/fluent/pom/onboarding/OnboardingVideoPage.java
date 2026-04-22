package com.fluent.pom.onboarding;

import com.fluent.base.BasePage;
import com.fluent.pom.registration.RegistrationPage;
import io.appium.java_client.AppiumBy;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class OnboardingVideoPage extends BasePage {

    // ─────────────────────────── Locators ──────────────────────────────────

    private static final By SKIP_BTN         = AppiumBy.id("com.fluenthealth.app:id/btnSkip");
    private static final By SKIP_TOOLBAR_BTN = AppiumBy.id("com.fluenthealth.app:id/btnSkipToolbar");

    // ─────────────────────────── Actions ───────────────────────────────────

    @Step("Tap Skip on onboarding video")
    public RegistrationPage tapSkip() {
        log.info("Tapping Skip on onboarding video screen");
        if (isVisible(SKIP_BTN)) {
            tap(SKIP_BTN);
        } else {
            tap(SKIP_TOOLBAR_BTN);
        }
        return new RegistrationPage();
    }

    // ─────────────────────────── Assertions ────────────────────────────────

    public boolean isDisplayed() {
        return isVisible(SKIP_BTN) || isVisible(SKIP_TOOLBAR_BTN);
    }
}
