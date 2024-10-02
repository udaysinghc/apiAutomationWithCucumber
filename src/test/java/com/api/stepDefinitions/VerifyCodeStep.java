package com.api.stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;

import java.util.Map;

import static com.api.stepDefinitions.LoginSteps.sessionId;
import static utils.ConfigManager.getBaseUrl;

public class VerifyCodeStep {
  private JSONObject requestBody;
  private Response response;
  public static String token;
  public static String id;

  @Given("user provides the following  details")
  public void userProvidesTheFollowingDetails(DataTable dataTable) {
    Map<String, String> credentials = dataTable.asMap(String.class, String.class);
    // Build the requestBody
    requestBody = new JSONObject();
    requestBody.put("sessionId", sessionId);
    requestBody.put("code", credentials.get("code"));
    requestBody.put("type", credentials.get("type"));
  }

  @When("user send a post request to {string}")
  public void userSendAPostRequestTo(String endpoint) {
    RestAssured.baseURI = getBaseUrl();
    response =
        RestAssured.given()
            .header("Content-Type", "application/json")
            .body(requestBody.toString())
            .when()
            .post(endpoint)
            .then()
            .extract()
            .response();
    response.prettyPrint();
    String responseBody = response.asString();
    JsonPath jsonPath = new JsonPath(responseBody);
    if (jsonPath.get("data") != null && jsonPath.get("data.id") != null) {
      id = jsonPath.getString("data.id");
      System.out.println("id : " + id);
    } else {
      System.err.println("id  not found in the response.");
    }
    if (jsonPath.get("data") != null && jsonPath.get("data.token") != null) {
      token = jsonPath.getString("data.token");
      System.out.println("Token : " + token);
    } else {
      System.err.println("Token  not found in the response.");
    }
  }

  @Then("the response status coe should be {int}")
  public void theResponseStatusCoeShouldBe(Integer statusCode) {
    Assert.assertEquals(response.getStatusCode(), statusCode.intValue());
  }

  @And("user valid the contain data")
  public void userValidTheContainData() {
    Assert.assertNotNull(response.jsonPath().get("data"), "Response should contain data");
  }
}
