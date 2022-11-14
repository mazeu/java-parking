package com.marcio.parking.controller;

import com.marcio.parking.controller.dto.ParkingCreateDTO;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import springfox.documentation.spring.web.scanners.MediaTypeReader;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ParkingControllerTest {

    @LocalServerPort
    private int randomPort;

    @BeforeEach
    public void setUpTest(){
        System.out.println(randomPort);
        RestAssured.port = randomPort;

    }

    @Test
    void whenFindAllThenCheckResult(){
        RestAssured.given()
                .when()
                .get("/parking")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("license[0]", Matchers.equalTo("GGG-3333"));
    }


    @Test
    void whenCreateThenCheckIsCreated() {

        var createDTO = new ParkingCreateDTO();
        createDTO.setColor("AMARELO");
        createDTO.setLicense("WRT-5555");
        createDTO.setModel("POLO");
        createDTO.setState("ES");

        RestAssured.given()
                .when()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createDTO)
                .post("/parking")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("license", Matchers.equalTo("WRT-5555"));
    }
}