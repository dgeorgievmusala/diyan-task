package com.api.test.repositories;

import com.api.test.models.Author;
import com.github.javafaker.Faker;
import java.util.List;

/**
 * The {@code AuthorRepository} class provides access to author data for testing purposes. This
 * class is responsible for retrieving mock or predefined author information that can be used in
 * test cases.
 */
public class AuthorRepository {

  private static final String DATA_URL = "data/authors/";
  private final Faker faker = new Faker();

  private final LoadDataFromJson loadData = new LoadDataFromJson();
  private List<Author> authors;

  public void loadAuthors(String resourceName) {
    authors = loadData.loadObjects(String.format("%s/%s", DATA_URL, resourceName), Author.class);
  }

  public List<Author> getAllAuthors() {
    return authors;
  }

  public Author getFakeNewAuthor() {

    return Author.builder()
        .firstName(faker.name().firstName())
        .lastName(faker.name().lastName())
        .idBook(faker.number().numberBetween(0, 1000))
        .build();
  }
}
