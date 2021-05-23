package com.crr.restful.webservices.restfulwebservices.Filtering;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilteringController {

    @GetMapping("/filtering")
    public SomeBean someBean() {
        return new SomeBean("value1", "value2", "value3");
    }
}
