import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * Program class for an SRPN calculator. Current it outputs "0" for every "=" sign.
 */

public class SRPN {

    //Fields
    char[] charArray_SRPN_operator_symbols = { '.', ' ', 'd', '+', '-', '*' };
    char char_d_symbol = 'd';
    char char_plus_symbol = '+';
    char char_minus_symbol = '-';
    char char_asterisk_symbol = '*';
    char char_slash_symbol = '/';
    //ArrayList<String> arraylistSRPN_operator_symbols = new ArrayList<>();

    //Constructor
    Stack<String> stackSRPN = new Stack<>(); //creation of empty stack that stores inputs of string-type

    //Method that puts together all SRPN operator symbols into an ArrayList.
    //Called from the Main method and executed only once.
    //Returns a boolean for exception control
//acacac


    //boolean isInt = isInt(s);

    //processCommand is the main method within the SRPN class. It's responsible for processing the "s" string pushed
    // from the buffer reader object and call other appropriate methods to
    public void processCommand(String s) {

        //Define ArrayList to store the split input string into processable string segments
        ArrayList<String> arrayListSplitInputString = new ArrayList<>();

        //Call the doggies
        //boolean isInt = isInt(s);
        //boolean isLong = isLong(s);
        //boolean isDouble = isDouble(s);
        splitStringIntoArrayList(s);

        //Lets print arrayListSplitInputString and see what we've got over thereeee
        System.out.println("arrayListSplitInputString=" + arrayListSplitInputString.toString());

    }

    //Method that splits an input string into string segments and stores the resulting string segments into the
    // arraylistSRPN_operator_symbols ArrayList.
    //The input string is split at the position of the relevant SRPN operators.
    public void splitStringIntoArrayList(String s) {
        int intStringLength = s.length();
        //String[] parts = new String[2];

         for (int i=0; i<intStringLength;i++){ //iterates across all characters of input string

             for (int j = 0; j< charArray_SRPN_operator_symbols.length; j++){ //iterates across all characters of the chararray_SRPN_operator_symbols array

                 if (s.charAt(i) == charArray_SRPN_operator_symbols[j]){
                     System.out.println("Coincidence found between: s.charAt(" + i + ")='" + s.charAt(i) + "' and chararray_SRPN_operator_symbols[" + j + "] = '" + charArray_SRPN_operator_symbols[j] + "'.");
                     //String[] parts = new String[];
                     StringBuilder str = new StringBuilder();           //use of StringBuilder class as an alternative
                     str.append(charArray_SRPN_operator_symbols[j]);    //to String class to facilitate
                     String[] parts = s.split(Pattern.quote(str.toString())); //String[] parts = new String[2];
                     //parts[] =  //toString().split("-");
                     //System.out.println("Parts[0]='" + parts[0] + "'; Parts[1]='" + parts[1] + "'; Parts[2]='" + parts[2] + "'.");
                     System.out.println("Parts[]=" + Arrays.toString(parts));
                     //System.out.println("Length of Parts[]=" + parts.length);

                     //System.out.println("charArray_SRPN_operator_symbols[" + j + "]=" + charArray_SRPN_operator_symbols[j]);

                     addStringIntoArrayList(Arrays.toString(parts));


                 } else {
                     //System.out.println("No coincidence found between: s.charAt(" + i + ")='" + s.charAt(i) + "' and chararray_SRPN_operator_symbols[" + j + "] = '" + chararray_SRPN_operator_symbols[j] + "'.");
                     //System.out.println("If's Else clause");
                 }

             }
         }

         //}
    }

    //Method that adds an string element into an ArrayList<String>
    public void addStringIntoArrayList(String s) {
        arrayListSplitInputString.add(s);
        }

    //Method that verifies if a string argument is an Int (32-bit) and returns a boolean (True if integer)
    public boolean isInt(String s) {
        try {
            int testInt = Integer.parseInt(s);

            System.out.println("isInt = True"); //commented in final version
            System.out.println(testInt); //commented in final version
            return true;
        } catch (NumberFormatException e) {
            //throw new RuntimeException(e); //commented in final version
            System.out.println("isInt = False"); //commented in final version
            return false;
        }
    }

    //Method that verifies if a string argument is a Long integer (64-bit) and returns a boolean (True if Long)
    public boolean isLong(String s) {
        try {
            long testLong = Long.parseLong(s);

            System.out.println("isLong = True"); //commented in final version
            System.out.println(testLong); //commented in final version
            return true;
        } catch (NumberFormatException e) {
            //throw new RuntimeException(e); //commented in final version
            System.out.println("isLong = False"); //commented in final version
            return false;
        }
    }

    //Method that verifies if a string argument is a Double (64-bit) and returns a boolean (True if Double or Float)
    //Verification of Floats (32-bit) is covered by this method as no distinction between Float and Double data types
    //is necessary to implement the functionality of the legacy SRPN calculator.
    public boolean isDouble(String s) {
        try {
            double testDouble = Double.parseDouble(s);
            System.out.println("isDouble = True"); //commented in final version
            System.out.println(testDouble); //commented in final version
            return true;
        } catch (NumberFormatException e) {
            //throw new RuntimeException(e); //commented in final version
            System.out.println("isDouble = False"); //commented in final version
            return false;
        }
    }




}