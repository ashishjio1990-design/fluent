package com.fluent.pom.mpin;

import com.fluent.base.BasePage;
import com.fluent.pom.profile.ProfileSetupPage;
import com.fluent.utils.WaitUtils;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class MpinPage extends BasePage {

    // ─────────────────────────── Locators ──────────────────────────────────

    private static final By PIN_CODE         = AppiumBy.id("com.fluenthealth.app:id/pinCode");
    private static final By PIN_CONFIRM_CODE = AppiumBy.id("com.fluenthealth.app:id/pinConfirmCode");

    // ─────────────────────────── Actions ───────────────────────────────────

    public MpinPage enterPin(String pin) {
        log.info("Tapping pinCode field and entering MPIN");
        try { Thread.sleep(5000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        WaitUtils.waitForVisibility(PIN_CODE);
        tap(PIN_CODE);
        pressDigits(pin);
        try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        hideKeyboardSafely();
        return this;
    }

    public ProfileSetupPage confirmPin(String pin) {
        log.info("Tapping pinConfirmCode field and confirming MPIN");
        tap(PIN_CONFIRM_CODE);
        pressDigits(pin);
        return new ProfileSetupPage();
    }

    // ─────────────────────────── Assertions ────────────────────────────────

    public boolean isDisplayed() {
        return isVisible(PIN_CODE);
    }
}
