
/**
 * Program class for an SRPN calculator. Current it outputs "0" for every "=" sign.
 */

public class SRPN {

    public void processCommand(String s) {
        //If an "=" is received, print zero always
        if (s.charAt(s.length()-1) == '=') {
            System.out.println("0");
        }
    }

}