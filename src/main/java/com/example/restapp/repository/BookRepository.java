package com.example.restapp.repository;

import com.example.restapp.model.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID>, PagingAndSortingRepository<Book, UUID> {

    List<Book> findAllByAuthorAndGenre(String author, String genre);
    List<Book> findAllByAuthorAndGenre(String author, String genre, Pageable pageable);

}
