package com.quantum.bookstore.service;

import com.quantum.bookstore.model.Book;

public interface ShippingService {
    void ship(Book book, String address);
}