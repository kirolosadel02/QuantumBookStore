package com.quantum.bookstore.factory;

import com.quantum.bookstore.model.Book;
import com.quantum.bookstore.model.EBook;
import com.quantum.bookstore.service.MailService;

public class EBookFactory implements BookFactory {
    private final MailService mailService;

    public EBookFactory(MailService mailService) {
        this.mailService = mailService;
    }

    @Override
    public Book createBook(String isbn, String title, String author, int publishYear, double price, Object... params) {
        if (params.length == 0) {
            throw new IllegalArgumentException("File type is required for e-books");
        }

        String fileType = (String) params[0];
        return new EBook(isbn, title, author, publishYear, price, fileType, mailService);
    }
}