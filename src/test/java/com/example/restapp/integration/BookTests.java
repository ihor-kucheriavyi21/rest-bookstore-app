package com.example.restapp.integration;

import com.example.restapp.RestAppApplication;
import com.example.restapp.controller.BookController;
import com.example.restapp.model.Book;
import com.example.restapp.model.Publisher;
import com.example.restapp.service.PublisherService;
import com.github.javafaker.Faker;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = RestAppApplication.class)
@AutoConfigureMockMvc
class BookTests {

    @Autowired
    private PublisherService publisherService;
    @Autowired
    private BookController bookController;

    private Book bookForCreation;
    private Publisher publisher;

    @BeforeEach
    public void initData() {
        publisher = new Publisher();
        publisher.setName("publisherTestName");
        publisher.setAddress("publisherTestAddress");
        publisher = publisherService.savePublisher(publisher);
        bookForCreation = getBookForTest();

    }

    @Test
    void testBookCreationIsSuccessAndGetById() {
        ResponseEntity<Book> actualResult = bookController.createBook(bookForCreation);
        Assertions.assertEquals(actualResult.getStatusCode().value(), HttpStatus.CREATED.value());

        ResponseEntity<Book> bookById = bookController.getBookById(Objects.requireNonNull(
                actualResult.getBody()).getId());
        Book createdBook = bookById.getBody();
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(bookById.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        softAssertions.assertThat(Objects.requireNonNull(createdBook).getAuthor())
                .isEqualTo(bookForCreation.getAuthor());
        softAssertions.assertThat(createdBook.getName())
                .isEqualTo(bookForCreation.getName());
        softAssertions.assertThat(createdBook.getId())
                .isEqualTo(bookForCreation.getId());
        softAssertions.assertThat(createdBook.getGenre())
                .isEqualTo(bookForCreation.getGenre());
        softAssertions.assertAll();
    }

    @Test
    void testUpdateBook() {
        String updatedBookName = "UpdatedBookName";
        String updatedAuthor = "UpdatedAuthor";
        bookForCreation.setName(updatedBookName);
        bookForCreation.setAuthor(updatedAuthor);
        bookController.createBook(bookForCreation);
        ResponseEntity<Book> bookResponseEntity = bookController.updateBook(bookForCreation);
        ResponseEntity<Book> actualResult = bookController.getBookById(Objects.requireNonNull(bookResponseEntity.getBody()).getId());
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(actualResult.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        softAssertions.assertThat(Objects.requireNonNull(actualResult.getBody()).getName())
                .isEqualTo(updatedBookName);
        softAssertions.assertThat(actualResult.getBody().getAuthor())
                .isEqualTo(updatedAuthor);
        softAssertions.assertAll();
    }

    @Test
    void testFindBooksBy2ParametersByPageable() {
        String genre = Faker.instance().book().genre();
        for (int i = 0; i < 6; i++) {
            Book bookForTest = getBookForTest();
            bookForTest.setGenre(genre);
            bookController.createBook(bookForTest);
        }
        Book bookForTest = getBookForTest();
        bookForTest.setGenre(genre);
        bookForTest.setName("Test2PageableParameters");

        Book bookWithWrongGenre = getBookForTest();
        bookWithWrongGenre.setGenre("WrongGenre");
        bookController.createBook(bookForTest);
        bookController.createBook(bookWithWrongGenre);


        int expectedPageSize = 5;
        ResponseEntity<List<Book>> firstPageBooks = bookController.getBooksByAuthorAndGenrePageable(
                bookForTest.getAuthor(), bookForTest.getGenre(), 0, expectedPageSize);
        ResponseEntity<List<Book>> secondPageBooks = bookController.getBooksByAuthorAndGenrePageable(
                bookForTest.getAuthor(), bookForTest.getGenre(), 1, expectedPageSize);
        List<Book> firstListBooks = Objects.requireNonNull(firstPageBooks.getBody());
        List<Book> secondListBooks = Objects.requireNonNull(secondPageBooks.getBody());
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(firstPageBooks.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        softAssertions.assertThat(secondPageBooks.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        softAssertions.assertThat(secondListBooks.size())
                .isEqualTo(2);
        softAssertions.assertThat(firstListBooks).size()
                .isEqualTo(expectedPageSize);
        softAssertions.assertThat(firstListBooks)
                .doesNotContain(bookWithWrongGenre);
        softAssertions.assertAll();
    }

    @Test
    void testDeleteBook() {
        ResponseEntity<Book> createdBook = bookController.createBook(bookForCreation);
        ResponseEntity<Void> bookResponseEntity = bookController.deleteBook(Objects.requireNonNull(createdBook.getBody()).getId());
        Assertions.assertEquals(HttpStatus.NO_CONTENT, bookResponseEntity.getStatusCode());
    }

    private Book getBookForTest() {
        Book book = new Book();
        book.setAuthor("bookAuthorTest");
        book.setName("bookNameTest");
        book.setGenre("bookGenreTest");
        book.setPublisher(publisher);
        return book;
    }
}
