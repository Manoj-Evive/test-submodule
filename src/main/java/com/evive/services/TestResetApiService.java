package com.evive.services;

import com.evive.config.ConfigProperties;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.Map;

import static io.restassured.RestAssured.given;

@Service
@Slf4j
public class TestResetApiService {
    @Autowired
    private ConfigProperties configProperties;
    @Autowired
    private JsonUtil jsonUtil;
    @Autowired
    private AbstractService abstractService;

    public Response getResponseForGetApiRequestParam(String url,
                                                     Map<String, Object> inputParameters) {
        return given()
                .relaxedHTTPSValidation()
                .accept(MediaType.APPLICATION_JSON.toString())
                .auth()
                .oauth2(abstractService.getTestResetApiAccessToken())
                .queryParams(inputParameters)
                .when()
                .get(configProperties.getResetApiUrl() + url)
                .then()
                .statusCode(HttpStatus.OK_200)
                .extract()
                .response();
    }


    public Response getResponseForPostApiRequestBody(String url,
                                                     Object inputParameters) {
        return given()
                .relaxedHTTPSValidation()
                .auth()
                .oauth2(abstractService.getTestResetApiAccessToken())
                .body(jsonUtil.mapObjectToJson(inputParameters))
                .contentType(ContentType.JSON)
                .when()
                .post(configProperties.getResetApiUrl() + url)
                .then()
                .statusCode(HttpStatus.OK_200)
                .extract()
                .response();
    }

    public Response getResponseForPostApiRequestBodyAndRequestParam(String url,
                                                                    Object inputParameters,
                                                                    Map<String, Object> inputParametersForRequestParam) {
        return given()
                .relaxedHTTPSValidation()
                .auth()
                .oauth2(abstractService.getTestResetApiAccessToken())
                .queryParams(inputParametersForRequestParam)
                .body(jsonUtil.mapObjectToJson(inputParameters))
                .contentType(ContentType.JSON)
                .when()
                .post(configProperties.getResetApiUrl() + url)
                .then()
                .statusCode(HttpStatus.OK_200)
                .extract()
                .response();
    }

}
