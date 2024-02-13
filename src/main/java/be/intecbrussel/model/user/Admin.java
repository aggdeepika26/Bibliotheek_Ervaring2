package be.intecbrussel.model.user;

public class Admin extends User{

    public Admin() {
    }

    public Admin(String firstName, String lastName, String emailId, String password, String birthYear) {
        super(firstName, lastName, emailId, password, birthYear);
    }
}
