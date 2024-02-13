package be.intecbrussel.utils;

import be.intecbrussel.model.book.Book;
import be.intecbrussel.model.book.FindAndSortKeys;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ValidationTools {


    public boolean isValidBook(Book book)
    {
        // Example: Check if essential information is not null
        return
                book.getBookAuthor() != null && !book.getBookAuthor().isEmpty()&&
                book.getBookTitle()!= null && !book.getBookTitle().isEmpty()&&
                //book.bookInLibrary != null &&
                book.getBookPublishYear() != 0 ;
    }
}
