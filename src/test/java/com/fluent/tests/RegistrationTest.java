package com.fluent.tests;

import com.fluent.base.BaseTest;
import com.fluent.testdata.RegistrationTestData;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("regression")
@Feature("User Registration")
class RegistrationTest extends BaseTest {

    @Test
    @DisplayName("TC-001: Successful registration with mandatory field validation")
    @Description("Verifies all fields are mandatory and a user can complete full registration flow.")
    @Story("New user registration")
    @Severity(SeverityLevel.CRITICAL)
    void tc001_successfulRegistrationWithValidDetails() {
        // Step 1 — Verify Welcome screen is shown
        resetApp();
        pages.welcome.allowPermissionDialogIfPresent();
        Assertions.assertTrue(pages.welcome.isDisplayed(),
                "Welcome screen should be visible after app launch");

        // Step 2 — Tap Create account → onboarding video
        pages.welcome.tapCreateAccount();
        pages.onboardingVideo.allowPermissionDialogIfPresent();
        Assertions.assertTrue(pages.onboardingVideo.isDisplayed(),
                "Onboarding video screen should appear after tapping Create account");

        // Step 3 — Tap Skip → registration form
        pages.onboardingVideo.tapSkip();
        Assertions.assertTrue(pages.registration.isDisplayed(),
                "Registration screen should appear after tapping Skip");
        Assertions.assertEquals("Let's get started", pages.registration.getHeaderText(),
                "Registration header text mismatch");

        // Step 4 — Mandatory field validation: Continue stays disabled until all fields are filled
        pages.registration.enterFirstName(RegistrationTestData.VALID_FIRST_NAME);
       
        Assertions.assertFalse(pages.registration.isContinueEnabled(),
                "Continue should be disabled with only first name filled");

        pages.registration.enterLastName(RegistrationTestData.VALID_LAST_NAME);
        Assertions.assertFalse(pages.registration.isContinueEnabled(),
                "Continue should be disabled with only first name and last name filled");

        pages.registration.enterEmail(RegistrationTestData.VALID_EMAIL);
        Assertions.assertFalse(pages.registration.isContinueEnabled(),
                "Continue should be disabled with only first name, last name, and email filled");

        pages.registration.enterMobileNumber(RegistrationTestData.VALID_MOBILE);
         pages.registration.scrollToContinueButton();
        Assertions.assertTrue(pages.registration.isContinueEnabled(),
                "Continue should be enabled once all fields are filled");

        // Step 5 — Tap Continue
        pages.registration.tapContinue();

        // Step 6 — Enter MPIN
        pages.mpin.enterPin(RegistrationTestData.VALID_MPIN);

        // Step 7 — Confirm MPIN → transitions to profile setup
        pages.mpin.confirmPin(RegistrationTestData.VALID_MPIN);

        // Step 8 — Open date-of-birth calendar and confirm the default date
        pages.profileSetup
                .tapDobCalendarIcon()
                .tapDatePickerOk()
                .tapDownArrow();

        // Step 9 — Select gender
        pages.profileSetup.selectGender(RegistrationTestData.GENDER);

        // Step 10 — Tap Finish Setup
        pages.profileSetup.tapFinishSetup();
    }
}
