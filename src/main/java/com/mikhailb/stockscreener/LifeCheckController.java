package com.mikhailb.stockscreener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
public class LifeCheckController {

    @Autowired
    private DatabaseProperties dbProperties;

    @GetMapping(value = "/life-check")
    public String lifeCheck() {
        return String.format("connection = %s", dbProperties.getConnection());
    }

}
