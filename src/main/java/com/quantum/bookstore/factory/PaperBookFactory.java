package com.quantum.bookstore.factory;

import com.quantum.bookstore.model.Book;
import com.quantum.bookstore.model.PaperBook;
import com.quantum.bookstore.service.ShippingService;

public class PaperBookFactory implements BookFactory {
    private final ShippingService shippingService;

    public PaperBookFactory(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @Override
    public Book createBook(String isbn, String title, String author, int publishYear, double price, Object... params) {
        if (params.length == 0) {
            throw new IllegalArgumentException("Stock quantity is required for paper books");
        }

        int stock = (Integer) params[0];
        return new PaperBook(isbn, title, author, publishYear, price, stock, shippingService);
    }
}