package com.quantum.bookstore.repository;

import com.quantum.bookstore.model.Book;
import java.util.List;

public interface BookRepository {
    void addBook(Book book);

    Book findBookByIsbn(String isbn);

    List<Book> findOutdatedBooks(int currentYear, int maxAge);

    boolean removeBook(String isbn);

    List<Book> getAllBooks();
}