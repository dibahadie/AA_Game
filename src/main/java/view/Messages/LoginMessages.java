package view.Messages;

public enum LoginMessages {
    SUCCESS(""),
    USER_NOT_FOUND("No such username exists"),
    ENTER_USERNAME("Please enter your username"),
    ENTER_PASSWORD("Please enter your password"),
    PASSWORD_INCORRECT("Incorrect password");
    private String message;
    LoginMessages(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
