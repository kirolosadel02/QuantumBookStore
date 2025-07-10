package com.quantum.bookstore;

import com.quantum.bookstore.model.Book;
import com.quantum.bookstore.repository.BookRepository;
import com.quantum.bookstore.repository.InMemoryBookRepository;
import com.quantum.bookstore.service.ShippingService;
import com.quantum.bookstore.service.MailService;
import com.quantum.bookstore.service.DefaultShippingService;
import com.quantum.bookstore.service.DefaultMailService;
import com.quantum.bookstore.store.QuantumBookStore;

import java.util.List;

public class QuantumBookstoreFullTest {

    public static void main(String[] args) {
        System.out.println("Quantum book store: Starting system test");

        ShippingService shippingService = new DefaultShippingService();
        MailService mailService = new DefaultMailService();
        BookRepository repository = new InMemoryBookRepository();

        QuantumBookStore bookstore = new QuantumBookStore(repository, shippingService, mailService);

        testAddingBooks(bookstore);
        testDisplayInventory(bookstore);
        testSuccessfulPurchases(bookstore);
        testFailedPurchases(bookstore);
        testRemoveOutdatedBooks(bookstore);
        testFinalInventory(bookstore);

        System.out.println("Quantum book store: All tests completed successfully");
    }

    private static void testAddingBooks(QuantumBookStore bookstore) {
        System.out.println("Adding books...");

        bookstore.addPaperBook("978-0131103627", "The C Programming Language", "Brian", 1978, 60.00, 10);
        bookstore.addPaperBook("978-0201633610", "Design Patterns: Elements of Reusable Object-Oriented Software", "Erich Gamma", 1994, 70.00, 5);
        bookstore.addPaperBook("978-0132350884", "Clean Code", "Robert C. Martin", 2008, 50.00, 8);

        bookstore.addEBook("978-0134685991", "Effective Java", "Joshua Bloch", 2018, 45.00, "PDF");
        bookstore.addEBook("978-0131177055", "Refactoring", "Martin Fowler", 1999, 55.00, "EPUB");

        bookstore.addShowcaseBook("978-0201485677", "The Pragmatic Programmer", "David Thomas", 1999, 0.0);
        bookstore.addShowcaseBook("978-0135974445", "Introduction to Algorithms", "Thomas", 2009, 0.0);

        System.out.println("Books added to inventory");
    }

    private static void testDisplayInventory(QuantumBookStore bookstore) {
        System.out.println("Displaying inventory:");
        bookstore.displayInventory();
    }

    private static void testSuccessfulPurchases(QuantumBookStore bookstore) {
        System.out.println("Testing successful purchases...");

        try {
            double amount1 = bookstore.buyBook("978-0131103627", 2, "alice@example.com", "42 Elm St");
            System.out.println("Purchased The C Programming Language: $" + String.format("%.2f", amount1));

            double amount2 = bookstore.buyBook("978-0134685991", 1, "bob@example.com", "99 Maple Ave");
            System.out.println("Purchased Effective Java: $" + String.format("%.2f", amount2));

            double total = amount1 + amount2;
            System.out.println("Total sales so far: $" + String.format("%.2f", total));

        } catch (Exception e) {
            System.out.println("Unexpected error during purchase: " + e.getMessage());
        }
    }

    private static void testFailedPurchases(QuantumBookStore bookstore) {
        System.out.println("Testing expected failures...");

        try {
            bookstore.buyBook("978-0201485677", 1, "charlie@example.com", "123 Oak Blvd");
            System.out.println("ERROR: Showcase book purchase should have failed!");
        } catch (Exception e) {
            System.out.println("Expected failure: " + e.getMessage());
        }

        try {
            bookstore.buyBook("978-0201633610", 10, "dave@example.com", "456 Pine Rd");
            System.out.println("ERROR: Over-stock purchase should have failed!");
        } catch (Exception e) {
            System.out.println("Expected failure: " + e.getMessage());
        }

        try {
            bookstore.buyBook("999-9999999999", 1, "eve@example.com", "789 Birch Ln");
            System.out.println("ERROR: Non-existent book purchase should have failed!");
        } catch (Exception e) {
            System.out.println("Expected failure: " + e.getMessage());
        }
    }

    private static void testRemoveOutdatedBooks(QuantumBookStore bookstore) {
        System.out.println("Removing outdated books older than 25 years...");
        List<Book> removedBooks = bookstore.removeOutdatedBooks(2024, 25);

        System.out.println("Removed " + removedBooks.size() + " books");
        for (Book book : removedBooks) {
            System.out.println("Removed: " + book.getTitle() + " (" + book.getPublishYear() + ")");
        }
    }

    private static void testFinalInventory(QuantumBookStore bookstore) {
        System.out.println("Final inventory check:");
        bookstore.displayInventory();

        Book cleanCode = bookstore.getBookByIsbn("978-0132350884");
        if (cleanCode != null) {
            System.out.println("Details for Clean Code: " + cleanCode);
        }

        List<Book> allBooks = bookstore.getAllBooks();
        System.out.println("Final number of books in inventory: " + allBooks.size());
    }
}
