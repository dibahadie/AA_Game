package Controller.UserController;

import Model.Game;
import Model.User;
import Settings.Paths;
import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Field;
import java.util.HashMap;

public class UserManager {


    public static void updateUsers() {
        // TODO : code passwords
        Gson gson = new Gson();
        JsonArray jsonArray = new JsonArray();
        JsonObject jsonObject = new JsonObject();
        for (User user : Game.getUsers().values()){
            jsonArray.add(gson.toJsonTree(user).getAsJsonObject());
        }
        jsonObject.add("users", jsonArray);
        try {
            FileWriter fileWriter = new FileWriter(Paths.USER_DATABASE_PATH);
            fileWriter.write(gson.toJson(jsonObject));
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void load() {
        initializeResources();
        Reader reader;
        try {
            reader = new FileReader(Paths.USER_DATABASE_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
        if (jsonObject == null)
            return;
        JsonArray usersArray = jsonObject.getAsJsonArray("users");
        for (JsonElement element : usersArray)
            Game.getInstance().addUser(gson.fromJson(element, User.class));
        createDefaultUser();
    }

    private static void createDefaultUser(){
        if (Game.getInstance().getUser("default") == null){
            User user = new User("default", "dafault");
            Game.getInstance().addUser(user);
        }
    }


    private static void initializeResources() {
        File file = new File(Paths.RESOURCE_DIRECTORY);
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            new File(Paths.USER_DATABASE_PATH).createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
