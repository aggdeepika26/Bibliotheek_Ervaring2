package be.intecbrussel.service;

import be.intecbrussel.model.user.Member;
import be.intecbrussel.model.user.User;
import be.intecbrussel.repository.BookRepository;
import be.intecbrussel.repository.UserRepository;

import java.util.*;

public class UserService {

    private UserRepository userRepository;

    private BookRepository bookRepository;

    public UserService(UserRepository userRepository, BookRepository bookRepository){
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public boolean addUser(String firstNameInput,String lastNameInput,String  emailInput,String password, String birthDate ){
        User user = new Member(firstNameInput, lastNameInput, emailInput, "1234", birthDate);
        return userRepository.addUser(user);
    }
    public void removerUserByName(String firstName, String lastName){
        try {
           User user = userRepository.getUserList().stream()
                    .filter(u -> u.getFirstName().equals(firstName))
                    .filter(u -> u.getLastName().equals(lastName))
                    .findFirst().get();
           userRepository.removerUser(user);
    } catch (Exception e) {
        System.out.println("Invalide name entry");
    }
    }

    public void removerUserByID(int userID){
        try {
            User user = userRepository.getUserList().stream()
                    .filter(u -> u.getUserId() == userID)
                    .findFirst().get();
            userRepository.removerUser(user);
        } catch (Exception e) {
            System.out.println("Invalide ID entry");
        }
    }


    public String InfoUserByName(String firstName, String lastName){

        String info = "";
        try {
            User user = userRepository.getUserList().stream()
                    .filter(u -> u.getFirstName().equals(firstName))
                    .filter(u -> u.getLastName().equals(lastName))
                    .findFirst().get();
            info = userRepository.getUserInfo(user);
        } catch (Exception e) {
            System.out.println("sorry name");
        }
        return info;
    }

    public String InfoUserByID(int userID){

        String info = "";
        try {
            User user = userRepository.getUserList().stream()
                    .filter(u -> u.getUserId() == userID)
                    .findFirst().get();
            info = userRepository.getUserInfo(user);

        } catch (Exception e) {
            System.out.println("sorry int");
        }
        return info;
    }

    public List<User> listAllMembersByDateOfJoining() {  //Jonathan

        List <User> sortedList= userRepository.getUserList();

        sortedList.sort(Comparator.comparing(user -> user.getDateOfJoiningLibrary()));

        return sortedList;
    }




    public void UpdateMemberInfo() {   //Jonathan


        Scanner myStringScanner = new Scanner(System.in);
        Scanner myIntScanner = new Scanner(System.in);

        List<User> userList = userRepository.getUserList();

        //entering the ID
        System.out.println("\n\nPlease enter the member ID: ");
        Integer memberID = (myIntScanner.hasNextInt())?myIntScanner.nextInt(): null;

        //checking there is only 1 member with the same ID
        Optional<List> allUsersWithSameIDOptional = Optional.of(userList.stream().filter(u -> u.getUserId() == memberID).toList()); //getting users with same ID

        List <User> allUsersWithSameID = allUsersWithSameIDOptional.orElse(null); //extracting optional


        if (allUsersWithSameID.size() > 1) {
            System.out.println("Error! Multiple users with the same ID!!!");
            return;
        }

        if (allUsersWithSameID.isEmpty()) {
            System.out.println("There is no member with ID number: " + memberID);
            return;
        }
        //certain that the memberID is unique, so now the information can be changed.
        User userNewWithOldID = allUsersWithSameID.get(0); //extracting the user object

        //Providing a menu-loop with options
        boolean continueLoop = true;

        while (continueLoop) {

            System.out.println("Current User information: "+userNewWithOldID); //providing an overview of the user info
            System.out.println("\nWhich information do you wish to change?");
            System.out.println("1. E-mail address\n2. Last name\n3.First Name\n4.Password\n5.Exit");

            int selection=myIntScanner.nextInt();

            switch (selection) {
                case 1:
                    System.out.println("Enter new E-mail address: \n");
                    //purge line buffer?
                    String email = myStringScanner.nextLine();
                    userNewWithOldID.setEmailId(email);
                    break;
                case 2:
                    System.out.println("Enter new last name: \n");
                    String name = myStringScanner.nextLine();
                    userNewWithOldID.setLastName(name);
                    break;
                case 3:
                    System.out.println("Enter new first name: \n");
                    String firstName = myStringScanner.nextLine();
                    userNewWithOldID.setFirstName(firstName);
                    break;
                case 4:
                    System.out.println("Enter new password: \n");
                    String password = myStringScanner.nextLine();
                    userNewWithOldID.setPassword(password);
                    break;
                case 5:
                    continueLoop=false; //indicates the end of the while-loop
                    break;

                default:
                    System.out.println("This option does not exist: try again.");
                    break;


            }

        } //end of while loop

        System.out.println("Is this correct?: Y/N \n" + userNewWithOldID);
        if (!myStringScanner.nextLine().toLowerCase().startsWith("y")) this.UpdateMemberInfo(); //if the librarian is not happy, the method is re-started.

        //admin is happy with information, now updating the user in the main list in UserRegistry with the new information
        removerUserByID(memberID);
        //userRepository.addUser(userNewWithOldID); // old version with ID number++
        userRepository.getUserList().add(userNewWithOldID);
    }

    public Optional<User> searchForMemberbyIDnumber(int searchID) {

        List <User> userList = userRepository.getUserList();

        Optional<User> userWithSameIDOptional = userList.stream()
                .filter(u -> u.getUserId() == searchID)
                .findFirst();

        if (userWithSameIDOptional.isEmpty()) {
            System.out.println("There is no member with ID number: " + searchID);
        }

        return userWithSameIDOptional;
    }

    public List <User> searchForMemberByLastName(String lastName) {

        List<User> userList = userRepository.getUserList();


        List<User> listWith1FamilyName = userList.stream().filter(user -> Objects.equals(user.getLastName(), lastName)).toList();

        if (listWith1FamilyName.isEmpty()) {
            System.out.println("No members with the family name  " + lastName + " exist!");
        }

        return Optional.of(listWith1FamilyName).orElse(null);

    }

    public List <User> searchForMembersByFirstName(String firstName) {

        List<User> userList = userRepository.getUserList();

        List<User> listWithFirstName = userList.stream().filter(user -> Objects.equals(user.getFirstName(), firstName)).toList();

        if (listWithFirstName.isEmpty()) {
            System.out.println("No members with the first name   " + firstName + " exist!");
        }

        return Optional.of(listWithFirstName).orElse(null);

    }

}
