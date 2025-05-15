package com.api.test.repositories;

import com.api.test.models.Book;
import com.github.javafaker.Faker;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * The {@code BookRepository} class provides access to author data for testing purposes. This class
 * is responsible for retrieving mock or predefined author information that can be used in test
 * cases.
 */
public class BookRepository {
  private static final String DATA_URL = "data/books/";
  private final LoadDataFromJson loadData = new LoadDataFromJson();
  private final Faker faker = new Faker();
  private List<Book> books;

  // Load books from JSON file
  public void loadBooks(String resourceName) {
    books = loadData.loadObjects(String.format("%s/%s", DATA_URL, resourceName), Book.class);
  }

  public List<Book> getAllBooks() {
    return books;
  }

  public Book getFakeNewBook() {

    Instant pastInstant = faker.date().past(365, TimeUnit.DAYS).toInstant();
    String formattedPublishDate =
        DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC).format(pastInstant);

    return Book.builder()
        .title(faker.book().title())
        .description(faker.lorem().paragraph())
        .pageCount(faker.number().numberBetween(0, 1000))
        .excerpt(faker.lorem().paragraph())
        .publishDate(formattedPublishDate)
        .build();
  }
}
