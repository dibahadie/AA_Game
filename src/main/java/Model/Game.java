package Model;

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

    static HashMap<String, User> users;
    public boolean doesUserExist(String username){
        return users.containsKey(username);
    }
    public boolean isPasswordValid(String username, String password){
        User user = users.get(username);
        return user.isPasswordTrue(password);
    }

    public static void addUser(User user){
        users.put(user.getUsername(), user);
    }

    public static HashMap<String, User> getUsers() {
        return users;
    }
}
