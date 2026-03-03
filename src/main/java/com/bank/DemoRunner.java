package com.bank;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DemoRunner implements CommandLineRunner {

    private final DemoService demoService;

    public DemoRunner(DemoService demoService) {
        this.demoService = demoService;
    }

    @Override
    public void run(String... args) {
        demoService.demoAblauf();
    }
}