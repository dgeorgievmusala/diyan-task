package com.api.test.requests;

import com.api.test.configs.ConfigurationLoader;
import io.restassured.response.Response;

/**
 * The {@code BookRequests} class extends {@code BaseRequest} and provides specific methods to
 * interact with the "books" API. This class contains methods to retrieve, create, update, and
 * delete books via HTTP requests.
 *
 * <p>The base URL for the books API is loaded from a configuration file, and each method
 * corresponds to a common CRUD operation, sending requests to the appropriate endpoint with the
 * relevant HTTP method and payload.
 *
 * <pre>
 * Example usage:
 * {@code
 *   BookRequests bookRequests = new BookRequests();
 *   Response response = bookRequests.getAllBooks();
 * }
 * </pre>
 *
 * <p>The following operations are supported:
 *
 * <ul>
 *   <li>Retrieve all books - {@code getAllBooks()}
 *   <li>Retrieve a specific book by ID - {@code getBookById(Object bookId)}
 *   <li>Create a new book - {@code createBook(String jsonPayload)}
 *   <li>Update an existing book - {@code updateBook(Object bookId, String jsonPayload)}
 *   <li>Delete a book - {@code deleteBook(Object bookId)}
 * </ul>
 *
 * <p>This class uses the {@code BaseRequest}'s {@code sendRequest()} method to execute the HTTP
 * requests, relying on REST-assured to handle the underlying request construction and execution.
 *
 * @see BaseRequest
 */
public class BookRequests extends BaseRequest {
  private final String BOOKS_URL = ConfigurationLoader.getProperty("booksUrl");

  public Response getAllBooks() {
    return sendRequest(BOOKS_URL, GET, null, null);
  }

  public Response getBookById(Object bookId) {
    return sendRequest(BOOKS_URL, GET, bookId, null);
  }

  public Response createBook(String jsonPayload) {
    return sendRequest(BOOKS_URL, POST, null, jsonPayload);
  }

  public Response updateBook(Object bookId, String jsonPayload) {
    return sendRequest(BOOKS_URL, PUT, bookId, jsonPayload);
  }

  public Response deleteBook(Object bookId) {
    return sendRequest(BOOKS_URL, DELETE, bookId, null);
  }
}
