package com.api.test.requests;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * The {@code BaseRequest} class provides a generic mechanism to send HTTP requests using common
 * HTTP methods such as GET, POST, PUT, and DELETE. It is designed to handle request creation and
 * dispatch, including setting the appropriate HTTP method, endpoint, and request payload.
 *
 * <p>This class supports sending JSON data as the payload for POST and PUT requests, and handles
 * path parameters in the URL for GET, PUT, and DELETE requests.
 *
 * <pre>
 * Example usage:
 * {@code
 *   BaseRequest request = new BaseRequest();
 *   Response response = request.sendRequest("/authors", "GET", null, null);
 * }
 * </pre>
 *
 * <p>This class is built on top of RestAssured, using {@code RequestSpecification} to configure and
 * send the HTTP requests. It provides flexibility by allowing different HTTP methods to be
 * specified dynamically at runtime.
 *
 * <p>The following HTTP methods are supported:
 *
 * <ul>
 *   <li>GET
 *   <li>POST
 *   <li>PUT
 *   <li>DELETE
 * </ul>
 */
public class BaseRequest {

  protected static final String GET = "GET";
  protected static final String POST = "POST";
  protected static final String PUT = "PUT";
  protected static final String DELETE = "DELETE";

  public Response sendRequest(String endpoint, String method, Object id, String jsonPayload) {

    RequestSpecification request = given().contentType(ContentType.JSON);

    if (jsonPayload != null && !method.equalsIgnoreCase(GET)) {
      request.body(jsonPayload);
    }

    switch (method.toUpperCase()) {
      case GET:
        if (id != null) {
          return given().when().get(String.format("%s/%s", endpoint, id));
        } else {
          return given().when().get(endpoint);
        }
      case POST:
        return request.when().post(endpoint);
      case PUT:
        return request.when().put(String.format("%s/%s", endpoint, id));
      case DELETE:
        return given()
            .contentType(ContentType.JSON)
            .when()
            .delete(String.format("%s/%s", endpoint, id));
      default:
        throw new IllegalArgumentException("Invalid HTTP method: " + method);
    }
  }
}
