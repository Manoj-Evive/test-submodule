package com.evive.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testng.util.Strings;

@Service
public class AbstractService {

  private String testResetApiAccessToken;
  @Autowired
  protected ApiService apiService;

  public final String getTestResetApiAccessToken() {
    if (Strings.isNullOrEmpty(testResetApiAccessToken)) {
      testResetApiAccessToken = apiService.getAccessToken();
    }
    return testResetApiAccessToken;
  }
}
