package Model;

import Controller.UserController.UserManager;

import java.util.ArrayList;
import java.util.Comparator;
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
        updateRanking();
    }

    public void removeUser(String username){
        users.remove(username);
        UserManager.updateUsers();
        updateRanking();
    }

    public void changeUsername(String old, String newUser){
        User user = users.get(old);
        users.remove(old);
        users.put(newUser, user);
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
        userRanking.remove(users.get("default"));
        userRanking.sort(Comparator.comparingInt(User :: getHighScore).thenComparing(User :: getRecordTime));
    }

    public int getUserRank(User user){
        return userRanking.indexOf(user) + 1;
    }

    public static ArrayList<User> getUserRanking() {
        return userRanking;
    }
}
