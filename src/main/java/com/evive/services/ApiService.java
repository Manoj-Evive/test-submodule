package com.evive.services;

import com.evive.config.ConfigProperties;
import com.evive.enums.InputType;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;
import org.testng.util.Strings;

import java.util.*;

import static com.evive.enums.InputType.*;
import static io.restassured.RestAssured.given;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@Service
@Slf4j
public class ApiService {
  @Autowired
  private ConfigProperties configProperties;
  @Autowired
  private JsonUtil jsonUtil;

  /**
   * To get the access token for accessing the test-reset-api
   *
   * @return access token
   */
  public String getAccessToken() {
    return given()
        .relaxedHTTPSValidation()
        .accept(MediaType.APPLICATION_JSON.toString())
        .log().ifValidationFails()
        .auth()
        .preemptive()
        .basic("clientId", "clientSecret")
        .formParam("grant_type", "client_credentials")
        .when()
        .post(configProperties.getResetApiUrl() + "/oauth/token")
        .then()
        .log()
        .ifError()
        .statusCode(200)
        .extract()
        .body()
        .path("access_token");
  }

  /**
   * to get the API response
   *
   * @param url             to be hit
   * @param inputParameters required Parameters
   * @param expectedStatus  expected status
   * @param requestMethod   GET or POST
   * @param inputType       type of input - request Parameter, request body, path parameter
   * @return response
   */
  public String getApiResponse(String url,
                               Object inputParameters,
                               int expectedStatus,
                               RequestMethod requestMethod,
                               InputType inputType,
                               String session) {
    return getApiStatus(url, inputParameters, expectedStatus, requestMethod, inputType, session)
        .extract()
        .response()
        .body()
        .prettyPrint();
  }

  public String getApiResponseFromTestResetApi(String url,
                                               Object inputParameters,
                                               int expectedStatus,
                                               RequestMethod requestMethod,
                                               InputType inputType,
                                               String session) {
    return getApiStatus(url, inputParameters, expectedStatus, requestMethod, inputType, session)
        .extract()
        .response()
        .body()
        .prettyPrint();
  }

  /**
   * to get the API response
   *
   * @param url             to be hit
   * @param inputParameters required Path Parameters
   * @return output response status code
   */
  public int getOutputStatusCodeGetApiWithPathParam(String url,
                                                    Object inputParameters,
                                                    String session) {
    return getApiResponse(url, inputParameters, GET, PATH_PARAM, session)
        .extract()
        .statusCode();
  }

  /**
   * to get the API response
   *
   * @param url             to be hit
   * @param inputParameters required Request Parameters
   * @return output response status code
   */
  public int getOutputStatusCodeGetApiWithRequestParam(String url,
                                                       Object inputParameters,
                                                       String session) {
    return getApiResponse(url, inputParameters, GET, REQUEST_PARAM, session)
        .extract()
        .statusCode();
  }

  /**
   * to get the API response
   *
   * @param url             to be hit
   * @param inputParameters required Path Parameters
   * @return output response status code
   */
  public int getOutputStatusCodePostApiWithPathParam(String url,
                                                     Object inputParameters,
                                                     String session) {
    return getApiResponse(url, inputParameters, POST, PATH_PARAM, session)
        .extract()
        .statusCode();
  }

  /**
   * to get the API response
   *
   * @param url             to be hit
   * @param inputParameters required Request Parameters
   * @return output response status code
   */
  public int getOutputStatusCodePostApiWithRequestParam(String url,
                                                        Object inputParameters,
                                                        String session) {
    return getApiResponse(url, inputParameters, POST, REQUEST_PARAM, session)
        .extract()
        .statusCode();
  }

  /**
   * to get the API response
   *
   * @param url             to be hit
   * @param inputParameters required Request Body
   * @return output response status code
   */
  public int getOutputStatusCodePostApiWithRequestBody(String url,
                                                       Object inputParameters,
                                                       String session) {
    return getApiResponse(url, inputParameters, POST, REQUEST_BODY, session)
        .extract()
        .statusCode();
  }

  /**
   * to get the API response
   *
   * @param url             to be hit
   * @param inputParameters required Request Body
   * @param inputType       type of input - request Parameter, request body, path parameter
   * @param requestMethod   GET or POST
   * @return api response
   */
  private Response hitController(String url,
                                 Object inputParameters,
                                 InputType inputType,
                                 RequestMethod requestMethod,
                                 String session) {
    RequestSpecification requestSpecification = setParam(inputParameters, inputType);
    if (!Strings.isNullOrEmpty(session)) {
      requestSpecification = requestSpecification
          .header("Cookie", session);
    }
    String baseURI = RestAssured.baseURI;
    if (!Strings.isNullOrEmpty(baseURI)
        && baseURI.equalsIgnoreCase(configProperties.getResetApiUrl())) {
      requestSpecification = requestSpecification
          .auth()
          .oauth2(session);
    }
    switch (requestMethod) {
      case POST:
        return requestSpecification
            .contentType(ContentType.JSON)
                  .post(url);
            case PUT:
                return requestSpecification
                        .contentType(ContentType.JSON)
                        .put(url);
            case DELETE:
                return requestSpecification
                  .delete(url);
            case GET:
            default:
                return requestSpecification
                  .accept(MediaType.APPLICATION_JSON.toString())
                  .get(url);
        }
    }

  public ValidatableResponse getApiStatus(String url,
                                          Object inputParameters,
                                          int expectedStatus,
                                          RequestMethod requestMethod,
                                          InputType inputType,
                                          String session) {
    return getApiResponse(url, inputParameters, requestMethod, inputType, session)
        .statusCode(expectedStatus);
  }

  private ValidatableResponse getApiResponse(String url,
                                             Object inputParameters,
                                             RequestMethod requestMethod,
                                             InputType inputType,
                                             String session) {
    return hitController(url, inputParameters, inputType, requestMethod, session)
        .then()
        .log().all();
  }

  private RequestSpecification setParam(Object inputParameters, InputType inputType) {
    return setParam(given().redirects().follow(false).relaxedHTTPSValidation(),
                    inputParameters,
                    inputType);
  }

  private RequestSpecification setParam(RequestSpecification requestSpecification,
                                        Object inputParameters,
                                        InputType inputType) {
    switch (inputType) {
      case PATH_PARAM:
        if (!(inputParameters instanceof Map)) {
          log.error("For Path Param, input parameters should be map.");
          return requestSpecification;
        } else {
          return requestSpecification
              .pathParams((Map) inputParameters);
        }
      case REQUEST_BODY:
        return requestSpecification
            .body(jsonUtil.mapObjectToJson(inputParameters));
      case REQUEST_PARAM:
      default:
        if (!(inputParameters instanceof Map)) {
          log.error("For Request Param, input parameters should be map.");
          return requestSpecification;
        }
        return requestSpecification

            .queryParams((Map) inputParameters);
    }
  }

  public Object getActualOutputObject(String output, Object expectedOutput) {
    if (expectedOutput instanceof Map) {
      return jsonUtil.getJsonToKeyValueMap(output);
    } else if (expectedOutput instanceof Set) {
      return jsonUtil.getJsonToSet(output);
    } else if (expectedOutput instanceof List) {
      return jsonUtil.getJsonToList(output);
    } else if (expectedOutput instanceof String) {
      return output;
    }
    return output;
  }

  public ValidatableResponse getValidatableResponseStatusForGetApiRequestParam(String url,
                                                                               Object inputParameters,
                                                                               int expectedStatus,
                                                                               String session) {
    return getApiResponse(url, inputParameters, GET, REQUEST_PARAM, session)
        .statusCode(expectedStatus);
  }

  public ValidatableResponse getValidatableResponseStatusForGetApiPathParam(String url,
                                                                            Object inputParameters,
                                                                            int expectedStatus,
                                                                            String session) {
    return getApiResponse(url, inputParameters, GET, PATH_PARAM, session)
        .statusCode(expectedStatus);
  }

  public ValidatableResponse getValidatableResponseStatusForPostApiRequestParam(String url,
                                                                                Object inputParameters,
                                                                                int expectedStatus,
                                                                                String session) {
    return getApiResponse(url, inputParameters, POST, REQUEST_PARAM, session)
        .statusCode(expectedStatus);
  }

  public ValidatableResponse getValidatableResponseStatusForPostApiPathParam(String url,
                                                                             Object inputParameters,
                                                                             int expectedStatus,
                                                                             String session) {
    return getApiResponse(url, inputParameters, POST, PATH_PARAM, session)
        .statusCode(expectedStatus);
  }

  public ValidatableResponse getValidatableResponseStatusForPutApiRequestBody(String url,
                                                                              Object inputParameters,
                                                                              int expectedStatus,
                                                                              String session) {
    return getApiResponse(url, inputParameters, PUT, REQUEST_BODY, session)
        .statusCode(expectedStatus);
  }

  public ValidatableResponse getValidatableResponseStatusForPostApiRequestBody(String url,
                                                                               Object inputParameters,
                                                                               int expectedStatus,
                                                                               String session) {
    return getApiResponse(url, inputParameters, POST, REQUEST_BODY, session)
        .statusCode(expectedStatus);
  }

  public Response getResponseForPostApiRequestBody(String url,
                                                   Object inputParameters,
                                                   String session) {
    return getApiResponse(url, inputParameters, POST, REQUEST_BODY, session)
        .extract()
        .response();
  }

  public Response getResponseForPostApiRequestParam(String url,
                                                    Object inputParameters,
                                                    String session) {
    return getApiResponse(url, inputParameters, POST, REQUEST_PARAM, session)
        .extract()
        .response();
  }

  public Response getResponseForPostApiPathParam(String url,
                                                 Object inputParameters,
                                                 String session) {
    return getApiResponse(url, inputParameters, POST, PATH_PARAM, session)
        .extract()
        .response();
  }

  public Response getResponseForGetApiRequestParam(String url,
                                                   Object inputParameters,
                                                   String session) {
    return getApiResponse(url, inputParameters, GET, REQUEST_PARAM, session)
        .extract()
        .response();
  }

  public Response getResponseForGetApiPathParam(String url,
                                                Object inputParameters,
                                                String session) {
    return getApiResponse(url, inputParameters, GET, PATH_PARAM, session)
        .extract()
        .response();
  }

  public Map<String, String> requestParamReturnsValue(String url,
                                                      Object inputParameters,
                                                      String session) {
    String output = getApiStatus(url,
                                 inputParameters,
                                 HttpStatus.OK_200,
                                 GET,
                                 REQUEST_PARAM,
                                 session)
        .extract()
        .response()
        .body()
        .prettyPrint();
    return jsonUtil.getJsonToKeyValueMap(output);
  }

  public Response getResponseForGetApiRequestParamAndRequestBody(String url,
                                                                 Map<String, Object> inputParametersForRequestParam,
                                                                 Object inputParametersForRequestBody,
                                                                 String session) {
    RequestSpecification requestSpecification = setParam(inputParametersForRequestParam,
                                                         REQUEST_PARAM);
    requestSpecification = setParam(requestSpecification,
                                    inputParametersForRequestBody,
                                    REQUEST_BODY);
    if (!Strings.isNullOrEmpty(session)) {
      requestSpecification = requestSpecification
          .header("Cookie", session);
    }
    return requestSpecification
        .post(url);
  }

  public <T> T getTheResponseFromGivenPath(Response response,
                                           String path) {

    return response
        .body()
        .path(path);
  }

    public Response getResponseForPostApiRequestParamAndRequestBody(String url,
                                                                    Map<String, Object> inputParametersForRequestParam,
                                                                    Object inputParametersForRequestBody,
                                                                    String session) {
        RequestSpecification requestSpecification = setParam(inputParametersForRequestParam,
                                                             REQUEST_PARAM);
        requestSpecification = setParam(requestSpecification,
                                        inputParametersForRequestBody,
                                        REQUEST_BODY);
        if (!Strings.isNullOrEmpty(session)) {
            requestSpecification = requestSpecification
              .header("Cookie", session);
        }
        return requestSpecification
          .contentType(ContentType.JSON)
          .post(url);
    }

    public Response getResponseForPutApiRequestParam(String url,
                                                     Object inputParameters,
                                                     String session) {
        return getApiResponse(url, inputParameters, PUT, REQUEST_PARAM, session)
          .extract()
          .response();
    }

    public Response getResponseForDeleteApiRequestParam(String url,
                                                        Map<String, Object> inputParameters,
                                                        String session) {
        return getApiResponse(url, inputParameters, DELETE, REQUEST_PARAM, session)
          .extract()
          .response();
    }
}
