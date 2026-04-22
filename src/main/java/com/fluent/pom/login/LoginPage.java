package com.fluent.pom.login;

import com.fluent.base.BasePage;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class LoginPage extends BasePage {

    // ─────────────────────────── Locators ──────────────────────────────────

    private static final By EMAIL_ET    = AppiumBy.id("com.fluenthealth.app:id/emailET");
    private static final By PASSWORD_ET = AppiumBy.id("com.fluenthealth.app:id/passwordET");
    private static final By LOGIN_BTN   = AppiumBy.id("com.fluenthealth.app:id/loginBtn");
    private static final By BACK_BTN    = AppiumBy.id("com.fluenthealth.app:id/onboardingBackIv");

    // ─────────────────────────── Actions ───────────────────────────────────

    public LoginPage enterEmail(String email) {
        log.info("Entering email: {}", email);
        enterText(EMAIL_ET, email);
        return this;
    }

    public LoginPage enterPassword(String password) {
        log.info("Entering password");
        enterText(PASSWORD_ET, password);
        return this;
    }

    public void tapLogin() {
        log.info("Tapping Log in button");
        tap(LOGIN_BTN);
    }

    public void tapBack() {
        tap(BACK_BTN);
    }

    // ─────────────────────────── Assertions ────────────────────────────────

    public boolean isDisplayed() {
        return isVisible(EMAIL_ET);
    }
}
