import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * Program class for an SRPN calculator. Current it outputs "0" for every "="
 * sign.
 */

public class SRPN {

    // Fields
    char[] charArray_SRPN_operator_symbols = { '.', ' ', 'd', '+', '-', '*', '/', '%', '^', '#' };
    char char_period_symbol = '.';
    char char_space_symbol = ' ';
    char char_d_symbol = 'd';
    char char_plus_symbol = '+';
    char char_minus_symbol = '-';
    char char_asterisk_symbol = '*';
    char char_slash_symbol = '/';
    char char_modulus_symbol = '%';
    char char_circumflex_symbol = '^';
    char char_hash_symbol = '#';

    // Define ArrayList to store the split input string into processable string
    // segments
    ArrayList<String> arrayListSplitInputString = new ArrayList<>();

    // Constructor?
    Stack<String> stackSRPN = new Stack<>(); // creation of empty stack that stores inputs of string-type

    // - processCommand is the main method within the SRPN class. It's responsible for
    //   processing the input String s pushed from the Main method by calling the rest of
    //   methods withing this class making up the SRPN logic.
    // - It prints out the output from the SRPN logic when applicable.
    public void processCommand(String s) {

        //Call to method splits the input String s into segments containing chain of
        //digits (numbers) and non-numerical
        splitStringIntoArrayList(s);

        //Lets print arrayListSplitInputString and see what we've got over theree - FOR TESTING ONLY
        System.out.println("arrayListSplitInputString=" + arrayListSplitInputString.toString());

        //
        processArrayList(arrayListSplitInputString);

        //Empty the contents of arrayListSplitInputString after each call to this
        //method (processCommand) before it is called again from the Main method.
        clearArrayList(arrayListSplitInputString);
        System.out.println("arrayListSplitInputString=" + arrayListSplitInputString.toString());

    }

    //processArrayList is the method that implements the SRPN logic including arithmetic
    //operations, Stack functionality, and handling of exceptions.
    public void processArrayList(ArrayList ArrL){

    }

    // - splitStringIntoArrayList is a method that is called from this SRPN class and
    //   from itself (recursive calls).
    // - For calls where the input String s has non-numeric characters,it splits the
    //   input string into segments at the position each non-numeric character. This
    //   leaves numeric characters in between (if any) grouped together blocks
    //   (integers of one or more digits).
    // - Split parts are inserted / stored in the correct sequence (order of appearance) into
    //   an ArrayList<String> called arraylistSRPN_operator_symbols.
    public void splitStringIntoArrayList(String s) {

        // Parameters
        int intStringLength = s.length(); // retrieve length of input String s
        StringBuilder str = new StringBuilder(1); // use of the StringBuilder class to facilitate the append() function

        // CASE 0: input String s is fully numeric, either positive or negative, one or more characters long
        if (isInt(s)) {
            int sInt = Integer.parseInt(s);
            if (sInt < 0) { // input String is a negative number
                str.append(s.charAt(0)); // insert first char (minus symbol) into str String
                addStringIntoArrayList(str.toString(), arrayListSplitInputString); // add the minus symbol into
                // arrayListSplitInputString
                addStringIntoArrayList(s.substring(1), arrayListSplitInputString); // add full input String s into
                // arrayListSplitInputString
                return;
            } else { // input String is a positive number
                addStringIntoArrayList(s, arrayListSplitInputString); // add full input String s into arrayListSplitInputString
                return;
            }
        }

        // CASE 1: input String s is formed by a single char, either numeric or non-numeric
        if (intStringLength == 1) {
            str.append(s.charAt(0)); // insert test char at i-iteration into str String
            addStringIntoArrayList(str.toString(), arrayListSplitInputString); // add single char into
            // arrayListSplitInputString
            // return;
            str.deleteCharAt(0); // clear str
            System.out.println("str value at end of loop =" + str);

        // CASE 2: input String s is formed by more than a single char and contains one or more non-numeric characters
        } else {

            for (int i = 0; i < intStringLength; i++) { // iterates across all characters of input String s

                // str String is the string formed by a single char being tested in the loop
                if (!str.isEmpty()) {
                    str.deleteCharAt(0); // remove any residual contents of str String from previous iterations
                }

                str.append(s.charAt(i)); // insert test char at i-iteration into str String

                if (!isInt(str.toString())) { // if non-numeric char is found at i-iteration

                    String[] parts = s.split(Pattern.quote(str.toString()), 2); // split input String s into two parts at the location of non-numeric char found

                    if (i == 0) { // if non-numeric char is found at first position of input string s
                        addStringIntoArrayList(str.toString(), arrayListSplitInputString); // add non-numeric char into arrayListSplitInputString

                        splitStringIntoArrayList(parts[1]); // recursive call to splitStringIntoArrayList to split the right-side part further
                        break; // end loop after recursive calls

                    } else if (i > 0 && i < (intStringLength - 1)) { // if non-numeric char is found at a position other than the
                        // first one (index > 0)
                        addStringIntoArrayList(parts[0], arrayListSplitInputString); // add left-side part of the split into
                        // arrayListSplitInputString
                        addStringIntoArrayList(str.toString(), arrayListSplitInputString); // add non-numeric char
                        // addStringIntoArrayList(parts[1],arrayListSplitInputString); //add right-side
                        // part of the split

                        splitStringIntoArrayList(parts[1]); // recursive call to splitStringIntoArrayList to split the right-side
                        // part further

                        break; // end loop after recursive calls

                    } else { // if non-numeric char is found at the last position of the string
                        addStringIntoArrayList(parts[0], arrayListSplitInputString); // add right-side part of the split into arrayListSplitInputString
                        addStringIntoArrayList(str.toString(), arrayListSplitInputString); // add non-numeric char
                    }
                }
            }
        }
    }

    // Method that adds a string element into an ArrayList<String>
    public void addStringIntoArrayList(String s, ArrayList ArrL) {
        ArrL.add(s);
    }

    // Method that removes all the existing elements from an ArrayList<String>
    public void clearArrayList(ArrayList ArrL) {
        ArrL.clear();
    }

    // Method that verifies if a string argument is an Int (32-bit) and returns a
    // boolean (True if integer)
    public boolean isInt(String s) {
        try {
            int testInt = Integer.parseInt(s);

            // System.out.println("isInt = True"); //commented in final version
            // System.out.println(testInt); //commented in final version
            return true;
        } catch (NumberFormatException e) {
            // throw new RuntimeException(e); //commented in final version
            // System.out.println("isInt = False"); //commented in final version
            return false;
        }
    }

    // Method that verifies if a string argument is a Long integer (64-bit)
    // (positive or negative) and returns a boolean (True if Long)
    public boolean isLong(String s) {
        try {
            long testLong = Long.parseLong(s);

            System.out.println("isLong = True"); // commented in final version
            System.out.println(testLong); // commented in final version
            return true;
        } catch (NumberFormatException e) {
            // throw new RuntimeException(e); //commented in final version
            System.out.println("isLong = False"); // commented in final version
            return false;
        }
    }

    // Method that verifies if a string argument is a Double (64-bit) and returns a
    // boolean (True if Double or Float).
    // Verification of Floats (32-bit) is covered by this method as no distinction
    // between Float and Double data types is necessary to implement the
    // functionality of the legacy SRPN calculator.
    public boolean isDouble(String s) {
        try {
            double testDouble = Double.parseDouble(s);
            System.out.println("isDouble = True"); // commented in final version
            System.out.println(testDouble); // commented in final version
            return true;
        } catch (NumberFormatException e) {
            // throw new RuntimeException(e); //commented in final version
            System.out.println("isDouble = False"); // commented in final version
            return false;
        }
    }




}