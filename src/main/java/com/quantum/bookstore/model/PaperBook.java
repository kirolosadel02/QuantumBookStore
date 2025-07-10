package com.quantum.bookstore.model;

import com.quantum.bookstore.service.ShippingService;

public class PaperBook extends Book {
    private int stock;
    private final ShippingService shippingService;

    public PaperBook(String isbn, String title, String author, int publishYear,
                     double price, int stock, ShippingService shippingService) {
        super(isbn, title, author, publishYear, price);
        this.stock = stock;
        this.shippingService = shippingService;
    }

    @Override
    protected boolean isAvailable(int quantity) {
        return stock >= quantity;
    }

    @Override
    protected void reduceStock(int quantity) {
        stock -= quantity;
    }

    @Override
    protected void deliver(String email, String address) {
        System.out.println("Quantum book store: Shipping paper book to address: " + address);
        shippingService.ship(this, address);
    }

    @Override
    public boolean isOutdated(int currentYear, int maxAge) {
        return (currentYear - publishYear) > maxAge;
    }

    @Override
    public String getBookType() {
        return "Paper Book";
    }

    public int getStock() {
        return stock;
    }

    @Override
    public String toString() {
        return super.toString() + ", Stock: " + stock;
    }
}