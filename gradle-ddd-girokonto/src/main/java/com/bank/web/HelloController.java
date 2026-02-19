package com.bank.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bank.service.GirokontoService;

@RestController
@RequestMapping("/")
public class HelloController {

    @Autowired
    private GirokontoService girokontoService;

    @GetMapping
    public String getAllGirokontos() {
        girokontoService.getAllGirokontos();
        return girokontoService.getAllGirokontos().toString();
    }

}