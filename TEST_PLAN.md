# Fluent Health — Mobile Test Plan

> IMPORTANT: Before writing any test class, read this file first.
> All test data, flows, and expected results are defined here.

---

## TC-001 — Successful Registration with Valid Details

**Feature:** User Registration
**Priority:** P0 — Critical path

### Flow
1. Launch app → Welcome screen
2. Tap **Create account** → Onboarding video plays
3. Tap **Skip** → Registration form
4. Enter **First name:** `ashish`
5. Enter **Last name:** `taralkar`
6. Enter **Email:** `ashishjio1990@gmail.com`
7. Enter **Mobile:** `9987741779`
8. Tap **Continue**

### Expected Result
- User is navigated away from the registration screen (OTP / next onboarding step)
- No validation errors shown
- All fields accepted

### Element IDs (extracted from live UI dump)
| Field | Resource ID |
|---|---|
| Create account button | `com.fluenthealth.app:id/onboardingWelcomeGetStartedBtn` |
| Log in button | `com.fluenthealth.app:id/onboardingWelcomeSignInBtn` |
| Skip button (video) | `com.fluenthealth.app:id/btnSkip` |
| First name field | `com.fluenthealth.app:id/firstNameET` |
| Last name field | `com.fluenthealth.app:id/lastNameET` |
| Email field | `com.fluenthealth.app:id/emailET` |
| Mobile number field | `com.fluenthealth.app:id/contactNumberET` |
| Continue button | `com.fluenthealth.app:id/nextBtn` |
| Header label | `com.fluenthealth.app:id/headerTV` |

### Pages Required
- `WelcomePage`
- `OnboardingVideoPage`
- `RegistrationPage`

---

## Pending Test Cases (to be added)

| ID | Title | Priority |
|---|---|---|
| TC-002 | Registration with invalid email format | P1 |
| TC-003 | Registration with already registered email | P1 |
| TC-004 | Registration with blank required fields | P1 |
| TC-005 | Successful login with valid credentials | P0 |
| TC-006 | Login with invalid password | P1 |
