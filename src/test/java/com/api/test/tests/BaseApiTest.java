package com.api.test.tests;

import com.api.test.configs.ConfigurationLoader;
import com.api.test.verifications.ErrorMessagesVerification;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseApiTest {
  protected Gson gson;

  protected ErrorMessagesVerification verifyError;

  //@Parameters("env")
  @BeforeClass
  public void setup() {

    RestAssured.baseURI = ConfigurationLoader.getProperty("baseUrl");
    gson = new GsonBuilder().setPrettyPrinting().create();
    verifyError = new ErrorMessagesVerification();

  }
}
