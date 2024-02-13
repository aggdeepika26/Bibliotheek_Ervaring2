package be.intecbrussel.model.user;

import java.time.LocalDate;

public abstract class User {
    private int userId;
    private String firstName;
    private String lastName;
    private String emailId;
    private String password;
    private String birthYear;
    private static LocalDate dateOfJoiningLibrary;
    private double fineAmount = 0;

    public User() {
    }

    public User(String firstName, String lastName, String emailId, String password, String birthYear) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.password = password;
        this.birthYear = birthYear;
        dateOfJoiningLibrary = LocalDate.now();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public LocalDate getDateOfJoiningLibrary() {
        return dateOfJoiningLibrary;
    }

    public void setDateOfJoiningLibrary(LocalDate dateOfJoiningLibrary) {
        User.dateOfJoiningLibrary = dateOfJoiningLibrary;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(double fineAmount) {
        this.fineAmount = fineAmount;
    }


    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailId='" + emailId + '\'' +
                ", password='" + password + '\'' +
                ", birthYear='" + birthYear + '\'' +
                ", fineAmount=" + fineAmount +
                '}';
    }
}
