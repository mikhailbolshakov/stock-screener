package com.mikhailb.stockscreener.controller;

import com.mikhailb.stockscreener.configuration.StorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LifeCheckController {

    @Autowired
    private StorageProperties storageProperties;

    @GetMapping(value = "/life-check")
    public String lifeCheck() {
        return String.format("connection = %s", storageProperties.getConnection().getHost());
    }

}
