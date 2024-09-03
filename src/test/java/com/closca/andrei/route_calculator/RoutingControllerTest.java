package com.closca.andrei.route_calculator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoutingControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void given_aValidRoute_when_callingTheApi_then_responseIsSuccessfully() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/routing/CZE/ITA", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("{\"route\":[\"CZE\",\"AUT\",\"ITA\"]}", response.getBody());
    }

    @Test
    public void given_aInvalidRoute_when_callingTheApi_then_responseIsWithFailure() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/routing/CZE/AUS", String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}