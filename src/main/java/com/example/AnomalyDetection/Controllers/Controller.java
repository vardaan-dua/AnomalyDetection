package com.example.AnomalyDetection.Controllers;

import com.example.AnomalyDetection.RepeatingPattern.CheckRepitition;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping(path = "/")
    public String home() {
        return "<h1>Welcome</h1>";
    }
}
