package com.rashmi.student.layer;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentIntegrationTest {


    @Test
    @Order(1)
    void getStudents() throws Exception {
        String response = get("/students").body().asString();
        JSONAssert.assertEquals(getJson("get-students.json"), response, true);
    }

    @Test
    @Order(2)
    void createContact() throws Exception {
        RequestSpecification request = given();
        request.header("content-type", MediaType.APPLICATION_JSON_VALUE);
        request.body(getJson("post-student.json"));
        Response response = request.post("/students").andReturn();
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
        String location = response.getHeader("location");
        System.out.println("post method body" + response.asString());
        assertTrue(String.format("%s should end with /students/4", location), location.endsWith("/students/4"));
    }

    @Test
    @Order(3)
    void deleteContact() {
        assertEquals(HttpStatus.OK.value(), get("/students/4").andReturn().getStatusCode());
        assertEquals(HttpStatus.NO_CONTENT.value(), delete("/students/4").andReturn().getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND.value(), get("/students/4").andReturn().getStatusCode());
    }

    @Test
    @Order(4)
    void getStudentById() throws Exception {
        String response = get("/students/1").body().asString();
        JSONAssert.assertEquals(getJson("get-student.json"), response, true);
    }

    private String getJson(String fileName) throws Exception {
        URL resource = getClass().getResource("/" + fileName);
        Path path = Paths.get(resource.toURI());
        return Files.lines(path).collect(Collectors.joining("\n"));

    }

}
