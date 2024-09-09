package com.adp.changeservice.bdd.steps;

import com.adp.changeservice.ChangeServiceApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Class to use spring application context while running cucumber
 */
@ContextConfiguration()
@SpringBootTest(
    classes = {
        ChangeServiceApplication.class
    },
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@CucumberContextConfiguration
public class CucumberSpringContextConfiguration {
}
