package com.evive;

import com.evive.history.model.UserBrowser;
import com.evive.history.model.UserDetails;
import com.evive.history.model.chatBot.ChatbotModel;
import com.evive.time.Chrono;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class HistoryTest extends AbstractApiTest {

    @Override
    protected void setBaseUri() {
        RestAssured.baseURI = configProperties.getHistoryUrl();
    }

    @BeforeClass
    public void setup() {
        setBaseUri();
    }

    @Test(description = "Dummy Test")
    public void test1() {
        UserDetails userDetails = UserDetails.builder()
                .upin("TEST_1030_CMP_USER1")
                .clientCode("1030")
                .sessionType("sessionType")
                .sessionId("sessionId")
                .build();

        UserBrowser userBrowser = UserBrowser.builder()
                .browserInfo("browserInfo")
                .ipAddress("ipAddress")
                .deviceType("NORMAL")
                .build();
        ChatbotModel chatbotModel = ChatbotModel.builder()
                .userDetails(userDetails)
                .timestamp(Chrono.Instant.now())
                .userBrowser(userBrowser)
                .keyWord("health")
                .productResult(ImmutableList.of("healthCard"))
                .cardResult(ImmutableList.of("healthCard"))
                .event("successful_searches")
                .build();
        Response response = apiService.getResponseForPostApiRequestParamAndRequestBody("/history/insert", ImmutableMap.of("insertDb", true), chatbotModel, "");
        Assert.assertEquals(200,response.statusCode());
    }
}
