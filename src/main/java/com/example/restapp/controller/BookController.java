package com.example.restapp.controller;

import com.example.restapp.model.Book;
import com.example.restapp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<>(bookService.findAllBooks(), HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<Book> getBookById(@RequestParam UUID id) {
        return new ResponseEntity<>(bookService.findBook(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Book> createBook(Book book) {
        return new ResponseEntity<>(bookService.saveBook(book), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Book> updateBook(Book book) {
        return new ResponseEntity<>(bookService.updateBook(book), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteBook(@RequestParam UUID id) {
        bookService.deleteBook(bookService.findBook(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/genre/author")
    public ResponseEntity<List<Book>> getBooksByAuthorAndGenre(@RequestParam String author,
                                                               @RequestParam String genre) {
        return new ResponseEntity<>(bookService.findByAuthorAndGenre(author, genre), HttpStatus.OK);
    }

    @GetMapping("/genre/author/page")
    public ResponseEntity<List<Book>> getBooksByAuthorAndGenrePageable(@RequestParam String author,
                                                                       @RequestParam String genre,
                                                                       @RequestParam Integer pageNumber,
                                                                       @RequestParam Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return new ResponseEntity<>(bookService.findByAuthorAndGenre(author, genre, pageable), HttpStatus.OK);
    }

}
