package com.api.test.data_providers;

import org.apache.http.HttpStatus;
import org.testng.annotations.DataProvider;

public class DataProviderClass {

  @DataProvider(name = "pageCountProvider")
  public static Object[][] pageCountProvider() {
    return new Object[][] {
      {0, HttpStatus.SC_OK, "Lower boundary (minimum allowed value)"},
      {1, HttpStatus.SC_OK, "Just above the lower boundary"},
      {9999, HttpStatus.SC_OK, "Upper boundary (assuming 9999 is max)"},
      {10000, HttpStatus.SC_BAD_REQUEST, "Just above the upper boundary"},
      {-1, HttpStatus.SC_BAD_REQUEST, "Below the lower boundary (invalid negative)"},
      {1.5, HttpStatus.SC_BAD_REQUEST, "Non-integer value"},
      {2147483647, HttpStatus.SC_OK, "Maximum 32-bit integer value"},
      {9223372036854775807L, HttpStatus.SC_BAD_REQUEST, "Exceeding maximum integer value (64-bit)"},
      {"two hundred", HttpStatus.SC_BAD_REQUEST, "String instead of integer"},
      {null, 400, "Null value"}
    };
  }
}
