package Model;

import view.UserMenu.ProfileMenu;

import java.util.Random;

public enum DefaultAvatar {
    AVATAR_1("/pictures/avatars/avatar1.jpg"),
    AVATAR_2("/pictures/avatars/avatar2.jpg"),
    AVATAR_3("/pictures/avatars/avatar3.jpg"),
    AVATAR_4("/pictures/avatars/avatar4.jpg"),
    AVATAR_5("/pictures/avatars/avatar5.jpg"),
    AVATAR_6("/pictures/avatars/avatar6.jpg"),
    AVATAR_7("/pictures/avatars/avatar7.jpg"),
    AVATAR_8("/pictures/avatars/avatar8.jpg"),;
    private String path;
    DefaultAvatar(String path){
        this.path = path;
    }
    public static String getRandomAvatar(){
        Random random = new Random();
        int number = random.nextInt(8);
        return ProfileMenu.class.getResource(DefaultAvatar.values()[number].path).toExternalForm();
    }
}
