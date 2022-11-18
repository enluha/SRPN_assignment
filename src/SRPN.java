import java.util.Stack;

/**
 * Program class for an SRPN calculator. Current it outputs "0" for every "=" sign.
 */

public class SRPN {

    //Fields


    //Constructor
    Stack<String> stackSRPN = new Stack<>(); //creation of empty stack that stores inputs of string-type


    //SRPN main method responsible for processing the "s" string pushed from the buffer reader object
    public void processCommand(String s) {
        //If an "=" is received, print zero always
        if (s.charAt(s.length()-1) == '=') {
            System.out.println("0");
        }
    }

}