package com.api.test.constants;

public final class ApiTestsConstants {
  // Define constant values
  public static final int NON_EXISTENT_ID = 99999999;
  public static final String INVALID_ID_DATA_TYPE = "abc";

  public static final String ERROR_MESSAGE_TITLE_KEY = "title";
  public static final String ERROR_MESSAGE_TITLE = "One or more validation errors occurred.";
  public static final String ERROR_MESSAGE_TRACEID_KEY = "traceId";
  public static final String ERROR_MESSAGE_ERRORS_KEY = "errors";
  public static final String ERROR_MESSAGE_ID_KEY = "id";
  public static final String ERROR_MESSAGE_ID_BOOK_KEY = "$.idBook";
  public static final String ERROR_MESSAGE_PUBLISH_DATE_KEY = "$.publishDate";
  public static final String ERROR_MESSAGE_PAGE_COUNT_KEY = "$.pageCount";
  public static final String ERROR_MESSAGE_NOT_FOUND = "Not Found";
  public static final String ERROR_MESSAGE_NOT_VALID_VALUE = "The value '%s' is not valid.";
  public static final String ERROR_MESSAGE_COULD_NOT_CONVERT_SYSTEM32 =
      "The JSON value could not be converted to System.Int32";
  public static final String ERROR_MESSAGE_COULD_NOT_CONVERT_DATE =
      "The JSON value could not be converted to System.DateTime";

  private ApiTestsConstants() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }
}
