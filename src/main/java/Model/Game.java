package Model;

import Controller.UserController.UserManager;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {
    private static Game game;
    private Game(){

    }
    public static Game getInstance(){
        if (game == null) return new Game();
        else return game;
    }

    static HashMap<String, User> users = new HashMap<>();
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
}
