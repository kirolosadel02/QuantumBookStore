package com.quantum.bookstore.factory;

import com.quantum.bookstore.model.Book;
import com.quantum.bookstore.model.ShowcaseBook;

public class ShowcaseBookFactory implements BookFactory {

    @Override
    public Book createBook(String isbn, String title, String author, int publishYear, double price, Object... params) {
        return new ShowcaseBook(isbn, title, author, publishYear, price);
    }
}