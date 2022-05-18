package io.hosuaby.inject.resources.examples.junit5.tests;

import com.adelean.inject.resources.junit.jupiter.GivenPropertiesResource;
import com.adelean.inject.resources.junit.jupiter.TestWithResources;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

@TestWithResources  // <-- Add @TestWithResources extension
@DisplayName("Demo how to read Java properties file resources in JUnit 5 (Jupiter) tests")
public class PropertiesResourcesTest {

    // Read content of resource db.properties into Properties field
    @GivenPropertiesResource("/io/hosuaby/db.properties")
    Properties dbProperties;

    @Test
    void testWithPropertiesFromResource() {
        // We can use parsed content of resource file in our test
        assertThat(dbProperties)
                .isNotNull()
                .isNotEmpty()
                .hasSize(3)
                .containsEntry("db.user", "hosuaby")
                .containsEntry("db.password", "password")
                .containsEntry("db.url", "localhost");
    }
}
