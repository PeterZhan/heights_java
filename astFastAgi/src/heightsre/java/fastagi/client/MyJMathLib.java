package heightsre.java.fastagi.client;

// this class is to test the input string is numeric 
public class MyJMathLib {


    /**
      * support Numeric format:<br>
      * "33" "+33" "033.30" "-.33" ".33" " 33." " 000.000 "
      * @param str String
      * @return boolean
      */
    public static boolean isNumeric(String str) {
        int begin = 0;
        boolean once = true;
        if ((str == null) || (str.trim().equals(""))) {
            return false;
        }
        str = str.trim();
        if ((str.startsWith("+")) || (str.startsWith("-"))) {
            if (str.length() == 1) {
                // "+" "-"
                return false;
            }
            begin = 1;
        }
        for (int i = begin; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                if (str.charAt(i) == '.' && once) {
                    // '.' can only once
                    once = false;
                }
                else {
                    return false;
                }
            }
        }
        if (str.length() == (begin + 1) && !once) {
            // "." "+." "-."
            return false;
        }
        return true;
    }

    /**
      * support Integer format:<br>
      * "33" "003300" "+33" " -0000 "
      * @param str String
      * @return boolean
      */
    public static boolean isInteger(String str) {
        int begin = 0;
        if ((str == null) || (str.trim().equals(""))) {
            return false;
        }
        str = str.trim();
        if ((str.startsWith("+")) || (str.startsWith("-"))) {
            if (str.length() == 1) {
                // "+" "-"
                return false;
            }
            begin = 1;
        }
        for (int i = begin; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    /**
      * use Exception
      * support Numeric format:<br>
      * "33" "+33" "033.30" "-.33" ".33" " 33." " 000.000 "
      * @param str String
      * @return boolean
      */
    public static boolean isNumericEx(String str) {
        try {
            Double.parseDouble(str);
            return true;
        }
        catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
      * use Exception
      * support less than 11 digits(<11)<br>
      * support Integer format:<br>
      * "33" "003300" "+33" " -0000 " "+ 000"
      * @param str String
      * @return boolean
      */
    public static boolean isIntegerEx(String str) {
        str = str.trim();
        try {
            Integer.parseInt(str);
            return true;
        }
        catch (NumberFormatException ex) {
            if (str.startsWith("+")) {
                return isIntegerEx(str.substring(1));
            }
            return false;
        }
    }
}
