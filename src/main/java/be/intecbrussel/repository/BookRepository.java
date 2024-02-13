package be.intecbrussel.repository;


import be.intecbrussel.model.book.*;
import be.intecbrussel.model.user.User;
import be.intecbrussel.utils.ValidationTools;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class BookRepository {
    private List<Book> bookList  = new ArrayList<>();

    private int bookId;

    {
        try {
            addBook(new ChildrenBooks("Diary of Wimpy Kid", "Jeff Kinney" , 2023, BookStatus.AVAILABLE));
            addBook(new ChildrenBooks("HarryPotter and the Philosphers", "J.K.Rowling" , 1997, BookStatus.AVAILABLE));
            addBook(new ChildrenBooks("Good night Moon", "Margaret Wise Brown" , 1947, BookStatus.AVAILABLE));
            addBook(new FictionBooks("The Great Gatsby", "F.Scott Fitzgerald" , 1925, BookStatus.AVAILABLE));
            addBook(new FictionBooks("The Alchemist", "Paulo Coelho" , 1947, BookStatus.AVAILABLE));
            addBook(new FictionBooks("To kill a mocking bird", "Harper Lee" , 1960, BookStatus.AVAILABLE));
            addBook(new NonFictionBooks("In Cold Blood", "Truman Copote" , 1965, BookStatus.AVAILABLE));
            addBook(new NonFictionBooks("A Brief History of Time", "Stephen Hawkings" , 1988, BookStatus.AVAILABLE));
            addBook(new NonFictionBooks("Into Thin Air", "John Krakower" , 1997, BookStatus.AVAILABLE));
            addBook(new OtherBooks("I am Malala", "Christina Lamb" , 2013, BookStatus.AVAILABLE));
            addBook(new OtherBooks("The Happiest Man on Earth", "Eddie Jalem" , 2020, BookStatus.AVAILABLE));
        } catch (Exception e) {
            System.out.println("Book info can not be nul");
        }
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void addBook(Book book) throws Exception {
        ValidationTools vt = new ValidationTools();
        if (book != null && vt.isValidBook(book))
        {
            book.setBookIDNO(bookId);
            bookList.add(book);
            bookId++;
            //bookList.forEach(System.out::println);
        }
        else
        {
            throw new Exception("Kindly enter correct book info");
            // System.out.println("Unable to add the book. Check conditions.");
        }

    }
    public boolean removeBook (Book book){
        if (!getBookList().contains(book)){
            return false;
        }
        getBookList().remove(book);
        return true;
    }

}
