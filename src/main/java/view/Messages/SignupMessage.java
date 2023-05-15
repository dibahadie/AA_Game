package view.Messages;

public enum SignupMessage {
    SUCCESS("");
    String message;
    SignupMessage(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
