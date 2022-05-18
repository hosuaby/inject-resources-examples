package io.hosuaby.inject.resources.examples.junit5.tests;

import com.adelean.inject.resources.junit.jupiter.GivenJsonResource;
import com.adelean.inject.resources.junit.jupiter.TestWithResources;
import io.hosuaby.inject.resources.examples.junit5.domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.type;

@TestWithResources  // <-- Add @TestWithResources extension
@DisplayName("Demo how to read JSON file resources in JUnit 5 (Jupiter) tests")
public class JsonResourcesTest {

    // Read content of resource sponge-bob.json into typed field
    // Important: jackson-databind must be on classpath !
    @GivenJsonResource("/io/hosuaby/sponge-bob.json")
    Person spongeBob;

    @Test
    public void testWithJsonFromResource() {
        // We can use parsed content of resource file in our test
        assertThat(spongeBob)
                .isNotNull()
                .hasFieldOrPropertyWithValue("firstName", "Bob")
                .hasFieldOrPropertyWithValue("lastName", "Square Pants")
                .hasFieldOrPropertyWithValue("email", "sponge.bob@bikinibottom.io")
                .hasFieldOrPropertyWithValue("age", 22)
                .extracting("address", as(type(Person.Address.class)))
                .isNotNull()
                .hasFieldOrPropertyWithValue("address1", "ananas house")
                .hasFieldOrPropertyWithValue("city", "Bikini Bottom")
                .hasFieldOrPropertyWithValue("zipcode", 10101);
    }
}
