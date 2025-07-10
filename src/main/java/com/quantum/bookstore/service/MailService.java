package com.quantum.bookstore.service;

import com.quantum.bookstore.model.Book;

public interface MailService {
    void sendEmail(Book book, String email);
}