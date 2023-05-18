package Utils;

public class Validation {
    public static boolean isNumeric(String string){
        try {
            int Value = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
