package com.example.restapp.service;

import com.example.restapp.model.Book;
import com.example.restapp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookService {

    @Autowired
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    public Book findBook(UUID uuid) {
        return bookRepository.findById(uuid)
                .orElseThrow();
    }

    public void deleteBook(Book book) {
        bookRepository.delete(book);
    }

    public List<Book> findByAuthorAndGenre(String author, String genre){
        return bookRepository.findAllByAuthorAndGenre(author, genre);
    }

    public List<Book> findByAuthorAndGenre(String author, String genre, Pageable pageable){
        return bookRepository.findAllByAuthorAndGenre(author, genre, pageable);
    }

}
