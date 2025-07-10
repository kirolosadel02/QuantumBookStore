package com.quantum.bookstore.model;

public abstract class Book {
    protected String isbn;
    protected String title;
    protected String author;
    protected int publishYear;
    protected double price;

    public Book(String isbn, String title, String author, int publishYear, double price) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publishYear = publishYear;
        this.price = price;
    }

    public final double processPurchase(int quantity, String email, String address) {
        if (!isAvailable(quantity)) {
            throw new IllegalArgumentException("Quantum book store: Book not available in requested quantity");
        }

        double totalAmount = calculateTotal(quantity);
        reduceStock(quantity);
        deliver(email, address);

        return totalAmount;
    }

    protected abstract boolean isAvailable(int quantity);
    protected abstract void reduceStock(int quantity);
    protected abstract void deliver(String email, String address);

    protected double calculateTotal(int quantity) {
        return price * quantity;
    }

    public abstract boolean isOutdated(int currentYear, int maxAge);
    public abstract String getBookType();

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getPublishYear() { return publishYear; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return String.format("%s - %s by %s (ISBN: %s, Price: $%.2f)",
                getBookType(), title, author, isbn, price);
    }
}