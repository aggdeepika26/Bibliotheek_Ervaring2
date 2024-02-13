package be.intecbrussel.service;

import be.intecbrussel.model.book.*;
import be.intecbrussel.model.user.Admin;
import be.intecbrussel.model.user.Member;
import be.intecbrussel.model.user.User;
import be.intecbrussel.repository.BookRepository;
import be.intecbrussel.repository.UserRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class BookService {
    private BookRepository bookRepository;
    private UserRepository userRepository;
    private UserService userService;

    private static final double FINE_RATE_PER_DAY = 0.5;

    public BookService(BookRepository bookRepository, UserRepository userRepository, UserService userService) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public void addBook(String titel, String author, int publishYear, String typeBook) throws Exception {
        Book book = null;
        switch (typeBook) {
            case "children" -> book = new ChildrenBooks(titel, author, publishYear, BookStatus.AVAILABLE);
            case "fiction" -> book = new FictionBooks(titel, author, publishYear, BookStatus.AVAILABLE);
            case "nonFiction" -> book = new NonFictionBooks(titel, author, publishYear, BookStatus.AVAILABLE);
            case "other" -> book = new OtherBooks(titel, author, publishYear, BookStatus.AVAILABLE);
        }
        bookRepository.addBook(book);
    }

    public void removeBookByTitle(String title) {
        try {
            Book book = bookRepository.getBookList().stream()
                    .filter(t -> t.getBookTitle().equals(title))
                    .findFirst().get();
            bookRepository.removeBook(book);
        } catch (Exception e) {
            System.out.println("Invalide title entry");
        }
    }

    public void removeBookByID(int IDBook) {
        try {
            Book book = bookRepository.getBookList().stream()
                    .filter(t -> t.getBookIDNO() == IDBook)
                    .findFirst().get();
            bookRepository.removeBook(book);
        } catch (Exception e) {
            System.out.println("Invalide ID entry");
        }
    }

    // method to control type of finding book
    public void findTypeControl(FindAndSortKeys findType) {
        Scanner myScanner = new Scanner(System.in);
        switch (findType) {
            case ID: {
                System.out.println("Enter Book ID");
                int bookId = myScanner.nextInt();
                Optional<Book> foundBookByBookId = findBookByBookId(bookId);

                if (foundBookByBookId.isPresent()) {

                    System.out.println(foundBookByBookId.get());
                } else {
                    System.out.println("Book not found with given ID: ");
                }
                break;

            }

            case TITLE: {
                System.out.println("Enter Book Title");
                String bookTitle = myScanner.nextLine();
                List<Book> foundBookByBookTitle = findBookByBookTitle(bookTitle);

                if (!foundBookByBookTitle.isEmpty()) {
                    System.out.println("Books by " + bookTitle + ":");
                    foundBookByBookTitle.forEach(System.out::println);


                } else {
                    System.out.println("No books found for given: " + bookTitle);
                }
                break;
            }


            case AUTHOR: {
                System.out.println("Enter Book Author");
                String bookAuthor = myScanner.nextLine();
                List<Book> foundBookByBookAuthor = findBookByBookAuthor(bookAuthor);

                if (!foundBookByBookAuthor.isEmpty()) {
                    System.out.println("Books by " + bookAuthor + ":");
                    foundBookByBookAuthor.forEach(System.out::println);
                } else {
                    System.out.println("No books found for author: " + bookAuthor);
                }
                break;
            }
        }
    }

    public Optional<Book> findBookByBookId(int bookId) {
        List<Book> bookList = bookRepository.getBookList();
        return bookList.stream()
                .filter(book -> book.getBookIDNO() == bookId)
                .findFirst();
    }

    public List<Book> findBookByBookTitle(String bookTitle) {
        List<Book> bookList = bookRepository.getBookList();
        return bookList.stream()
                .filter(book -> book.getBookTitle().equalsIgnoreCase(bookTitle))
                .collect(Collectors.toList());
    }

    public List<Book> findBookByBookAuthor(String bookAuthor) {
        List<Book> bookList = bookRepository.getBookList();
        return bookList.stream()
                .filter(book -> book.getBookAuthor().equalsIgnoreCase(bookAuthor))
                .collect(Collectors.toList());
    }

    //Method to control sorting Type
    public void sortTypeControl(FindAndSortKeys sortKey) {
        switch (sortKey) {
            case AUTHOR: {
                List<Book> sortedList = sortBookByBookAuthor();
                sortedList.forEach(System.out::println);
                break;
            }
            case PUBLISHING_YEAR: {
                List<Book> sortedList = sortBookByPublishingYear();
                sortedList.forEach(System.out::println);
                break;
            }
        }
    }

    public List<Book> sortBookByBookAuthor() {
        List<Book> bookList = bookRepository.getBookList();
        return (bookList.stream().sorted(Comparator.comparing(Book::getBookAuthor)).collect(Collectors.toList()));
    }

    public List<Book> sortBookByPublishingYear() {
        List<Book> bookList = bookRepository.getBookList();
        return (bookList.stream().sorted(Comparator.comparing(Book::getBookPublishYear)).collect(Collectors.toList()));
    }

    public void updateBookInfo(int bookId) throws Exception {
        Scanner myScanner = new Scanner(System.in);
        List<Book> bookList = bookRepository.getBookList();
        Optional<Book> bookInfo = bookList.stream().filter(book -> book.getBookIDNO() == bookId).findFirst();
        if (bookInfo.isPresent()) {
            System.out.println("Book Title : ");
            String bookTitle = myScanner.nextLine();
            System.out.println("Book Author : ");
            String bookAuthor = myScanner.nextLine();
            System.out.println("Book Publishing Year : ");
            int publishingYear = myScanner.nextInt();
            updateBook(bookId, bookTitle, bookAuthor, publishingYear);
        } else {
            System.out.println("Book not present");
        }
    }

    public void updateBook(int bookId, String bookTitle, String bookAuthor, int publishingYear) throws Exception {

        List<Book> bookList = bookRepository.getBookList();
        Book updateBookRecord = bookList.stream().filter(book -> book.getBookIDNO() == bookId).findFirst().get();
        if (bookTitle != null && !bookTitle.isEmpty() && !bookAuthor.isEmpty() && bookAuthor != null && publishingYear > 0) {
            updateBookRecord.setBookTitle(bookTitle);
            updateBookRecord.setBookPublishYear(publishingYear);
            updateBookRecord.setBookAuthor(bookAuthor);
            System.out.println(updateBookRecord);
        } else {
            throw new Exception();
        }
    }

    public int countBooks() {
        List<Book> bookList = bookRepository.getBookList();
        return (bookList.size());
    }


    // Changed
    // Used ScannerTools and changed name from reservedBook to reserveBook
    public boolean reserveBook(int bookIDNO, int userId) {

        Optional<Book> optionalBook = findBookByBookId(bookIDNO);
        Optional<User> optionalUser = userService.searchForMemberbyIDnumber(userId);

        if (optionalBook.isPresent() && optionalUser.isPresent()) {
            Book book = optionalBook.get();
            if (book.getStatus() == BookStatus.AVAILABLE) {
                book.setStatus(BookStatus.RESERVED);
                book.setBookReservedBy(optionalUser.orElse(new Member()));
                System.out.println("Book reserved successfully.");
                return true;
            } else {
                System.out.println("Book cannot be reserved.");
                return false;
            }
        } else {
            System.out.println("Book not found with ID: " + bookIDNO);
            return false;
        }
    }


    // changed
    // Used ScannerTools
    public void borrowBook(int bookIDNO, int userId) {

        Optional<Book> optionalBook = findBookByBookId(bookIDNO);
        Optional<User> optionalUser = userService.searchForMemberbyIDnumber(userId);


        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (book.getStatus() == BookStatus.AVAILABLE) {
                // Set issue date to current date
                LocalDate issueDate = LocalDate.now();
                // Calculate due date (e.g., 30 days from issue date)
                LocalDate dueDate = issueDate.plusDays(30);

                book.setStatus(BookStatus.BORROWED);
                book.setWhereIsTheBook(optionalUser.orElse(new Member()));
                book.setIssueDate(issueDate);
                book.setDueDate(dueDate);
                System.out.println("Book borrowed successfully. Due date: " + dueDate);
            } else {
                System.out.println("The book is not available for borrowing.");
            }
        } else {
            System.out.println("User or book not found.");
        }
    }

    // Changed
    // used ScannerTools
    public void issueBook(int bookIDNO, int userId) {

        Optional<Book> optionalBook = findBookByBookId(bookIDNO);
        Optional<User> optionalUser = userService.searchForMemberbyIDnumber(userId);

        if (optionalBook.isPresent() && optionalUser.isPresent()) {
            Book book = optionalBook.get();

            if (book.getStatus() == BookStatus.AVAILABLE) {

                LocalDate issueDate = LocalDate.now();
                LocalDate dueDate = issueDate.plusDays(30);

                book.setStatus(BookStatus.ISSUED);
                book.setWhereIsTheBook(userService.searchForMemberbyIDnumber(userId).orElse(new Member()));
                book.setIssueDate(LocalDate.now());
                book.setIssueDate(issueDate);
                book.setDueDate(dueDate);

                System.out.println("Book " + book.getBookTitle() + " is issued to " + optionalUser + ".");
                System.out.println("Issue Date: " + issueDate);
                System.out.println("Due Date: " + dueDate);
            } else {
                System.out.println("The book can't be issued.");
            }
        } else {
            System.out.println("User or book not found.");
        }

    }


    // Changed
    // used ScannerTools
    // userId should also be verified,if it is the same user that want to renew the same borrowed book.
    public void renewBook(int bookIDNO, int userId) {

        Optional<Book> optionalBook = findBookByBookId(bookIDNO);
        Optional<User> optionalUser = userService.searchForMemberbyIDnumber(userId);

        if (optionalBook.isPresent() && optionalUser.isPresent()) {
            Book book = optionalBook.get();
            User user = optionalUser.get();
            if ((book.getStatus() == BookStatus.ISSUED) || (book.getStatus() == BookStatus.BORROWED)) {
                if (book.getWhereIsTheBook().getUserId() == user.getUserId()) {
                    book.setDueDate(book.getDueDate().plusDays(7)); // Extend the due date by 7 days
                    System.out.println("Book renewed successfully. New due date: " + book.getDueDate());
                } else {
                    System.out.println("Book cannot be renewed. It is not currently borrowed by the user.");
                }
            } else {
                System.out.println("Book cannot be renewed. It is not currently issued or borrowed.");
            }
        } else {
            System.out.println("Book not found with ID: " + bookIDNO);
        }
    }

    // changed
    // used ScannerTools

    public void returnBookByBookIDNO(int bookIDNO, int userId) {

        Optional<Book> bookToReturn = findBookByBookId(bookIDNO);
        Optional<User> optionalUser = userService.searchForMemberbyIDnumber(userId);

        if (bookToReturn.isPresent() && optionalUser.isPresent()) {
            Book book = bookToReturn.get();
            // Changed
            // The book must be borrowed or issued.
            // Issue is handled by admin
            // Borrow is done by members.
            if (book.getStatus() == BookStatus.BORROWED || book.getStatus() == BookStatus.ISSUED) {
                int borrowedByUserId = book.getWhereIsTheBook().getUserId();
                if (borrowedByUserId == userId) {
                    double fineAmount = calculateFine(LocalDate.now(), book.getDueDate());
                    if (fineAmount > 0) {
                        User user = optionalUser.get();
                        ////
                        user.setFineAmount(++fineAmount);
                        ///
                        System.out.println("Fine of $" + fineAmount + " applied for delayed return.");
                    }
                    book.setStatus(BookStatus.AVAILABLE);
                    book.setWhereIsTheBook(new Admin()); // Set borrowedBy to 0 to indicate no user
                    book.setIssueDate(null);
                    book.setDueDate(null);
                    System.out.println("Book with ID " + bookIDNO + " returned successfully.");
                } else {
                    System.out.println("Book is not borrowed by the specified user.");
                }
            } else {
                System.out.println("Book is not currently borrowed.");
            }
        } else {
            System.out.println("Book return unsuccessful. Book not found or user not found.");
        }
    }

    public void returnAllBooks() {
        List<Book> borrowedBooks = bookRepository.getBookList().stream()
                .filter(book -> book.getStatus() == BookStatus.BORROWED)
                .collect(Collectors.toList());

        if (borrowedBooks.isEmpty()) {
            System.out.println("No books are currently borrowed.");
            return;
        }

        for (Book book : borrowedBooks) {
            double fineAmount = calculateFine(LocalDate.now(), book.getDueDate());
            if (fineAmount > 0) {
                User user = userService.searchForMemberbyIDnumber(book.getWhereIsTheBook().getUserId()).orElse(null);
                if (user != null) {
                    //user.addFine(fineAmount);
                    ////
                    user.setFineAmount(++fineAmount);
                    ///
                    System.out.println("Fine of $" + fineAmount + " applied for delayed return of book with ID " + book.getBookIDNO());
                }
            }
        }
        // Reset book details after applying fines
        for (Book book : borrowedBooks) {
            book.setStatus(BookStatus.AVAILABLE);
            book.setWhereIsTheBook(new Admin());
            book.setIssueDate(null);
            book.setDueDate(null);
        }

        System.out.println("All borrowed books returned successfully.");
    }

    private double calculateFine(LocalDate returnDate, LocalDate dueDate) {
        long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
        return Math.max(0, daysLate * FINE_RATE_PER_DAY); // Ensure fine is non-negative
    }

    public void browseBooks() {
        List<Book> bookList = bookRepository.getBookList();
        System.out.println("Available books:");
        bookList.forEach(System.out::println);

    }

   /* public void addFine(int userId, LocalDate issueDate, LocalDate dueDate) {
        Optional<User> optionalUser = userService.searchForMemberbyIDnumber(userId);

        if (optionalUser.isPresent() && issueDate != null && dueDate != null) {
            User user = optionalUser.get();
            double fine = calculateFine(issueDate, dueDate);
            double currentFine = user.getFineAmount();

            System.out.println("Current fine amount for user " + userId + ": " + currentFine);
            System.out.println("Calculated fine: " + fine);

            try {
                user.setFineAmount(currentFine + fine);
                System.out.println("Fine added to user " + userId + " successfully.");
            } catch (Exception e) {
                System.out.println("Error adding fine to user " + userId + ": " + e.getMessage());
            }
        } else {
            System.out.println("User not found or invalid dates provided.");
        }
    }*/



}

