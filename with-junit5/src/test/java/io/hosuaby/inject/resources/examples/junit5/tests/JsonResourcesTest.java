package io.hosuaby.inject.resources.examples.junit5.tests;

import com.adelean.inject.resources.junit.jupiter.GivenJsonLinesResource;
import com.adelean.inject.resources.junit.jupiter.GivenJsonResource;
import com.adelean.inject.resources.junit.jupiter.TestWithResources;
import com.adelean.inject.resources.junit.jupiter.WithJacksonMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.hosuaby.inject.resources.examples.junit5.domain.Log;
import io.hosuaby.inject.resources.examples.junit5.domain.LogSeverity;
import io.hosuaby.inject.resources.examples.junit5.domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.type;

@TestWithResources  // <-- Add @TestWithResources extension
@DisplayName("Demo how to read JSON file resources in JUnit 5 (Jupiter) tests")
public class JsonResourcesTest {

    // Configure ObjectMapper that will be used to parse content of resources.
    // If no ObjectMapper is annotated with @WithJacksonMapper, default mapper created by new ObjectMapper()
    // will be used.
    @WithJacksonMapper
    ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    // Read content of resource sponge-bob.json into typed field
    // Important: jackson-databind must be on classpath !
    @GivenJsonResource("/io/hosuaby/sponge-bob.json")
    Person spongeBob;

    @GivenJsonLinesResource("/io/hosuaby/logs.jsonl")
    List<Log> logs;

    @Test
    void testWithJsonFromResource() {
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

    @Test
    void testWithJsonLinesFromResource() {
        // We can use parsed content of resource file in our test
        assertThat(logs)
                .isNotNull()
                .isNotEmpty()
                .hasSize(3)
                .containsExactly(
                        new Log(LocalDateTime.of(2012, 1, 1, 2, 0, 1), LogSeverity.ERROR, "Foo failed"),
                        new Log(LocalDateTime.of(2012, 1, 1, 2, 4, 2), LogSeverity.INFO, "Bar was successful"),
                        new Log(LocalDateTime.of(2012, 1, 1, 2, 10, 12), LogSeverity.DEBUG, "Baz was notified"));
    }
}
