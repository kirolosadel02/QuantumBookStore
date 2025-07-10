package com.quantum.bookstore.model;

public class ShowcaseBook extends Book {

    public ShowcaseBook(String isbn, String title, String author, int publishYear, double price) {
        super(isbn, title, author, publishYear, price);
    }

    @Override
    protected boolean isAvailable(int quantity) {
        return false;
    }

    @Override
    protected void reduceStock(int quantity) {
        throw new UnsupportedOperationException("Quantum book store: Showcase books cannot be purchased");
    }

    @Override
    protected void deliver(String email, String address) {
        throw new UnsupportedOperationException("Quantum book store: Showcase books cannot be delivered");
    }

    @Override
    public boolean isOutdated(int currentYear, int maxAge) {
        return (currentYear - publishYear) > maxAge;
    }

    @Override
    public String getBookType() {
        return "Showcase Book";
    }

    @Override
    public String toString() {
        return super.toString() + " [NOT FOR SALE]";
    }
}