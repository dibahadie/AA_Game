package Model;

import Controller.UserController.UserManager;

public class User {
    private String username;
    private String password;
    private UserSetting setting;
    private String avatarPath;
    public User(String username, String password){
        this.username = username;
        this.password = password;
        setting = new UserSetting();
        avatarPath = DefaultAvatar.getRandomAvatar();
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
        UserManager.updateUsers();
    }

    public void setUsername(String username) {
        this.username = username;
        UserManager.updateUsers();
    }

    public boolean isPasswordTrue(String givenPassword){
        return password.equals(givenPassword);
    }

    public UserSetting getSetting() {
        return setting;
    }

    public void setSetting(UserSetting setting) {
        this.setting = setting;
        UserManager.updateUsers();
    }

    public String getPassword() {
        return password;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }
}
