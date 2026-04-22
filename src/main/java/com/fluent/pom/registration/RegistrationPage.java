package com.fluent.pom.registration;

import com.fluent.base.BasePage;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class RegistrationPage extends BasePage {

    // ─────────────────────────── Locators ──────────────────────────────────

    private static final By HEADER_TV     = AppiumBy.id("com.fluenthealth.app:id/headerTV");
    private static final By FIRST_NAME_ET = AppiumBy.id("com.fluenthealth.app:id/firstNameET");
    private static final By LAST_NAME_ET  = AppiumBy.id("com.fluenthealth.app:id/lastNameET");
    private static final By EMAIL_ET      = AppiumBy.id("com.fluenthealth.app:id/emailET");
    private static final By MOBILE_ET     = AppiumBy.id("com.fluenthealth.app:id/contactNumberET");
    private static final By CONTINUE_BTN  = AppiumBy.id("com.fluenthealth.app:id/button");
    private static final By BACK_BTN      = AppiumBy.id("com.fluenthealth.app:id/onboardingBackIv");

    // ─────────────────────────── Actions ───────────────────────────────────

    public RegistrationPage enterFirstName(String firstName) {
        log.info("Entering first name: {}", firstName);
        enterText(FIRST_NAME_ET, firstName);
        return this;
    }

    public RegistrationPage enterLastName(String lastName) {
        log.info("Entering last name: {}", lastName);
        enterText(LAST_NAME_ET, lastName);
        return this;
    }

    public RegistrationPage enterEmail(String email) {
        log.info("Entering email: {}", email);
        enterText(EMAIL_ET, email);
        return this;
    }

    public RegistrationPage enterMobileNumber(String mobile) {
        log.info("Entering mobile number: {}", mobile);
        enterText(MOBILE_ET, mobile);
        return this;
    }

    public RegistrationPage fillForm(String firstName, String lastName, String email, String mobile) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterEmail(email);
        enterMobileNumber(mobile);
        return this;
    }

    public RegistrationPage scrollToContinueButton() {
        scrollToText("Continue");
        return this;
    }

    public void tapContinue() {
        log.info("Scrolling to and tapping Continue button");
        scrollToText("Continue");
        tap(CONTINUE_BTN);
    }

    public void tapBack() {
        tap(BACK_BTN);
    }

    // ─────────────────────────── Assertions ────────────────────────────────

    public boolean isDisplayed() {
        try {
            if (!isVisible(HEADER_TV)) return false;
            return "Let's get started".equals(getText(HEADER_TV));
        } catch (Exception e) {
            return false;
        }
    }

    public String getHeaderText() {
        return getText(HEADER_TV);
    }

    public boolean isContinueEnabled() {
        return getElement(CONTINUE_BTN).isEnabled();
    }
}
