package io.hosuaby.inject.resources.examples.spring;

import com.adelean.inject.resources.spring.JsonLinesResource;
import io.hosuaby.inject.resources.examples.spring.domain.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LogsController {

    @JsonLinesResource("logs.jsonl")    // <- inject parsed content of resource
    private List<Log> logs;

    @GetMapping("/inject-resources-examples/logs")
    public List<Log> getLogs() {
        return logs;
    }
}
