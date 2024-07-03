package it.teems.codingtask.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class Controller {

    @GetMapping("/health")
    public Object hello() {
        return Map.of("status", "UP");
    }
}
