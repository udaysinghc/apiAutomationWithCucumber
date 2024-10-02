package com.api.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/java/features",
        plugin = {
                "pretty",
                "html:target/site/cucumber-pretty",
                "json:target/cucumber/cucumber.json",
                "usage:target/usage.jsonx",
                "junit:target/cucumber.xml"
        },
    glue = "com/api/stepDefinitions")

public class TestRunner extends AbstractTestNGCucumberTests {}
