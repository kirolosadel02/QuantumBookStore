package com.quantum.bookstore.service;

import com.quantum.bookstore.model.Book;


public class DefaultShippingService implements ShippingService {

    @Override
    public void ship(Book book, String address) {
        System.out.println("Quantum book store: Shipping " + book.getTitle() + " to " + address);
    }
}