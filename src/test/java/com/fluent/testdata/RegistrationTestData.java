package com.fluent.testdata;

/**
 * Test data for registration flow tests.
 *
 * Keep all registration-related test inputs here so that test classes
 * stay focused on behaviour rather than data.
 */
public final class RegistrationTestData {

    private RegistrationTestData() { /* utility class */ }

    // ─────────────────────────── Valid user ────────────────────────────────

    public static final String VALID_FIRST_NAME = "ashish";
    public static final String VALID_LAST_NAME  = "taralkar";
    public static final String VALID_EMAIL      = "ashishjio1990@gmail.com";
    public static final String VALID_MOBILE     = "9987741779";

    // ─────────────────────────── MPIN ──────────────────────────────────────

    public static final String VALID_MPIN       = "000000";

    // ─────────────────────────── Profile setup ─────────────────────────────

    public static final String GENDER           = "Male";
    public static final String SEX_AT_BIRTH     = "Male";
}
