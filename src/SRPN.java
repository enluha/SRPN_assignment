import java.util.Stack;

/**
 * Program class for an SRPN calculator. Current it outputs "0" for every "=" sign.
 */

public class SRPN {

    //Fields


    //Constructor
    Stack<String> stackSRPN = new Stack<>(); //creation of empty stack that stores inputs of string-type




    //boolean isInt = isInt(s);

    //processCommand is the main method within the SRPN class. It's responsible for processing the "s" string pushed
    // from the buffer reader object and call other appropriate methods to
    public void processCommand(String s) {

        //Call the doggies
        boolean isInt = isInt(s);
        boolean isLong = isLong(s);
        boolean isDouble = isDouble(s);


        //If an "=" is received, print zero always
        if (s.charAt(s.length()-1) == '=') {
            System.out.println("Equal, OK");

        }
    }

    //Method that verifies if a string argument is an int (32-bit) and returns a boolean (True if integer)
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