package io.hexlet.spring.controller.welcome;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/welcome")
public class WelcomeController {

    @Value("${app.welcome-message}")
    public String welcomeMessage;

    @GetMapping
    public String getWelcomeMessage() {
        return welcomeMessage;
    }
}
