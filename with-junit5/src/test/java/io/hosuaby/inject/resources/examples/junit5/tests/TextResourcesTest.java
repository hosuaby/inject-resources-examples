package io.hosuaby.inject.resources.examples.junit5.tests;

import com.adelean.inject.resources.junit.jupiter.GivenTextResource;
import com.adelean.inject.resources.junit.jupiter.TestWithResources;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@TestWithResources  // <-- Add @TestWithResources extension
@DisplayName("Demo how to read text file resources in JUnit 5 (Jupiter) tests")
public class TextResourcesTest {

    // Read content of resource alice.txt into String field
    @GivenTextResource("/io/hosuaby/alice.txt")
    String fieldWithText;

    @Test
    void testWithTextFromResource() {
        // We can use content of resource file in our test
        assertThat(fieldWithText)
                .isNotNull()
                .isNotEmpty()
                .isNotBlank()
                .contains("Alice");
    }
}
