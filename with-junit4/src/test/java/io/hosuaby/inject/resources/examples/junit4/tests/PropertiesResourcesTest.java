package io.hosuaby.inject.resources.examples.junit4.tests;

import com.adelean.inject.resources.junit.vintage.properties.PropertiesResource;
import org.junit.Rule;
import org.junit.Test;

import static com.adelean.inject.resources.junit.vintage.GivenResource.givenResource;
import static org.assertj.core.api.Assertions.assertThat;

public class PropertiesResourcesTest {

    @Rule   // Declare rule to read content of resource into the field
    public PropertiesResource dbProperties = givenResource().properties("/io/hosuaby/db.properties");

    @Test
    public void testLoadProperties() {
        // We can use parsed content of resource file in our test
        assertThat(dbProperties.get())
                .isNotNull()
                .isNotEmpty()
                .hasSize(3)
                .containsEntry("db.user", "hosuaby")
                .containsEntry("db.password", "password")
                .containsEntry("db.url", "localhost");
    }
}
