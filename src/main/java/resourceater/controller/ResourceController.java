package resourceater.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jonatan Ivanov
 */
@RestController("/resources")
public class ResourceController {
    @GetMapping
    public String hi() {
        return "hi";
    }
}
