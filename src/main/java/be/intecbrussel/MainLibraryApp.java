package be.intecbrussel;

import be.intecbrussel.model.book.Book;
import be.intecbrussel.model.book.ChildrenBooks;
import be.intecbrussel.model.book.FindAndSortKeys;
import be.intecbrussel.repository.BookRepository;
import be.intecbrussel.repository.UserRepository;
import be.intecbrussel.service.BookService;
import be.intecbrussel.service.UserService;
import be.intecbrussel.utils.ScannerTools;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import be.intecbrussel.utils.ScannerTools;

//ok
public class MainLibraryApp {
    static BookRepository bookRepository = new BookRepository();
    static UserRepository userRepository = new UserRepository();
    static UserService userService = new UserService(userRepository, bookRepository);
    static BookService bookService = new BookService(bookRepository, userRepository, userService);



    public static void main(String[] args) throws Exception {

        char ans = 'y';
        Scanner myScanner = new Scanner(System.in);
        FindAndSortKeys findAndSortKeys;
        System.out.println("Welcome to Alexandria 2.99 \n");
        System.out.println("Enter your status: admin/member");
        String status = myScanner.nextLine().toLowerCase();
        do {
            System.out.println("Kindly choose the method you want to perform");
            switch (status) {
                case "admin": {
                    System.out.println("1.Add Book");
                    System.out.println("2.Find Book");
                    System.out.println("3.Remove Book");
                    System.out.println("4.Sort Book");
                    System.out.println("5.Update Book Record");
                    System.out.println("6.Count Book");

                    //Changed
                    //BorrowBook() is for members.
                    // Admin has issueBook().
                    //System.out.println("7. borrow Book");
                    System.out.println("8.Issue Book");
                    System.out.println("9.Renew Book");
                    System.out.println("10.Return Book");
                    System.out.println("11.Browse Books");
                    System.out.println("12.Add Member");
                    System.out.println("13.Remove Member");
                    System.out.println("14.Search Member");
                    System.out.println("15.Update member info");
                    System.out.println("16.List Members by date of joining");
                    System.out.println("0.Exit");
                    break;
                }
                case "member": {
                    System.out.println("17.Find Book");
                    System.out.println("18.Browse member info");
                    System.out.println("19.Browse Book");
                    System.out.println("20.Reserve Book");
                    System.out.println("0.Exit");
                    break;
                }
                default: {
                    System.out.println("Enter valid choice");
                }

            }

            System.out.println("Kindly Enter your choice");
            int choice = myScanner.nextInt();
            switch (choice) {
                case 1: {
                    try {
                        addBook();
                    } catch (Exception e) {
                        System.out.println("Book info can not be null");
                    }
                    break;

                }
                case 2:

                case 17: {
                    System.out.println("How do you want to find a book Book Title/ Book Author/Book No");
                    System.out.println("Enter your choice");
                    System.out.println("1. Book Title");
                    System.out.println("2. Book Author");
                    System.out.println("3. Book No");
                    choice = myScanner.nextInt();
                    switch (choice) {
                        case 1: {
                            findAndSortKeys = FindAndSortKeys.TITLE;
                            bookService.findTypeControl(findAndSortKeys);
                            break;
                        }
                        case 2: {
                            findAndSortKeys = FindAndSortKeys.AUTHOR;
                            bookService.findTypeControl(findAndSortKeys);
                            break;
                        }
                        case 3: {
                            findAndSortKeys = FindAndSortKeys.ID;
                            bookService.findTypeControl(findAndSortKeys);
                            break;
                        }
                        default: {
                            System.out.println("Invalid choice");
                            break;

                        }
                    }
                    break;
                }
                case 3: {
                    removeBook();
                    break;
                }
                case 4: {
                    //Sort method
                    System.out.println("How do you want to sort books Book Author/ Publishing year");
                    System.out.println("Enter your choice");
                    System.out.println("1. Book Author");
                    System.out.println("2. Publishing Year");
                    choice = myScanner.nextInt();
                    switch (choice) {
                        case 1: {
                            findAndSortKeys = FindAndSortKeys.AUTHOR;
                            bookService.sortTypeControl(findAndSortKeys);
                            System.out.println("List Sorted");
                            break;
                        }
                        case 2: {
                            findAndSortKeys = FindAndSortKeys.PUBLISHING_YEAR;
                            bookService.sortTypeControl(findAndSortKeys);
                            System.out.println("List Sorted");
                            break;
                        }

                        default: {
                            System.out.println("Invalid choice");
                            break;

                        }
                    }
                    break;
                }
                case 5: {
                    //Update book Record
                    System.out.println("Enter book Id to update info");
                    int bookNo = myScanner.nextInt();
                    try {
                        bookService.updateBookInfo(bookNo);
                        System.out.println("Info updated");
                    } catch (Exception e) {
                        System.out.println("Book info can not be null");
                    }
                    break;
                }
                case 6: {
                    //count books method
                    int countOfBooks = bookService.countBooks();
                    System.out.println("Total no of books are   " + countOfBooks);
                    break;
                }
                case 7:
                    borrowBook();
                    break;
                case 8:
                    issueBook();
                    break;
                case 9:
                    renewBook();
                    break;
                case 10:
                    returnBook();
                    break;

                    // Changed case 11 and 20 are browseBooks();
                case 11:
                case 19:
                    bookService.browseBooks();
                    break;
                case 12:
                    addUser();
                    break;
                case 13:
                    removeUser();
                    break;
                case 14:
                    searchMember();
                    break;
                case 15:
                    userService.UpdateMemberInfo();
                    break;
                case 16:
                    userService.listAllMembersByDateOfJoining().stream().forEach(System.out::println);
                    break;
                //case 17: findBook (case2)

                /*case 17:
                    addFine();
                    break;*/

                // changed 18 to 19
                case 18:
                    getUserInfo();
                    break;

                case 20:
                    // changed 20 to 21
                    reserveBook();
                    break;
                    //changed 21 to 22
                case 0:
                    exitSystem();
                    break;
            }
            System.out.println("Do you want to continue again (y/n)");
            ans = myScanner.next().charAt(0);

        } while (ans == 'y' || ans == 'Y');

    }


    private static void addBook() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter the book's data :");
        System.out.println("Title: ");
        String titelInput = scanner.nextLine();
        System.out.println("Author: ");
        String authorInput = scanner.nextLine();
        System.out.println("Publish year: ");
        int publishYearInput = scanner.nextInt();
        String typeBookInput = ScannerTools.typeBookScanner();
        bookService.addBook(titelInput, authorInput, publishYearInput, typeBookInput);
    }

    private static void removeBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1: Get book by title\n" +
                "2: Get book by ID");
        String input = scanner.nextLine();
        if (input.equals("1")) {
            System.out.println("Title: ");
            String titelInput = scanner.nextLine();
            bookService.removeBookByTitle(titelInput);
        } else if (input.equals("2")) {
            System.out.println("book id:");
            int bookIdInput = scanner.nextInt();
            bookService.removeBookByID(bookIdInput);
        } else {
            System.out.println("Invalid choice");
        }
    }

    private static void addUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter the user's data :");
        List<String> names = ScannerTools.namesScanner();
        System.out.println("Email: ");
        String EmailInput = scanner.nextLine();
        System.out.println("birth year: ");
        String birthDate = scanner.nextLine();
        System.out.println(userService.addUser(names.get(0), names.get(1), EmailInput, "1234", birthDate));
    }

    private static void removeUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1: Get User by name\n" +
                "2: GetUser by ID");
        String input = scanner.nextLine();
        if (input.equals("1")) {
            List<String> names = ScannerTools.namesScanner();
            userService.removerUserByName(names.get(0), names.get(1));
        } else if (input.equals("2")) {
            System.out.println("UserId:");
            int userId = scanner.nextInt();
            userService.removerUserByID(userId);
        } else {
            System.out.println("Invalid choice");
        }
    }

    private static void searchMember() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1: search member by last name\n" +
                "2: search member by first name\n" +
                "3: Search member by ID");
        String input = scanner.nextLine();
        switch (input) {
            case "1":
                System.out.println("last name: ");
                String lastNameInput = scanner.nextLine();
                System.out.println(userService.searchForMemberByLastName(lastNameInput));
                break;
            case "2":
                System.out.println("first name: ");
                String firstNameInput = scanner.nextLine();
                System.out.println(userService.searchForMembersByFirstName(firstNameInput));
                break;
            case "3":
                System.out.println("Member ID: ");
                int userId = scanner.nextInt();
                System.out.println(userService.searchForMemberbyIDnumber(userId).orElse(null));
                break;
            default:
                System.out.println("Invalid choice");
        }
    }


    // changed used ScannerTools.
    private static void returnBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1: return all books\n" +
                "2: return one boek");
        String input = scanner.nextLine();
        if (input.equals("1")) bookService.returnAllBooks();
        else if (input.equals("2")) {
            int bookIDNO = ScannerTools.enterBookId();
            int userId = ScannerTools.enterUserId();
            bookService.returnBookByBookIDNO(bookIDNO,userId);
        }

    }

    private static void getUserInfo() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1: Get User by name\n" +
                "2: GetUser by ID");
        String input = scanner.nextLine();
        if (input.equals("1")) {
            List<String> names = ScannerTools.namesScanner();
            System.out.println(userService.InfoUserByName(names.get(0), names.get(1)));
        } else if (input.equals("2")) {
            System.out.println("UserId:");
            int userId = scanner.nextInt();
            System.out.println(userService.InfoUserByID(userId));
        } else {
            System.out.println("slecht invoer");
        }
    }

    private static void reserveBook() {
        int userId = ScannerTools.enterUserId();
        int bookIDNO = ScannerTools.enterBookId();
        bookService.reserveBook(bookIDNO, userId);
    }

    private static void issueBook(){
        int userId = ScannerTools.enterUserId();
        int bookIDNO = ScannerTools.enterBookId();
        bookService.issueBook(bookIDNO, userId);

    }
    private static void borrowBook() {
        int userId = ScannerTools.enterUserId();
        int bookIDNO = ScannerTools.enterBookId();
        bookService.borrowBook(bookIDNO, userId);
    }

    private static void renewBook() {
        int userId = ScannerTools.enterUserId();
        int bookIDNO = ScannerTools.enterBookId();
        bookService.renewBook(bookIDNO, userId);
    }

    /*private static void addFine(){
        int userId = ScannerTools.enterUserId();
        int bookId = ScannerTools.enterBookId();
        LocalDate issueDate = book.getIssueDate();
        LocalDate dueDate = book.getDueDate();
        bookService.addFine(userId,issueDate,dueDate);
    }*/

    private static void exitSystem() {
        System.out.println("Thank you for visiting!");
        System.exit(0);
    }

}
