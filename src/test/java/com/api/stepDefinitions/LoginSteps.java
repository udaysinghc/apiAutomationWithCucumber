package com.api.stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import utils.ConfigManager;

import java.util.Map;

import static utils.ConfigManager.getBaseUrl;

public class LoginSteps {
  private JSONObject requestBody;
  private Response response;
  public static String sessionId;

  @Given("the user provides the following credentials:")
  public void the_user_provides_the_following_credentials(DataTable dataTable) {
    Map<String, String> credentials = dataTable.asMap(String.class, String.class);

    // Build the requestBody
    requestBody = new JSONObject();
    requestBody.put("loginType", credentials.get("loginType"));
    requestBody.put("phoneCode", credentials.get("phoneCode"));
    requestBody.put("phoneNumber", credentials.get("phoneNumber"));
    requestBody.put("password", credentials.get("password"));

    JSONObject deviceInfo = new JSONObject();
    deviceInfo.put("deviceId", credentials.get("deviceId"));
    deviceInfo.put("language", credentials.get("language"));
    deviceInfo.put("fcmToken", credentials.get("fcmToken"));
    deviceInfo.put("appVersion", credentials.get("appVersion"));
    deviceInfo.put("osVersion", credentials.get("osVersion"));
    deviceInfo.put("platform", credentials.get("platform"));

    requestBody.put("deviceInfo", deviceInfo);
  }

  @When("user send a POST request to {string}")
  public void userSendAPOSTRequestTo(String endpoint) {
    RestAssured.baseURI = getBaseUrl();
    response =
        RestAssured.given()
            .header("Content-Type", "application/json") // Set header to accept JSON
            .body(requestBody.toString()) // Attach the JSON body
            .when()
            .post(endpoint) // POST method
            .then()
            //                            .log().all()
            .extract()
            .response();
    response.prettyPrint();
    String responseBody = response.asString();
    JsonPath jsonPath = new JsonPath(responseBody);
    if (jsonPath.get("data") != null && jsonPath.get("data.sessionId") != null) {
      sessionId = jsonPath.getString("data.sessionId");
      System.out.println("Session ID: " + sessionId);
    } else {
      System.err.println("Session ID not found in the response.");
    }


  }

  @Then("the response status code should be {int}")
  public void the_response_status_code_should_be(Integer statusCode) {
    Assert.assertEquals(response.getStatusCode(), statusCode.intValue());

  }

  @Then("the response should contain data")
  public void the_response_should_contain_data() {
    Assert.assertNotNull(response.jsonPath().get("data"), "Response should contain data");
  }


}
