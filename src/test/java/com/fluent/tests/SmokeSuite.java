package com.fluent.tests;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("com.fluent.tests")
@IncludeTags("smoke")
public class SmokeSuite {
}
