package com.api.test.verifications;

import static com.api.test.constants.ApiTestsConstants.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import io.restassured.response.Response;
import org.testng.Assert;

/**
 * The {@code ErrorMessagesVerification} class provides utility methods for verifying specific error
 * messages in API responses. These methods check the structure and content of the error messages to
 * ensure they are returned correctly by the API in various error scenarios.
 *
 * <p>This class performs assertions on common error fields like the error message title, trace ID,
 * and specific error keys that correspond to different error types.
 *
 * <pre>
 * Example usage:
 * {@code
 *   Response response = someApiRequest();
 *   ErrorMessagesVerification verifier = new ErrorMessagesVerification();
 *   verifier.verifyErrorInvalidPageCount(response);
 * }
 * </pre>
 */
public class ErrorMessagesVerification {

  /**
   * Verifies that the error message in the response indicates an invalid page count.
   *
   * <p>This method asserts that the error message title is correct, the trace ID exists, and the
   * error map contains the key for page count errors. It also checks that the error message for the
   * page count is as expected.
   *
   * @param response the {@code Response} object containing the API response to verify
   * @throws AssertionError if any of the error message verifications fail
   */
  public void verifyErrorInvalidPageCount(Response response) {
    assertEquals(response.jsonPath().getString(ERROR_MESSAGE_TITLE_KEY), ERROR_MESSAGE_TITLE);
    assertNotNull(response.jsonPath().get(ERROR_MESSAGE_TRACEID_KEY));
    Assert.assertTrue(
        response
            .jsonPath()
            .getMap(ERROR_MESSAGE_ERRORS_KEY)
            .containsKey(ERROR_MESSAGE_PAGE_COUNT_KEY),
        "Error key is missing");
    String errorMessage =
        response
            .jsonPath()
            .getString(
                String.format(
                    "%s.'%s'[0]", ERROR_MESSAGE_ERRORS_KEY, ERROR_MESSAGE_PAGE_COUNT_KEY));
    Assert.assertTrue(
        errorMessage.contains(ERROR_MESSAGE_COULD_NOT_CONVERT_SYSTEM32),
        "Expected error message is missing or incorrect");
  }

  /**
   * Verifies that the error message in the response indicates an invalid date format.
   *
   * <p>This method asserts that the error message title is correct, the trace ID exists, and the
   * error map contains the key for date format errors. It also checks that the error message for
   * the date format is as expected.
   *
   * @param response the {@code Response} object containing the API response to verify
   * @throws AssertionError if any of the error message verifications fail
   */
  public void verifyErrorInvalidDateFormat(Response response) {
    assertEquals(response.jsonPath().getString(ERROR_MESSAGE_TITLE_KEY), ERROR_MESSAGE_TITLE);
    assertNotNull(response.jsonPath().get(ERROR_MESSAGE_TRACEID_KEY));
    Assert.assertTrue(
        response
            .jsonPath()
            .getMap(ERROR_MESSAGE_ERRORS_KEY)
            .containsKey(ERROR_MESSAGE_PUBLISH_DATE_KEY),
        "Error key is missing");
    String errorMessage =
        response
            .jsonPath()
            .getString(
                String.format(
                    "%s.'%s'[0]", ERROR_MESSAGE_ERRORS_KEY, ERROR_MESSAGE_PUBLISH_DATE_KEY));
    Assert.assertTrue(
        errorMessage.contains(ERROR_MESSAGE_COULD_NOT_CONVERT_DATE),
        "Expected error message is missing or incorrect");
  }

  /**
   * Verifies that the error message in the response indicates an invalid book ID type.
   *
   * <p>This method asserts that the error message title is correct, the trace ID exists, and the
   * error map contains the key for invalid book ID errors. It also checks that the error message
   * for the book ID is as expected.
   *
   * @param response the {@code Response} object containing the API response to verify
   * @throws AssertionError if any of the error message verifications fail
   */
  public void verifyErrorInvalidIdBookType(Response response) {
    assertEquals(response.jsonPath().getString(ERROR_MESSAGE_TITLE_KEY), ERROR_MESSAGE_TITLE);
    assertNotNull(response.jsonPath().get(ERROR_MESSAGE_TRACEID_KEY));
    Assert.assertTrue(
        response.jsonPath().getMap(ERROR_MESSAGE_ERRORS_KEY).containsKey(ERROR_MESSAGE_ID_BOOK_KEY),
        "Error key is missing");
    String errorMessage =
        response
            .jsonPath()
            .getString(
                String.format("%s.'%s'[0]", ERROR_MESSAGE_ERRORS_KEY, ERROR_MESSAGE_ID_BOOK_KEY));
    Assert.assertTrue(
        errorMessage.contains(ERROR_MESSAGE_COULD_NOT_CONVERT_SYSTEM32),
        "Expected error message is missing or incorrect");
  }

  /**
   * Verifies that the error message in the response indicates an invalid ID type.
   *
   * <p>This method asserts that the error message title is correct, the trace ID exists, and the
   * error map contains the key for invalid ID errors. It also checks that the error message for the
   * ID contains the expected value.
   *
   * @param response the {@code Response} object containing the API response to verify
   * @param value the invalid value that should appear in the error message
   * @throws AssertionError if any of the error message verifications fail
   */
  public void verifyErrorInvalidIdType(Response response, String value) {
    assertEquals(response.jsonPath().getString(ERROR_MESSAGE_TITLE_KEY), ERROR_MESSAGE_TITLE);
    assertNotNull(response.jsonPath().get(ERROR_MESSAGE_TRACEID_KEY));
    Assert.assertTrue(
        response.jsonPath().getMap(ERROR_MESSAGE_ERRORS_KEY).containsKey(ERROR_MESSAGE_ID_KEY),
        "Error key is missing");
    String errorMessage =
        response
            .jsonPath()
            .getString(String.format("%s.'%s'[0]", ERROR_MESSAGE_ERRORS_KEY, ERROR_MESSAGE_ID_KEY));
    Assert.assertTrue(
        errorMessage.contains(String.format(ERROR_MESSAGE_NOT_VALID_VALUE, value)),
        "Expected error message is missing or incorrect");
  }

  /**
   * Verifies that the error message in the response indicates a resource not found.
   *
   * <p>This method asserts that the error message title is set to "Not Found" and that the trace ID
   * exists in the response.
   *
   * @param response the {@code Response} object containing the API response to verify
   * @throws AssertionError if any of the error message verifications fail
   */
  public void verifyErrorNotFound(Response response) {
    assertEquals(response.jsonPath().getString(ERROR_MESSAGE_TITLE_KEY), ERROR_MESSAGE_NOT_FOUND);
    assertNotNull(response.jsonPath().get(ERROR_MESSAGE_TRACEID_KEY));
  }
}
