package Model;

import Controller.UserController.UserManager;

import java.util.ArrayList;
import java.util.HashMap;

public class AA {
    private static AA AA;
    private AA(){

    }
    public static AA getInstance(){
        if (AA == null) return new AA();
        else return AA;
    }

    private static HashMap<String, User> users = new HashMap<>();
    private static ArrayList<User> userRanking = new ArrayList<>();
    public boolean doesUserExist(String username){
        return users.containsKey(username);
    }
    public boolean isPasswordValid(String username, String password){
        User user = users.get(username);
        if (user == null) return false;
        return user.isPasswordTrue(password);
    }

    public void addUser(User user){
        users.put(user.getUsername(), user);
        UserManager.updateUsers();
    }

    public void removeUser(String username){
        users.remove(username);
        UserManager.updateUsers();
    }

    public User getUser(String username){
        return users.get(username);
    }

    public static HashMap<String, User> getUsers() {
        return users;
    }

    public static void updateRanking(){
        userRanking.clear();
        userRanking.addAll(users.values());
    }
}
