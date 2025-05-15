package com.api.test.routes;

public class Routes {

    public static final String BASE_URL = "https://fakerestapi.azurewebsites.net";

    //BOOKS
    // GET /api/v1/Books – Retrieve a list of all books.
    public static final String GET_ALL_BOOKS = "api/v1/Books";

    // GET /api/v1/Books/{id} – Retrieve details of a specific book by its ID.
    public static final String GET_ALL_BOOKS_BY_ID = "api/v1/Books{id}";

    //POST /api/v1/Books –
    public static final String CREATE_BOOK = "api/v1/Books";

    //PUT /api/v1/Books/{id} – Update an existing book by its ID.
    public static final String UPDATE_BOOK_BY_ID = "api/v1/Books{id}";

    //DELETE /api/v1/Books/
    public static final String DELETE_BOOK = "api/v1/Books{id}";

    //AUTHORS
    public static final String GET_AUTHORS = "api/v1/Authors";
    public static final String GET_AUTHORS_BY_ID = "api/v1/Authors/{id}";
    public static final String POST_AUTHOR = "api/v1/Authors";
    public static final String PUT_AUTHOR = "api/v1/Authors{id}";
    public static final String DELETE_AUTHOR = "api/v1/Authors{id}";

}