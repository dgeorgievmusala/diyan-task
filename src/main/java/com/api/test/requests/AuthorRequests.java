package com.api.test.requests;

/**
 * The {@code AuthorRequests} class extends {@code BaseRequest} and provides specific methods to
 * interact with the "authors" API. This class contains methods to retrieve, create, update, and
 * delete authors via HTTP requests.
 *
 * <p>The base URL for the authors API is loaded from a configuration file, and each method
 * corresponds to a common CRUD operation, sending requests to the appropriate endpoint with the
 * relevant HTTP method and payload.
 *
 * <pre>
 * Example usage:
 * {@code
 *   AuthorRequests authorRequests = new AuthorRequests();
 *   Response response = authorRequests.getAllAuthors();
 * }
 * </pre>
 *
 * <p>The following operations are supported:
 *
 * <ul>
 *   <li>Retrieve all authors - {@code getAllAuthors()}
 *   <li>Retrieve a specific author by ID - {@code getAuthorById(Object authorId)}
 *   <li>Create a new author - {@code createAuthor(String jsonPayload)}
 *   <li>Update an existing author - {@code updateAuthor(Object authorId, String jsonPayload)}
 *   <li>Delete an author - {@code deleteAuthor(Object authorId)}
 * </ul>
 *
 * <p>This class uses the {@code BaseRequest}'s {@code sendRequest()} method to execute the HTTP
 * requests, relying on REST-assured to handle the underlying request construction and execution.
 *
 * @see com.api.test.requests.BaseRequest
 */
import com.api.test.configs.ConfigurationLoader;
import io.restassured.response.Response;

public class AuthorRequests extends BaseRequest {
  private final String AUTHORS_URL = ConfigurationLoader.getProperty("authorsUrl");

  public Response getAllAuthors() {
    return sendRequest(AUTHORS_URL, GET, null, null);
  }

  public Response getAuthorById(Object authorId) {
    return sendRequest(AUTHORS_URL, GET, authorId, null);
  }

  public Response createAuthor(String jsonPayload) {
    return sendRequest(AUTHORS_URL, POST, null, jsonPayload);
  }

  public Response updateAuthor(Object authorId, String jsonPayload) {
    return sendRequest(AUTHORS_URL, PUT, authorId, jsonPayload);
  }

  public Response deleteAuthor(Object authorId) {
    return sendRequest(AUTHORS_URL, DELETE, authorId, null);
  }
}
