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
        System.out.println("Quantum book store: Starting Quantum Bookstore System Test");
        System.out.println("=" .repeat(60));

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

        System.out.println("\nQuantum book store: All tests completed successfully!");
        System.out.println("=" .repeat(60));
    }

    private static void testAddingBooks(QuantumBookStore bookstore) {
        System.out.println("\n=== Test 1: Adding Books ===");

        bookstore.addPaperBook("978-0132350884", "Clean Code", "Robert C. Martin", 2008, 45.99, 10);
        bookstore.addPaperBook("978-0596007126", "Head First Design Patterns", "Eric Freeman", 2004, 49.99, 5);
        bookstore.addPaperBook("978-0134685991", "Effective Java", "Joshua Bloch", 2017, 54.99, 3);

        bookstore.addEBook("978-0201616224", "Design Patterns", "Gang of Four", 1994, 39.99, "PDF");
        bookstore.addEBook("978-0321356680", "Effective C++", "Scott Meyers", 2005, 42.99, "EPUB");

        bookstore.addShowcaseBook("978-0137081073", "The Clean Coder", "Robert C. Martin", 2011, 39.99);
        bookstore.addShowcaseBook("978-0201835953", "The Mythical Man-Month", "Frederick P. Brooks Jr.", 1995, 44.99);

        System.out.println("Quantum book store: Successfully added 7 books to inventory");
    }

    private static void testDisplayInventory(QuantumBookStore bookstore) {
        System.out.println("\n=== Test 2: Display Current Inventory ===");
        bookstore.displayInventory();
    }

    private static void testSuccessfulPurchases(QuantumBookStore bookstore) {
        System.out.println("\n=== Test 3: Successful Purchases ===");

        try {
            System.out.println("\n--- Purchasing Paper Book ---");
            double amount1 = bookstore.buyBook("978-0132350884", 2, "customer@email.com", "123 Main St, City, State");
            System.out.println("Quantum book store: Payment processed: $" + String.format("%.2f", amount1));

            System.out.println("\n--- Purchasing E-Book ---");
            double amount2 = bookstore.buyBook("978-0201616224", 1, "customer@email.com", "123 Main St, City, State");
            System.out.println("Quantum book store: Payment processed: $" + String.format("%.2f", amount2));

            System.out.println("\n--- Purchasing Another E-Book ---");
            double amount3 = bookstore.buyBook("978-0321356680", 3, "customer2@email.com", "456 Oak Ave, City, State");
            System.out.println("Quantum book store: Payment processed: $" + String.format("%.2f", amount3));

            double totalSales = amount1 + amount2 + amount3;
            System.out.println("\nQuantum book store: Total sales: $" + String.format("%.2f", totalSales));

        } catch (Exception e) {
            System.out.println("Quantum book store: Unexpected error during successful purchases: " + e.getMessage());
        }
    }

    private static void testFailedPurchases(QuantumBookStore bookstore) {
        System.out.println("\n=== Test 4: Failed Purchases (Expected Failures) ===");

        System.out.println("\n--- Attempting to Purchase Showcase Book ---");
        try {
            bookstore.buyBook("978-0137081073", 1, "customer@email.com", "123 Main St, City, State");
            System.out.println("Quantum book store: ERROR - This should have failed!");
        } catch (Exception e) {
            System.out.println("Quantum book store: Expected failure: " + e.getMessage());
        }

        System.out.println("\n--- Attempting to Purchase More Than Available Stock ---");
        try {
            bookstore.buyBook("978-0596007126", 10, "customer@email.com", "123 Main St, City, State");
            System.out.println("Quantum book store: ERROR - This should have failed!");
        } catch (Exception e) {
            System.out.println("Quantum book store: Expected failure: " + e.getMessage());
        }

        System.out.println("\n--- Attempting to Purchase Non-Existent Book ---");
        try {
            bookstore.buyBook("978-9999999999", 1, "customer@email.com", "123 Main St, City, State");
            System.out.println("Quantum book store: ERROR - This should have failed!");
        } catch (Exception e) {
            System.out.println("Quantum book store: Expected failure: " + e.getMessage());
        }
    }

    private static void testRemoveOutdatedBooks(QuantumBookStore bookstore) {
        System.out.println("\n=== Test 5: Remove Outdated Books ===");

        List<Book> removedBooks = bookstore.removeOutdatedBooks(2024, 15);
        System.out.println("Quantum book store: Removed " + removedBooks.size() + " outdated books");

        if (!removedBooks.isEmpty()) {
            System.out.println("Quantum book store: Removed books:");
            for (Book book : removedBooks) {
                System.out.println("Quantum book store: - " + book.getTitle() + " (" + book.getPublishYear() + ")");
            }
        }

        System.out.println("\n--- Removing books older than 30 years ---");
        List<Book> removedBooks2 = bookstore.removeOutdatedBooks(2024, 30);
        System.out.println("Quantum book store: Removed " + removedBooks2.size() + " additional outdated books");
    }

    private static void testFinalInventory(QuantumBookStore bookstore) {
        System.out.println("\n=== Test 6: Final Inventory Check ===");
        bookstore.displayInventory();

        System.out.println("\n--- Checking Specific Book Details ---");
        Book cleanCode = bookstore.getBookByIsbn("978-0132350884");
        if (cleanCode != null) {
            System.out.println("Quantum book store: Clean Code remaining details: " + cleanCode.toString());
        }

        Book designPatterns = bookstore.getBookByIsbn("978-0201616224");
        if (designPatterns != null) {
            System.out.println("Quantum book store: Design Patterns details: " + designPatterns.toString());
        }

        List<Book> allBooks = bookstore.getAllBooks();
        System.out.println("\nQuantum book store: Final inventory contains " + allBooks.size() + " books");
    }
}