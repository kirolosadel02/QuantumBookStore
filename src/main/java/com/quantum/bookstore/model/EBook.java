package com.quantum.bookstore.model;

import com.quantum.bookstore.service.MailService;


public class EBook extends Book {
    private String fileType;
    private final MailService mailService;

    public EBook(String isbn, String title, String author, int publishYear,
                 double price, String fileType, MailService mailService) {
        super(isbn, title, author, publishYear, price);
        this.fileType = fileType;
        this.mailService = mailService;
    }

    @Override
    protected boolean isAvailable(int quantity) {
        return true;
    }

    @Override
    protected void reduceStock(int quantity) {
        // No stock reduction needed for EBooks
    }

    @Override
    protected void deliver(String email, String address) {
        System.out.println("Quantum book store: Sending EBook to email: " + email);
        mailService.sendEmail(this, email);
    }

    @Override
    public boolean isOutdated(int currentYear, int maxAge) {
        return (currentYear - publishYear) > maxAge;
    }

    @Override
    public String getBookType() {
        return "EBook";
    }

    public String getFileType() {
        return fileType;
    }

    @Override
    public String toString() {
        return super.toString() + ", File Type: " + fileType;
    }
}