package be.intecbrussel.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ScannerTools {

    private static final Scanner scanner = new Scanner(System.in);

    public static String enterString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public static int enterInt(String prompt) {
        System.out.print(prompt);
        return scanner.nextInt();
    }

    public static String enterFirstName() {
        return enterString("Enter first name: ");
    }

    public static String enterLastName() {
        return enterString("Enter last name: ");
    }

    public static String enterEmail() {
        return enterString("Enter email: ");
    }

    public static String enterPassword() {
        return enterString("Enter password: ");
    }

    public static String enterUsername() {
        return enterString("Enter username: ");
    }

    public static int enterUserId() {
        return enterInt("Enter userId: ");
    }

    public static String enterBookTitle() {
        return enterString("Enter book title: ");
    }

    public static int enterBookId() {
        return enterInt("Enter bookId: ");
    }

    public static String enterAuthor() {
        return enterString("Enter author: ");
    }

    public static String enterCategory() {
        return enterString("Enter category: ");
    }

    public static int enterPublishedYear() {
        return enterInt("Enter book ID: ");
    }




    public static List<String> namesScanner(){
        Scanner scanner = new Scanner(System.in);
        List<String> names = new ArrayList<>();
        System.out.println("First name: ");
        names.add(0,scanner.nextLine());
        System.out.println("Last name: ");
        names.add(1,scanner.nextLine());
        return names;
    }

    public static String typeBookScanner() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type of book:\n" +
                "1: Children book\n" +
                "2: Fiction book\n" +
                "3: Non fiction book\n" +
                "4: Other book ");
        String input = scanner.nextLine();
        switch (input) {
            case "1" -> input = "children";
            case "2" -> input = "fiction";
            case "3" -> input = "nonFiction";
            default -> input = "other";
        }
        return input;
    }


}
