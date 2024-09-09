package com.adp.changeservice.bdd.steps;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "json:build/reports/tests/change_service_test_cucumber.json"},
    features = "src/test/resources/features",
    tags = "not @ignore")
public class CucumberTests {
}
