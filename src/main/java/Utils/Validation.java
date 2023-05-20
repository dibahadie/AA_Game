package Utils;

import Model.AA;
import view.Messages.LoginMessages;
import view.Messages.SignupMessage;

public class Validation {
    public static boolean isNumeric(String string){
        try {
            int Value = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static LoginMessages usernameValidationLogin(String username){
        if (username.equals("")) return LoginMessages.ENTER_USERNAME;
        if (!AA.getInstance().doesUserExist(username)) return LoginMessages.USER_NOT_FOUND;
        return LoginMessages.SUCCESS;
    }
    public static LoginMessages passwordValidationLogin(String username, String password){
        if (password.equals("")) return LoginMessages.ENTER_PASSWORD;
        if (!AA.getInstance().isPasswordValid(username, password)) return LoginMessages.PASSWORD_INCORRECT;
        return LoginMessages.SUCCESS;
    }

    public static SignupMessage usernameValidation(String username){
        if (!username.matches("\\S+")) return SignupMessage.INVALID_CHARACTER;
        if (AA.getInstance().doesUserExist(username)) return SignupMessage.TAKEN_USERNAME;
        return SignupMessage.SUCCESS;
    }
    public static SignupMessage passwordValidation(String username, String password){
        if (!password.matches(".*[a-z].*")) return SignupMessage.NO_LOWERCASE;
        if (!password.matches(".*[A-Z].*")) return SignupMessage.NO_UPPERCASE;
        if (!password.matches(".*[0-9].*")) return SignupMessage.NO_NUMBER;
        if (!password.matches(".*[!@#$%^&*].*")) return SignupMessage.NO_SIGN;
        if (!password.matches(".{4}.*")) return SignupMessage.SHORT_PASSWORD;
        return SignupMessage.SUCCESS;
    }

    public static SignupMessage passwordConfirmValidation(String pass, String confirm){
        if (pass.equals(confirm)) return SignupMessage.SUCCESS;
        else return SignupMessage.CONFIRM_NO_MATCH;
    }
}
