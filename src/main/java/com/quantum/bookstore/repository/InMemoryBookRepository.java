package com.quantum.bookstore.repository;

import com.quantum.bookstore.model.Book;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryBookRepository implements BookRepository {
    private final Map<String, Book> books = new HashMap<>();

    @Override
    public void addBook(Book book) {
        books.put(book.getIsbn(), book);
        System.out.println("Quantum book store: Added book - " + book.getTitle());
    }

    @Override
    public Book findBookByIsbn(String isbn) {
        return books.get(isbn);
    }

    @Override
    public List<Book> findOutdatedBooks(int currentYear, int maxAge) {
        return books.values().stream()
                .filter(book -> book.isOutdated(currentYear, maxAge))
                .collect(Collectors.toList());
    }

    @Override
    public boolean removeBook(String isbn) {
        Book removed = books.remove(isbn);
        if (removed != null) {
            System.out.println("Quantum book store: Removed book - " + removed.getTitle());
            return true;
        }
        return false;
    }

    @Override
    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }
}