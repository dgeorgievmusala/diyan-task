package com.api.test.tests;

import static com.api.test.constants.ApiTestsConstants.INVALID_ID_DATA_TYPE;
import static com.api.test.constants.ApiTestsConstants.NON_EXISTENT_ID;
import static org.testng.Assert.assertEquals;

import com.api.test.models.Author;
import com.api.test.repositories.AuthorRepository;
import com.api.test.requests.AuthorRequests;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.util.List;

public class AuthorTests extends BaseApiTest {

    private AuthorRepository authorRepository;
    private Author expectedAuthor;
    private AuthorRequests authorRequests;

    @BeforeClass
    public void beforeClass() {

        authorRepository = new AuthorRepository();

        // ✅ Load authors from a file or mock if needed
        authorRepository.loadAuthors("authors.json");  // <-- UNCOMMENT THIS LINE

        authorRequests = new AuthorRequests();

        List<Author> authors = authorRepository.getAllAuthors();
        if (authors == null || authors.isEmpty()) {
            throw new RuntimeException("Author list is empty or not loaded. Check authors.json file.");
        }

        expectedAuthor = authors.get(0);
    }

    @Test(description = "Get all authors")
    public void testGetAllAuthors() {
        Response response =
                authorRequests.getAllAuthors().then().statusCode(HttpStatus.SC_OK).extract().response();

        Assert.assertFalse(
                response.jsonPath().getList("id").isEmpty(), "Authors list should not be empty");
    }

    @Test(description = "Get an author by ID")
    public void testGetAuthorById() {
        Author responseAuthor =
                authorRequests
                        .getAuthorById(expectedAuthor.getId())
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .extract()
                        .response()
                        .as(Author.class);
        Assert.assertNotNull(responseAuthor, "Author not found in repository");
        assertEquals(
                responseAuthor.getId(),
                expectedAuthor.getId(),
                String.format(
                        "The expected author id is [%s], but we got [%s]",
                        expectedAuthor.getId(), responseAuthor.getId()));
        assertEquals(
                responseAuthor.getFirstName(),
                expectedAuthor.getFirstName(),
                String.format(
                        "The expected author FirstName is [%s], but we got [%s]",
                        expectedAuthor.getFirstName(), responseAuthor.getFirstName()));
        assertEquals(
                responseAuthor.getLastName(),
                expectedAuthor.getLastName(),
                String.format(
                        "The expected author LastName is [%s], but we got [%s]",
                        expectedAuthor.getLastName(), responseAuthor.getLastName()));
        assertEquals(
                responseAuthor.getIdBook(),
                expectedAuthor.getIdBook(),
                String.format(
                        "The expected author book reference is [%s], but we got [%s]",
                        expectedAuthor.getIdBook(), responseAuthor.getIdBook()));
    }

    @Test(description = "Get an author by NON-Existent ID")
    public void testGetAuthorByIdNotFound() {

        Response response =
                authorRequests
                        .getAuthorById(NON_EXISTENT_ID)
                        .then()
                        .statusCode(HttpStatus.SC_NOT_FOUND)
                        .extract()
                        .response();
        verifyError.verifyErrorNotFound(response);
    }

    @Test(description = "Get an author by Invalid DataType Id")
    public void testGetAuthorByInvalidIdDataType() {

        Response response =
                authorRequests
                        .getAuthorById(INVALID_ID_DATA_TYPE)
                        .then()
                        .statusCode(HttpStatus.SC_BAD_REQUEST)
                        .extract()
                        .response();

        verifyError.verifyErrorInvalidIdType(response, INVALID_ID_DATA_TYPE);
    }

    @Test(description = "Create an Author with valid data")
    @Description("Expected to fail due to bug XYZ")
    public void testCreateAuthor() {

        // Generate a valid Author payload
        Author newAuthor = authorRepository.getFakeNewAuthor();
        String payload = gson.toJson(newAuthor);

        // 🔒 Step 1: Create author and validate creation response
        Response createResponse = authorRequests.createAuthor(payload);
        int createStatusCode = createResponse.getStatusCode();

        System.out.println("Create Author Response: " + createResponse.asString());
        assertEquals(createStatusCode, HttpStatus.SC_OK, "Author creation failed");

        // 🔒 Step 2: Extract ID safely
        String id = createResponse.jsonPath().getString("id");
        Assert.assertNotNull(id, "ID should be present in creation response");

    }
    @Ignore
    @Description("Expected to fail due to bug XYZ")
    @Test( enabled = false,
            description = "Create an Author with invalid data type")

    public void testCreateAuthorWithInvalidDataType() {
        Author newAuthor = authorRepository.getFakeNewAuthor();
        newAuthor.setIdBook(Integer.valueOf(INVALID_ID_DATA_TYPE));
        String payLoad = gson.toJson(newAuthor);
        Response response = authorRequests.createAuthor(payLoad).then().extract().response();
        assertEquals(
                response.statusCode(),
                HttpStatus.SC_NOT_FOUND,
                String.format("Expected %s status but got %s", HttpStatus.SC_NOT_FOUND, response.statusCode())
        );

    }

    @Test(description = "Update Author with valid data")
    public void updateAuthor() {
        Author author = authorRepository.getFakeNewAuthor();
        author.setId(expectedAuthor.getId());
        String payload = gson.toJson(author);

        Author updatedAuthor =
                authorRequests
                        .updateAuthor(author.getId(), payload)
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .extract()
                        .response()
                        .as(Author.class);

        Assert.assertNotNull(updatedAuthor, "Author not found in repository");
        assertEquals(
                updatedAuthor.getId(),
                author.getId(),
                String.format(
                        "The expected author id is [%s], but we got [%s]",
                        author.getId(), updatedAuthor.getId()));
        assertEquals(
                updatedAuthor.getFirstName(),
                author.getFirstName(),
                String.format(
                        "The expected author FirstName is [%s], but we got [%s]",
                        author.getFirstName(), updatedAuthor.getFirstName()));
        assertEquals(
                updatedAuthor.getLastName(),
                author.getLastName(),
                String.format(
                        "The expected author LastName is [%s], but we got [%s]",
                        author.getLastName(), updatedAuthor.getLastName()));
        assertEquals(
                updatedAuthor.getIdBook(),
                author.getIdBook(),
                String.format(
                        "The expected author book reference is [%s], but we got [%s]",
                        author.getIdBook(), updatedAuthor.getIdBook()));
    }
    @Ignore
    @Test(description = "Update Author with mismatch id")
    public void updateAuthorMistMatchID() {
        Integer misMatchId = ((Integer) expectedAuthor.getId()) + 1;
        Author author = authorRepository.getFakeNewAuthor();
        author.setId(expectedAuthor.getId());
        String payload = gson.toJson(author);
        authorRequests.updateAuthor(misMatchId, payload).then().statusCode(HttpStatus.SC_BAD_REQUEST);
    }
    @Ignore
    @Test(description = "Update Author with NON-existent id")
    public void updateAuthorNonExistentID() {
        Author author = authorRepository.getFakeNewAuthor();
        author.setId(expectedAuthor.getId());
        String payload = gson.toJson(author);
        Response response =
                authorRequests
                        .updateAuthor(NON_EXISTENT_ID, payload)
                        .then()
                        .statusCode(HttpStatus.SC_NOT_FOUND)
                        .extract()
                        .response();
        verifyError.verifyErrorNotFound(response);
    }

    @Test(description = "Update Author with invalid data type ")

    public void updateAuthorInvalidDataType() {
        boolean knownBug = true;
        if (knownBug) {
            throw new SkipException("Skipping due to known bug XYZ-123");
        }
        Author author = authorRepository.getFakeNewAuthor();
        author.setId(expectedAuthor.getId());
        author.setIdBook(Integer.valueOf(INVALID_ID_DATA_TYPE));
        String payload = gson.toJson(author);
        Response response =
                authorRequests
                        .updateAuthor(author.getId(), payload)
                        .then()
                        .statusCode(HttpStatus.SC_BAD_REQUEST)
                        .extract()
                        .response();
        verifyError.verifyErrorInvalidIdBookType(response);
    }

    @Test(description = "Delete author with valid Id")
    public void testDeleteAuthorWithValidId() {
        authorRequests.deleteAuthor(expectedAuthor.getId()).then().statusCode(HttpStatus.SC_OK);
    }
    @Ignore
    @Test(description = "Delete author with NON-existent ID")

    public void testDeleteAuthorWithNonExistentID() {

        Response response =
                authorRequests
                        .deleteAuthor(NON_EXISTENT_ID)
                        .then()
                        .statusCode(HttpStatus.SC_NOT_FOUND)
                        .extract()
                        .response();
        verifyError.verifyErrorNotFound(response);
    }

    @Test(description = "Delete author with invalidIDFormat")
    public void testDeleteAuthorWithInvalidIdFormat() {
        Response response =
                authorRequests
                        .deleteAuthor(INVALID_ID_DATA_TYPE)
                        .then()
                        .statusCode(HttpStatus.SC_BAD_REQUEST)
                        .extract()
                        .response();

        verifyError.verifyErrorInvalidIdType(response, INVALID_ID_DATA_TYPE);
    }
}
