package com.github.rshtishi.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rshtishi.demo.entity.Person;
import com.github.rshtishi.demo.repository.PersonRepository;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by User: Vu
 * Date: 13.06.2023
 * Time: 8:29
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonControllerTest {

    @Autowired
    private PersonRepository personRepository;

    private static final Logger logger = LoggerFactory.getLogger(PersonControllerTest.class);

    @Test
    @Order(4)
    public void getMethodHTTP() throws IOException {
        final HttpGet request = new HttpGet("http://localhost:8081/person");
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

            List<Person> persons = personRepository.findAll();

            logger.info("fetching all persons from DB in Pojo format");
            for (Person person: persons){
                logger.info(person.toString());
            }

            logger.info("fetching all persons from DB in JSON format");

            for (Person person: persons){
                String json = new ObjectMapper().writeValueAsString(person);
                logger.info(person.toString());
            }



            HttpResponse response = client.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            assertThat(statusCode, equalTo(HttpStatus.SC_OK));
        }
    }

    @Test
    @Order(1)
    public void postMethodHttp() throws IOException, JSONException {
        HttpPost httpPost = new HttpPost("http://localhost:8081/person");



        // Create the JSON payload
        JSONObject jsonPayload = new JSONObject();
        jsonPayload.put("firstname", "test");
        jsonPayload.put("lastname", "test");
        jsonPayload.put("birthdate", "1990-01-01");

        // Set the JSON payload as the request entity
        StringEntity requestEntity = new StringEntity(jsonPayload.toString(), ContentType.APPLICATION_JSON);
        httpPost.setEntity(requestEntity);

        try (CloseableHttpClient client = HttpClientBuilder.create().build();
             CloseableHttpResponse response = client.execute(httpPost)) {

            int statusCode = response.getStatusLine().getStatusCode();
            assertThat(statusCode, equalTo(HttpStatus.SC_CREATED));
        }


    }
    @Test
    @Order(2)
    public void putMethodHttp() throws IOException {
        String personJson = "{ \"id\": 1, \"firstName\": \"UpdatedFirstName\", \"lastName\": \"UpdatedLastName\", \"birthDate\": \"1990-05-15\" }";


        // Create the HTTP client
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            // Create the PUT request with the person data
            HttpPut request = new HttpPut("http://localhost:8081/person/1");
            request.setEntity(new StringEntity(personJson, ContentType.APPLICATION_JSON));

            // Execute the request and capture the response
            try (CloseableHttpResponse response = client.execute(request)) {
                // Verify the response status code
                int statusCode = response.getStatusLine().getStatusCode();

                assertEquals(HttpStatus.SC_OK, statusCode);
            }
        }
    }




    @Test
    @Order(3)
    public void deleteMethodHttp() throws IOException{
        final HttpDelete request = new HttpDelete("http://localhost:8081/person/1");
        try(CloseableHttpClient client = HttpClientBuilder.create().build()){
            HttpResponse response = client.execute(request);
            int statuscode = response.getStatusLine().getStatusCode();

            personRepository.deleteByFirstName("test");
            List<Person>persons = personRepository.findByFirstname("test");
            personRepository.insertFirstPerson();
            assertThat(statuscode,equalTo(HttpStatus.SC_OK));

        }
    }







    }

