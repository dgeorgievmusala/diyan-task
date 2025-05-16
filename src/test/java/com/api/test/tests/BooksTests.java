package com.api.test.tests;

import static com.api.test.constants.ApiTestsConstants.INVALID_ID_DATA_TYPE;
import static com.api.test.constants.ApiTestsConstants.NON_EXISTENT_ID;
import static org.testng.Assert.*;

import com.api.test.data_providers.DataProviderClass;
import com.api.test.models.Book;
import com.api.test.repositories.BookRepository;
import com.api.test.requests.BookRequests;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Date;

import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

public class BooksTests extends BaseApiTest {

    private BookRequests bookRequests;
    private BookRepository bookRepository;
    private Book expectedBook;

    @BeforeClass
    public void beforeClass() {

        bookRepository = new BookRepository();
        bookRepository.loadBooks("books.json");
        expectedBook = bookRepository.getAllBooks().get(0);
        bookRequests = new BookRequests();
    }

    @Test(description = "Get all books")
    public void testGetAllBooks() {
        Response response =
                bookRequests.getAllBooks().then().statusCode(HttpStatus.SC_OK).extract().response();
        assertFalse(response.jsonPath().getList("id").isEmpty(), "Books list should not be empty");

        int totalBooks = response.jsonPath().getList("id").size();
        assertEquals(totalBooks, 200, "Total number of books should be 200");
    }

    @Ignore
    @Test(description = "Get a book by ID and validate fields")
    public void testGetBookById() {
        // Step 1: Create a book to ensure we know the ID
        Book newBook = bookRepository.getFakeNewBook();
        Response createResponse = bookRequests.createBook(gson.toJson(newBook)).then().extract().response();
        int createdId = createResponse.jsonPath().getInt("id");

        // Step 2: Retrieve it using GET by ID
        Response getResponse = bookRequests.getBookById(createdId).then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        Book responseBook = getResponse.as(Book.class);

        // Step 3: Assertions on available fields (excluding ID)
        assertNotNull(responseBook, "Book response should not be null");
        assertEquals(responseBook.getTitle(), newBook.getTitle(), "Book title mismatch");
        assertEquals(responseBook.getPageCount(), newBook.getPageCount(), "Page count mismatch");
        assertEquals(responseBook.getDescription(), newBook.getDescription(), "Description mismatch");
        assertEquals(responseBook.getExcerpt(), newBook.getExcerpt(), "Excerpt mismatch");
        assertEquals(responseBook.getPublishDate(), newBook.getPublishDate(), "Publish date mismatch");
    }


    @Test(description = "Get a book by NON-Existent ID")
    public void testGetBookByIdNotFound() {
        Response response =
                bookRequests
                        .getBookById(NON_EXISTENT_ID)
                        .then()
                        .statusCode(HttpStatus.SC_NOT_FOUND)
                        .extract()
                        .response();
        verifyError.verifyErrorNotFound(response);
    }

    @Test(description = "Get a book by invalid ID data ype")
    public void testGetBookByInvalidIdDataType() {
        Response response =
                bookRequests
                        .getBookById(INVALID_ID_DATA_TYPE)
                        .then()
                        .statusCode(HttpStatus.SC_BAD_REQUEST)
                        .extract()
                        .response();
        verifyError.verifyErrorInvalidIdType(response, INVALID_ID_DATA_TYPE);
    }

    @Epic("Books API")
    @Feature("Book Creation")
    @Test(description = "Create a Book with valid data")
    public void testCreateBookValid() {
        Book newBook = bookRepository.getFakeNewBook();
        String newBookJson = gson.toJson(newBook);
        Response response = bookRequests.createBook(newBookJson).then().extract().response();

        assertEquals(response.statusCode(), HttpStatus.SC_OK); // HTTP 201 Created
        assertNotNull(response.jsonPath().getString("id"), "The bookId should not be empty");

        Book createdBook = response.as(Book.class);
        assertNotNull(createdBook, "Book not found in repository");
        assertNotNull(createdBook.getId(), "The book id should be generate");
        assertEquals(
                createdBook.getTitle(),
                newBook.getTitle(),
                String.format(
                        "The expected title id is [%s], but we got [%s]",
                        newBook.getTitle(), createdBook.getTitle()));
        assertFalse(createdBook.getDescription().isEmpty(), "Book description should not be empty");
        assertEquals(
                createdBook.getPageCount(),
                newBook.getPageCount(),
                String.format(
                        "The expected page count id is [%s], but we got [%s]",
                        newBook.getPageCount(), createdBook.getPageCount()));
        assertFalse(createdBook.getExcerpt().isEmpty(), "Excerpt should not be empty");
        assertFalse(createdBook.getPublishDate().isEmpty(), "Publish date should not be empty");
    }

    @Epic("Books API")
    @Feature("Book Creation")
    @Test(description = "Create a book with duplicated ID")
    public void testCreateBookDuplicateId() {
        // Step 1: Create a book first
        Book original = bookRepository.getFakeNewBook();
        Response originalResponse = bookRequests.createBook(gson.toJson(original)).then().extract().response();
        int existingId = originalResponse.jsonPath().getInt("id");


        String duplicateJson = gson.toJson(existingId);
        Response response = bookRequests.createBook(duplicateJson).then().extract().response();

        assertEquals(
                response.statusCode(),
                HttpStatus.SC_BAD_REQUEST,
                String.format("Expected %s status but got %s", HttpStatus.SC_BAD_REQUEST, response.statusCode()));
    }


    @Test(description = "Create a book invalid date format")
    public void testCreateWithInvalidDateFormat() {
        Book newBook = bookRepository.getFakeNewBook();
        newBook.setPublishDate(new Date().toString());
        String newBookJson = gson.toJson(newBook);
        Response response =
                bookRequests
                        .createBook(newBookJson)
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.SC_BAD_REQUEST) // Expecting HTTP 400 Bad Request
                        .contentType(ContentType.JSON)
                        .extract()
                        .response();

        verifyError.verifyErrorInvalidDateFormat(response);
    }

    @Test(description = "Update Book with valid data")
    public void testUpdateBook() {
        // Step 1: Create book first
        Book originalBook = bookRepository.getFakeNewBook();
        Response createResponse = bookRequests.createBook(gson.toJson(originalBook)).then().extract().response();
        int bookId = createResponse.jsonPath().getInt("id");

        // Step 2: Modify and send update
        Book updatedBook = bookRepository.getFakeNewBook(); // new data for update
        String updatedPayload = gson.toJson(updatedBook);

        Response updateResponse = bookRequests.updateBook(bookId, updatedPayload).then().extract().response();
        Book result = updateResponse.as(Book.class);

        assertEquals(updateResponse.statusCode(), HttpStatus.SC_OK);
        assertEquals(result.getTitle(), updatedBook.getTitle());
    }

    @Ignore
    @Test(description = "Update Book with NON-Existent ID")
    public void testUpdateBookNonExistentID() {
        Book newBook = bookRepository.getFakeNewBook();
        String newBookJson = gson.toJson(newBook);
        Response response =
                bookRequests
                        .updateBook(NON_EXISTENT_ID, newBookJson)
                        .then()
                        .statusCode(HttpStatus.SC_NOT_FOUND)
                        .extract()
                        .response();
        verifyError.verifyErrorNotFound(response);
    }

    @Test(description = "Update Book with invalid Data type ID")
    public void testUpdateBookInvalidDataTypeID() {
        Book newBook = bookRepository.getFakeNewBook();
        String newBookJson = gson.toJson(newBook);
        Response response =
                bookRequests
                        .updateBook(INVALID_ID_DATA_TYPE, newBookJson)
                        .then()
                        .statusCode(HttpStatus.SC_BAD_REQUEST)
                        .extract()
                        .response();
        verifyError.verifyErrorInvalidIdType(response, INVALID_ID_DATA_TYPE);
    }
    @Ignore
    @Test(description = "Update Book with mismatching ID")
    public void testUpdateBookWithMisMatchingID() {
        int idMismatch = (Integer) expectedBook.getId() + 1;
        Book newBook = bookRepository.getFakeNewBook();
        newBook.setId(expectedBook.getId());
        String newBookJson = gson.toJson(newBook);

        bookRequests
                .updateBook(idMismatch, newBookJson)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .response();
    }
    @Ignore
    @Test(

            description = "Update Book with different page count values",
            dataProvider = "pageCountProvider",
            dataProviderClass = DataProviderClass.class)
    public void testUpdateBookWithPageCount(Integer pageCount, int expectedStatus, String description) {
        Book book = bookRepository.getFakeNewBook();
        book.setId(expectedBook.getId());
        book.setPageCount((Integer) pageCount);
        String newBookJson = gson.toJson(book);
        Response response =
                bookRequests.updateBook(book.getId(), newBookJson).then().extract().response();

        assertEquals(
                response.statusCode(),
                expectedStatus,
                " Failed: Expected status "
                        + expectedStatus
                        + ", but got "
                        + response.getStatusCode()
                        + ". Description: "
                        + description);
        if (expectedStatus == HttpStatus.SC_BAD_REQUEST) {
            verifyError.verifyErrorInvalidPageCount(response);
        } else {
            assertEquals(response.jsonPath().getInt("pageCount"), (Integer) pageCount);
        }
    }
    @Ignore
    @Test(description = "Delete book by ID")
    public void testDeleteBookByID() {
        // Create a book first
        Book newBook = bookRepository.getFakeNewBook();
        Response createResponse = bookRequests.createBook(gson.toJson(newBook)).then().extract().response();
        int createdId = createResponse.jsonPath().getInt("id");

        // Delete it
        bookRequests.deleteBook(createdId).then().statusCode(HttpStatus.SC_NO_CONTENT);

        // Validate it's deleted
        bookRequests.getBookById(createdId).then().statusCode(HttpStatus.SC_NOT_FOUND);

    }

    @Ignore
    @Test(description = "Delete Book with NON-Existent ID")
    public void testDeleteBookNonExistentID() {
        Response response =
                bookRequests
                        .deleteBook(NON_EXISTENT_ID)
                        .then()
                        .statusCode(HttpStatus.SC_NOT_FOUND)
                        .extract()
                        .response();
        verifyError.verifyErrorNotFound(response);
    }

    @Test(description = "Delete Book with invalid Data type ID")
    public void testDeleteBookInvalidDataTypeID() {
        Response response =
                bookRequests
                        .deleteBook(INVALID_ID_DATA_TYPE)
                        .then()
                        .statusCode(HttpStatus.SC_BAD_REQUEST)
                        .extract()
                        .response();
        verifyError.verifyErrorInvalidIdType(response, INVALID_ID_DATA_TYPE);
    }
}
