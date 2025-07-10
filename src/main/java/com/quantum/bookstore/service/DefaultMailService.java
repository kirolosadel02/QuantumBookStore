package com.quantum.bookstore.service;

import com.quantum.bookstore.model.Book;

public class DefaultMailService implements MailService {

    @Override
    public void sendEmail(Book book, String email) {
        System.out.println("Quantum book store: Emailing " + book.getTitle() + " to " + email);
    }
}