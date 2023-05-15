package view.Messages;

public enum SignupMessage {
    SUCCESS(""),
    INVALID_CHARACTER("Username should consist of letters a-zA-Z0-9_ only"),
    SHORT_PASSWORD("Password is too short"),
    TAKEN_USERNAME("This username is already taken"),
    NO_NUMBER("Password should have at least one number"),
    NO_UPPERCASE("Password should have at least one uppercase letter"),
    NO_LOWERCASE("Password should have at least one lowercase letter"),
    NO_SIGN("Password should have at least one of the following : !@#$%^&*"),
    CONFIRM_NO_MATCH("Password confirmation doesn't match");
    String message;
    SignupMessage(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
