package io.hosuaby.inject.resources.examples.junit4.tests;

import com.adelean.inject.resources.junit.vintage.json.JsonLinesResource;
import com.adelean.inject.resources.junit.vintage.json.JsonResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.hosuaby.inject.resources.examples.junit4.domain.Log;
import io.hosuaby.inject.resources.examples.junit4.domain.LogSeverity;
import io.hosuaby.inject.resources.examples.junit4.domain.Person;
import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static com.adelean.inject.resources.junit.vintage.GivenResource.givenResource;
import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.type;

public class JsonResourcesTest {
    ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Rule   // Declare rule to read content of resource into the field
    public JsonResource<Person> spongeBob = givenResource()
            .json("/io/hosuaby/sponge-bob.json")
            .parseWith(objectMapper);

    @Rule   // Declare rule to read content of resource into the field
    public JsonLinesResource<List<Log>> logs = givenResource()
            .jsonLines("/io/hosuaby/logs.jsonl")
            .parseWith(objectMapper);

    @Test
    public void testWithJsonFromResource() {
        // We can use parsed content of resource file in our test
        assertThat(spongeBob.get())
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
    public void testWithJsonLinesFromResource() {
        // We can use parsed content of resource file in our test
        assertThat(logs.get())
                .isNotNull()
                .isNotEmpty()
                .hasSize(3)
                .containsExactly(
                        new Log(LocalDateTime.of(2012, 1, 1, 2, 0, 1), LogSeverity.ERROR, "Foo failed"),
                        new Log(LocalDateTime.of(2012, 1, 1, 2, 4, 2), LogSeverity.INFO, "Bar was successful"),
                        new Log(LocalDateTime.of(2012, 1, 1, 2, 10, 12), LogSeverity.DEBUG, "Baz was notified"));
    }
}
