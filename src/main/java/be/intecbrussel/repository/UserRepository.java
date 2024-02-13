package be.intecbrussel.repository;

import be.intecbrussel.model.user.Admin;
import be.intecbrussel.model.user.Member;
import be.intecbrussel.model.user.User;
import be.intecbrussel.service.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserRepository {

    private int userID;
    private List<User> userList  = new ArrayList<>();

    {
        addUser(new Admin("Admin", "Admin", "Admin@Alexandria.org", "11","2000"));
        addUser(new Member("Deepika", "Aggarwala", "Deepika@Alexandria.org", "22", "2000"));
        addUser(new Member("Jonathan", "Deroo", "Jonathan@Alexandria.org", "33","2000"));
        addUser(new Member("Maxime", "Franquet", "Maxime@Alexandria.org", "44", "2000"));
        addUser(new Member("Mausam", "Tiwari", "Mausam@Alexandria.org", "55", "2000"));
        addUser(new Member("Hilal", "H", "Mausam@Alexandria.org", "55", "2000"));
    }

    public List<User> getUserList(){
        return userList;
    }

    public boolean addUser(User user){
        if (getUserList().stream()
                .anyMatch(u -> u.getFirstName()
                .equals(user.getFirstName()))){
            return false;
        } else {
            user.setUserId(userID);
            getUserList().add(user);
            userID++;
            return true;
        }
    }

    public boolean removerUser(User user){
        if (!getUserList().contains(user)){
            return false;
        }
        getUserList().remove(user);
        return true;
    }

    public String getUserInfo(User user){

        return getUserList().stream()
                .filter(u -> u.equals(user))
                .map(u -> u.toString())
                .collect(Collectors.joining());
    }
}
