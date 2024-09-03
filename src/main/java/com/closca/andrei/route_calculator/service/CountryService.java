package com.closca.andrei.route_calculator.service;

import com.closca.andrei.route_calculator.exception.RouteNotFoundException;
import com.closca.andrei.route_calculator.model.Country;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

import org.springframework.core.io.ClassPathResource;

@Service
public class CountryService {

    private final Map<String, Country> countryMap = new HashMap<>();

    private final ObjectMapper mapper;

    public CountryService(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @PostConstruct
    public void loadCountriesData() throws IOException {
        List<Country> countries = mapper.readValue(new ClassPathResource("countries.json").getInputStream(),
                new TypeReference<>() {
                });

        for (Country country : countries) {
            countryMap.put(country.getCca3(), country);
        }
    }

    public List<String> findRoute(String origin, String destination) {
        if (!countryMap.containsKey(origin) || !countryMap.containsKey(destination)) {
            throw new RouteNotFoundException("Invalid country code");
        }

        if (origin.equals(destination)) {
            return List.of(origin);
        }

        return calculateRoute(origin, destination);
    }

    private List<String> calculateRoute(String origin, String destination) {
        Queue<List<String>> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.add(List.of(origin));
        visited.add(origin);

        while (!queue.isEmpty()) {
            List<String> path = queue.poll();
            String lastCountry = path.get(path.size() - 1);

            for (String neighbor : countryMap.get(lastCountry).getBorders()) {
                if (neighbor.equals(destination)) {
                    path = new ArrayList<>(path);
                    path.add(neighbor);
                    return path;
                }

                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    List<String> newPath = new ArrayList<>(path);
                    newPath.add(neighbor);
                    queue.add(newPath);
                }
            }
        }

        throw new RouteNotFoundException("No land route found between " + origin + " and " + destination);
    }
}
