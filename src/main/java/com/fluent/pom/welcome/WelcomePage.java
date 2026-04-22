package com.fluent.pom.welcome;

import com.fluent.base.BasePage;
import com.fluent.pom.login.LoginPage;
import com.fluent.pom.onboarding.OnboardingPage;
import com.fluent.utils.WaitUtils;
import io.appium.java_client.AppiumBy;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class WelcomePage extends BasePage {

    // ─────────────────────────── Locators ──────────────────────────────────

    private static final By CREATE_ACCOUNT_BTN = AppiumBy.id("com.fluenthealth.app:id/onboardingWelcomeGetStartedBtn");
    private static final By LOG_IN_BTN         = AppiumBy.id("com.fluenthealth.app:id/onboardingWelcomeSignInBtn");
    private static final By DESCRIPTION_TV     = AppiumBy.id("com.fluenthealth.app:id/onboardingWelcomeDescriptionTv");

    // ─────────────────────────── Actions ───────────────────────────────────

    @Step("Tap Create account")
    public OnboardingPage tapCreateAccount() {
        log.info("Tapping Create account");
        tap(CREATE_ACCOUNT_BTN);
        return new OnboardingPage();
    }

    @Step("Tap Log in")
    public LoginPage tapLogIn() {
        log.info("Tapping Log in");
        tap(LOG_IN_BTN);
        return new LoginPage();
    }

    // ─────────────────────────── Assertions ────────────────────────────────

    public boolean isDisplayed() {
        try {
            WaitUtils.waitForVisibility(CREATE_ACCOUNT_BTN, 30);
            return true;
        } catch (Exception e) {
            log.warn("Welcome screen not visible within 30s: {}", e.getMessage());
            return false;
        }
    }

    public String getDescriptionText() {
        return getText(DESCRIPTION_TV);
    }
}
