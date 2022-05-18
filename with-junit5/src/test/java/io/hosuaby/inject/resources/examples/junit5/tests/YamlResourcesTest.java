package io.hosuaby.inject.resources.examples.junit5.tests;

import com.adelean.inject.resources.junit.jupiter.GivenYamlDocumentsResource;
import com.adelean.inject.resources.junit.jupiter.GivenYamlResource;
import com.adelean.inject.resources.junit.jupiter.TestWithResources;
import com.adelean.inject.resources.junit.jupiter.WithSnakeYaml;
import io.hosuaby.inject.resources.examples.junit5.domain.LogSeverity;
import io.hosuaby.inject.resources.examples.junit5.domain.Person;
import io.hosuaby.inject.resources.examples.junit5.domain.YamlLog;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.type;

@TestWithResources  // <-- Add @TestWithResources extension
@DisplayName("Demo how to read YAML file resources in JUnit 5 (Jupiter) tests")
public class YamlResourcesTest {

    @WithSnakeYaml("default")
    Yaml yaml = new Yaml();

    @WithSnakeYaml("log-parser")
    Yaml logParser = new Yaml(new Constructor(YamlLog.class));

    @GivenYamlResource("/io/hosuaby/sponge-bob.yaml")
    Person spongeBob;

    @GivenYamlDocumentsResource(from = "/io/hosuaby/logs.yml", yaml = "log-parser")
    List<YamlLog> logs;

    @Test
    public void testWithYamlFromResource() {
        // We can use content of resource file in our test
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
    void testWithYamlDocumentsFromResource() {
        // We can use content of resource file in our test
        assertThat(logs)
                .isNotNull()
                .isNotEmpty()
                .hasSize(3)
                .containsExactly(
                        new YamlLog(
                                Date.from(LocalDateTime.of(2012, 1, 1, 2, 0, 1).atZone(ZoneId.of("UTC")).toInstant()),
                                LogSeverity.ERROR,
                                "Foo failed"),
                        new YamlLog(
                                Date.from(LocalDateTime.of(2012, 1, 1, 2, 4, 2).atZone(ZoneId.of("UTC")).toInstant()),
                                LogSeverity.INFO,
                                "Bar was successful"),
                        new YamlLog(
                                Date.from(LocalDateTime.of(2012, 1, 1, 2, 10, 12).atZone(ZoneId.of("UTC")).toInstant()),
                                LogSeverity.DEBUG,
                                "Baz was notified"));
    }
}
