package com.quantum.bookstore.store;

import com.quantum.bookstore.model.Book;
import com.quantum.bookstore.repository.BookRepository;
import com.quantum.bookstore.factory.*;
import com.quantum.bookstore.service.ShippingService;
import com.quantum.bookstore.service.MailService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuantumBookStore {
    private final BookRepository repository;
    private final Map<String, BookFactory> factories;

    public QuantumBookStore(BookRepository repository, ShippingService shippingService, MailService mailService) {
        this.repository = repository;
        this.factories = new HashMap<>();

        // Register factories for different book types
        factories.put("PAPER", new PaperBookFactory(shippingService));
        factories.put("EBOOK", new EBookFactory(mailService));
        factories.put("SHOWCASE", new ShowcaseBookFactory());
    }

    public double buyBook(String isbn, int quantity, String email, String address) {
        Book book = repository.findBookByIsbn(isbn);
        if (book == null) {
            throw new IllegalArgumentException("Quantum book store: Book not found with ISBN: " + isbn);
        }

        try {
            double totalAmount = book.processPurchase(quantity, email, address);
            System.out.println("Quantum book store: Purchase successful. Total amount: $" + String.format("%.2f", totalAmount));
            return totalAmount;
        } catch (Exception e) {
            System.out.println("Quantum book store: Purchase failed - " + e.getMessage());
            throw e;
        }
    }

    public void displayInventory() {
        System.out.println("Quantum book store: Current Inventory:");
        List<Book> allBooks = repository.getAllBooks();
        if (allBooks.isEmpty()) {
            System.out.println("Quantum book store: No books in inventory");
            return;
        }

        for (Book book : allBooks) {
            System.out.println("Quantum book store: " + book.toString());
        }
    }

    public Book getBookByIsbn(String isbn) {
        return repository.findBookByIsbn(isbn);
    }

    public List<Book> getAllBooks() {
        return repository.getAllBooks();
    }

    public void addPaperBook(String isbn, String title, String author, int publishYear, double price, int stock) {
        BookFactory factory = factories.get("PAPER");
        Book book = factory.createBook(isbn, title, author, publishYear, price, stock);
        repository.addBook(book);
    }

    public void addEBook(String isbn, String title, String author, int publishYear, double price, String fileType) {
        BookFactory factory = factories.get("EBOOK");
        Book book = factory.createBook(isbn, title, author, publishYear, price, fileType);
        repository.addBook(book);
    }

    public void addShowcaseBook(String isbn, String title, String author, int publishYear, double price) {
        BookFactory factory = factories.get("SHOWCASE");
        Book book = factory.createBook(isbn, title, author, publishYear, price);
        repository.addBook(book);
    }

    public List<Book> removeOutdatedBooks(int currentYear, int maxAge) {
        System.out.println("Quantum book store: Removing outdated books older than " + maxAge + " years");
        List<Book> outdatedBooks = repository.findOutdatedBooks(currentYear, maxAge);

        for (Book book : outdatedBooks) {
            repository.removeBook(book.getIsbn());
        }

        return outdatedBooks;
    }
}
