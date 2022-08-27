package io.hosuaby.inject.resources.examples.junit4.tests;

import com.adelean.inject.resources.junit.vintage.core.ResourceRule;
import org.junit.Rule;
import org.junit.Test;

import static com.adelean.inject.resources.junit.vintage.GivenResource.givenResource;
import static org.assertj.core.api.Assertions.assertThat;

public class TextResourcesTest {

    @Rule   // Declare rule to read content of resource into the field
    public ResourceRule<String> textResource = givenResource().text("/io/hosuaby/alice.txt");

    @Test
    public void testLoadTextIntoString() {
        // We can use content of resource file in our test
        assertThat(textResource.get())
                .isNotNull()
                .isNotEmpty()
                .isNotBlank()
                .contains("Alice");
    }
}
