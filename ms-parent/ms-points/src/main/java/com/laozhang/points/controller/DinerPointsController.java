package com.laozhang.points.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DinerPointsController {

    @GetMapping("points")
    public Object points() {

        return "points";
    }
}
