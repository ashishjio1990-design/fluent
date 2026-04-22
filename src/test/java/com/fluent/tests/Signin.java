package com.fluent.tests;

import com.fluent.base.BaseTest;
import com.fluent.testdata.SignInTestData;
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
@Feature("Sign In")
class Signin extends BaseTest {

        @Test
        @DisplayName("TC-001: Successful login with valid credentials")
        @Description("Verifies that a user can log in using a valid mobile number and PIN.")
        @Story("User sign in")
        @Severity(SeverityLevel.CRITICAL)
        void tc001_successfulLoginWithValidCredentials() {

                resetApp();
                pages.welcome.allowPermissionDialogIfPresent();

                // Step 1 — Verify Welcome screen and tap Log In.
                pages.onboarding.tapSignIn();

                // Step 2 — Enter mobile number and tap Submit
                pages.signIn.enterPhone(SignInTestData.VALID_MOBILE);
                pages.signIn.tapSubmit();

                // Step 3 — Wait for OTP/PIN screen to load
                try {
                        Thread.sleep(3000);
                } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                }

                // Step 4 — Enter PIN
                pages.signIn.enterPin(SignInTestData.VALID_PIN);

                // Step 5 — Verify home view is displayed after login
                Assertions.assertTrue(pages.signIn.isHomeViewDisplayed(),
                                "Home view should be visible after successful login");

                // Step 6 — Logout
                pages.home.logout();
                pages.signIn.enterPin(SignInTestData.VALID_PIN);
                Assertions.assertTrue(pages.signIn.isHomeViewDisplayed(),
                                "Home view should be visible after successful login");
                pages.home.logout();
                pages.home.logout();
                System.out.println("Sign-in test completed.");
        }
}
