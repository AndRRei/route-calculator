package com.closca.andrei.route_calculator.controller;


import com.closca.andrei.route_calculator.service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/routing")
public class RoutingController {

    private final CountryService countryService;

    public RoutingController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/{origin}/{destination}")
    public ResponseEntity<?> getRoute(@PathVariable String origin, @PathVariable String destination) {
        List<String> route = countryService.findRoute(origin, destination);
        return ResponseEntity.ok(Collections.singletonMap("route", route));
    }
}
