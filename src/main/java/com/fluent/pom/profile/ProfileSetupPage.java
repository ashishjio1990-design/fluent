package com.fluent.pom.profile;

import com.fluent.base.BasePage;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class ProfileSetupPage extends BasePage {

    // ─────────────────────────── Locators ──────────────────────────────────

    private static final By DOB_CALENDAR_IV      = AppiumBy.id("com.fluenthealth.app:id/iv_calendar");
    private static final By DATE_PICKER_OK_BTN   = AppiumBy.id("android:id/button1");
    private static final By DOWN_ARROW_IV        = AppiumBy.id("com.fluenthealth.app:id/downArrowIV");
    private static final By FINISH_SETUP_BTN     = AppiumBy.id("com.fluenthealth.app:id/finishSetupBtn");

    // ─────────────────────────── Actions ───────────────────────────────────

    public ProfileSetupPage tapDobCalendarIcon() {
        log.info("Tapping DOB calendar icon");
        tap(DOB_CALENDAR_IV);
        return this;
    }

    public ProfileSetupPage tapDatePickerOk() {
        log.info("Tapping OK on date picker dialog");
        tap(DATE_PICKER_OK_BTN);
        return this;
    }

    public ProfileSetupPage tapDownArrow() {
        log.info("Tapping DOB section down arrow");
        tap(DOWN_ARROW_IV);
        return this;
    }

    public ProfileSetupPage selectGender(String gender) {
        log.info("Selecting gender: {}", gender);
        tap(AppiumBy.id("com.fluenthealth.app:id/sexAssignedAtBirthET"));
        tap(AppiumBy.id("com.fluenthealth.app:id/downArrowSexAssignedAtBirthETIV"));
        tap(AppiumBy.id("com.fluenthealth.app:id/sexAssignedAtBirthET"));
        tap(AppiumBy.id("com.fluenthealth.app:id/whyWeAskTV"));
        tap(AppiumBy.id("com.fluenthealth.app:id/finishStepBtn"));
        return this;
    }

    public void tapFinishSetup() {
        log.info("Tapping Finish Setup button");
        tap(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.ImageView\").instance(4)"));
        tap(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.View\").instance(1)"));
        tap(AppiumBy.androidUIAutomator(
            "new UiScrollable(new UiSelector().scrollable(true))" +
            ".scrollIntoView(new UiSelector().resourceId(\"com.fluenthealth.app:id/tv_delete_account_and_all_personal_data\"))"));
        tap(AppiumBy.id("com.fluenthealth.app:id/button"));
        tap(AppiumBy.id("com.fluenthealth.app:id/btn_two"));
    }

    // ─────────────────────────── Assertions ────────────────────────────────

    public boolean isDisplayed() {
        return isVisible(DOB_CALENDAR_IV) || isVisible(FINISH_SETUP_BTN);
    }
}
