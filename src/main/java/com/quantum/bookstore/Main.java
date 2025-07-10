package com.quantum.bookstore;

import com.quantum.bookstore.repository.*;
import com.quantum.bookstore.service.*;
import com.quantum.bookstore.store.QuantumBookStore;

public class Main {
    public static void main(String[] args) {
        ShippingService shippingService = new DefaultShippingService();
        MailService mailService = new DefaultMailService();

        BookRepository repository = new InMemoryBookRepository();

        QuantumBookStore bookstore = new QuantumBookStore(repository, shippingService, mailService);

        bookstore.addPaperBook("P123", "Clean Code", "Robert Martin", 2008, 45.0, 10);
        bookstore.addEBook("E456", "Effective Java", "Joshua Bloch", 2018, 30.0, "PDF");
        bookstore.addShowcaseBook("S789", "Design Patterns", "GoF", 1994, 0.0);

        bookstore.displayInventory();

        bookstore.buyBook("P123", 1, "customer@example.com", "123 Quantum Street");

        bookstore.displayInventory();

        bookstore.removeOutdatedBooks(2025, 20);

        bookstore.displayInventory();

        System.out.println("Quantum book store: Test completed!");
    }
}
