package com.quantum.bookstore.factory;

import com.quantum.bookstore.model.Book;

public interface BookFactory {
    Book createBook(String isbn, String title, String author, int publishYear, double price, Object... params);
}