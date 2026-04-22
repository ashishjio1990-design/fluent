package com.fluent.pom.signin;

import com.fluent.base.BasePage;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class SignInPage extends BasePage {

    // ─────────────────────────── Locators ──────────────────────────────────

    private static final By PHONE_ET   = AppiumBy.id("com.fluenthealth.app:id/phoneET");
    private static final By SUBMIT_BTN = AppiumBy.id("com.fluenthealth.app:id/button");
    private static final By PIN_CODE   = AppiumBy.id("com.fluenthealth.app:id/pinCode");
    private static final By HOME_VIEW  = AppiumBy.androidUIAutomator(
            "new UiSelector().className(\"android.view.View\").instance(1)");

    // ─────────────────────────── Actions ───────────────────────────────────

    public SignInPage enterPhone(String phone) {
        log.info("Tapping phone field and entering number: {}", phone);
        tap(PHONE_ET);
        pressDigits(phone);
        return this;
    }

    public SignInPage tapSubmit() {
        log.info("Tapping Submit button");
        tap(SUBMIT_BTN);
        return this;
    }

    public SignInPage enterPin(String pin) {
        log.info("Entering PIN");
        enterText(PIN_CODE, pin);
        return this;
    }

    // ─────────────────────────── Assertions ────────────────────────────────

    public boolean isHomeViewDisplayed() {
        return isVisible(HOME_VIEW);
    }
}
