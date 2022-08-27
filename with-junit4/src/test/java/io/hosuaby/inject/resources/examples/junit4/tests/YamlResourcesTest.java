package io.hosuaby.inject.resources.examples.junit4.tests;

import com.adelean.inject.resources.junit.vintage.yaml.YamlDocumentsResource;
import com.adelean.inject.resources.junit.vintage.yaml.YamlResource;
import io.hosuaby.inject.resources.examples.junit4.domain.LogSeverity;
import io.hosuaby.inject.resources.examples.junit4.domain.Person;
import io.hosuaby.inject.resources.examples.junit4.domain.YamlLog;
import org.junit.Rule;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static com.adelean.inject.resources.junit.vintage.GivenResource.givenResource;
import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.type;

public class YamlResourcesTest {
    Yaml yaml = new Yaml();
    Yaml logParser = new Yaml(new Constructor(YamlLog.class));

    @Rule   // Declare rule to read content of resource into the field
    public YamlResource<Person> spongeBob = givenResource()
            .yaml("/io/hosuaby/sponge-bob.yaml")
            .parseWith(yaml);

    @Rule   // Declare rule to read content of resource into the field
    public YamlDocumentsResource<List<YamlLog>> logs = givenResource()
            .yamlDocuments("/io/hosuaby/logs.yml")
            .parseWith(logParser);

    @Test
    public void testWithYamlFromResource() {
        // We can use content of resource file in our test
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
    public void testWithYamlDocumentsFromResource() {
        // We can use content of resource file in our test
        assertThat(logs.get())
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
