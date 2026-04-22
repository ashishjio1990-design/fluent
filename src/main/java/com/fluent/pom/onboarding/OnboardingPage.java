package com.fluent.pom.onboarding;

import com.fluent.base.BasePage;
import com.fluent.pom.registration.RegistrationPage;
import com.fluent.pom.signin.SignInPage;
import io.appium.java_client.AppiumBy;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class OnboardingPage extends BasePage {

    // ─────────────────────────── Locators ──────────────────────────────────

    private static final By SKIP_BTN         = AppiumBy.id("com.fluenthealth.app:id/btnSkip");
    private static final By SKIP_TOOLBAR_BTN = AppiumBy.id("com.fluenthealth.app:id/btnSkipToolbar");
    private static final By GET_STARTED_BTN  = AppiumBy.id("com.fluenthealth.app:id/onboardingWelcomeGetStartedBtn");
    private static final By SIGN_IN_BTN      = AppiumBy.id("com.fluenthealth.app:id/onboardingWelcomeSignInBtn");

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

    @Step("Tap Get Started on onboarding welcome screen")
    public RegistrationPage tapGetStarted() {
        log.info("Tapping Get Started on onboarding welcome screen");
        tap(GET_STARTED_BTN);
        return new RegistrationPage();
    }

    @Step("Tap Sign In on onboarding welcome screen")
    public SignInPage tapSignIn() {
        log.info("Tapping Sign In on onboarding welcome screen");
        tap(SIGN_IN_BTN);
        return new SignInPage();
    }

    // ─────────────────────────── Assertions ────────────────────────────────

    public boolean isDisplayed() {
        return isVisible(SKIP_BTN) || isVisible(SKIP_TOOLBAR_BTN);
    }
}
