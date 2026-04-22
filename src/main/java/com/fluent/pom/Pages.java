package com.fluent.pom;

import com.fluent.pom.home.HomePage;
import com.fluent.pom.login.LoginPage;
import com.fluent.pom.mpin.MpinPage;
import com.fluent.pom.signin.SignInPage;
import com.fluent.pom.onboarding.OnboardingVideoPage;
import com.fluent.pom.profile.ProfileSetupPage;
import com.fluent.pom.registration.RegistrationPage;
import com.fluent.pom.welcome.WelcomePage;

/**
 * Single entry point for all page objects.
 * Instantiate once in BaseTest and access pages via fields.
 */
public class Pages {

    public final WelcomePage welcome;
    public final OnboardingVideoPage onboardingVideo;
    public final RegistrationPage registration;
    public final MpinPage mpin;
    public final ProfileSetupPage profileSetup;
    public final LoginPage login;
    public final SignInPage signIn;
    public final HomePage home;





    // onboarding constructror
    public Pages() {
        welcome       = new WelcomePage();
        onboardingVideo = new OnboardingVideoPage();
        registration  = new RegistrationPage();
        mpin          = new MpinPage();
        profileSetup  = new ProfileSetupPage();
        login         = new LoginPage();
        signIn        = new SignInPage();
        home          = new HomePage();
    }
}


// health profile constructor