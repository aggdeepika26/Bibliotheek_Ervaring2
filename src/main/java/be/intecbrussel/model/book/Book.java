package be.intecbrussel.model.book;

import be.intecbrussel.model.user.Admin;
import be.intecbrussel.model.user.User;
import be.intecbrussel.repository.BookRepository;

import java.time.LocalDate;

public abstract class Book {
    private int bookIDNO;
    private String bookTitle;
    private String bookAuthor;
    private int bookPublishYear;
    private User whereIsTheBook;
    private User bookReservedBy;
    private LocalDate dueDate;
    private java.time.LocalDate issueDate;
    private BookStatus status;

    public Book()
    {}

    public Book(String bookTitle, String bookAuthor, int bookPublishYear, BookStatus status) {
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookPublishYear = bookPublishYear;
        this.whereIsTheBook = new Admin();
        this.bookReservedBy = null;
        this.status = status;
    }

    public int getBookIDNO() {
        return bookIDNO;
    }

    public void setBookIDNO(int bookIDNO) {
        this.bookIDNO = bookIDNO;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public int getBookPublishYear() {
        return bookPublishYear;
    }

    public void setBookPublishYear(int bookPublishYear) {
        this.bookPublishYear = bookPublishYear;
    }

    public User getWhereIsTheBook() {
        return whereIsTheBook;
    }

    public void setWhereIsTheBook(User whereIsTheBook) {
        this.whereIsTheBook = whereIsTheBook;
    }

    public User getBookReservedBy() {
        return bookReservedBy;
    }

    public void setBookReservedBy(User bookReservedBy) {
        this.bookReservedBy = bookReservedBy;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }


    @Override
    public String toString() {
        return "Book{" +
                "bookIDNO=" + bookIDNO +
                ", bookTitle='" + bookTitle + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", bookPublishYear=" + bookPublishYear +
                ", whereIsTheBook=" + whereIsTheBook.getFirstName() +
                ", bookReservedBy=" + bookReservedBy+
                ", dueDate=" + dueDate +
                ", issueDate=" + issueDate +
                ", status=" + status +
                '}';
    }
}
