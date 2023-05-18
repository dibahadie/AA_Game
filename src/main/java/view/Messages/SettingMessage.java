package view.Messages;

public enum SettingMessage {
    SUCCESS(""),
    INVALID_BALL_NUMBER("invalid ball number");
    private String message;
    SettingMessage(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
