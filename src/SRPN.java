import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * Program class for an SRPN calculator. Current it outputs "0" for every "="
 * sign.
 */

public class SRPN {

    //Fields, parameters
    long intMaxSaturationVal = 2147483647;
    long intMinSaturationVal = -2147483648;
    char[] charArraySrpnOperatorSymbols = { '.', ' ', 'd', '+', '-', '*', '/', '%', '=', '^', '#', 'r' };
    char[] charArrayArithmeticOperatorSymbols = { '+', '-', '*', '/', '%', '^' };
    char charPeriodSymbol = '.';
    char charSpaceSymbol = ' ';
    char charDeeSymbol = 'd';
    char charPlusSymbol = '+';
    char charMinusSymbol = '-';
    char charAsteriskSymbol = '*';
    char charSlashSymbol = '/';
    char charModulusSymbol = '%';
    char charEqualSymbol = '=';
    char charCircumflexSymbol = '^';
    char charHashSymbol = '#';
    char charArSymbol = 'r';
    String strStackOverflow = "";
    String strStackUnderflow = "";
    String strNegativePower = "";

    int intMaxStackSize = 23;  //controls max. size of stack, which is 23 for the legacy SRPN calculator

    // Define ArrayList to store the split input string into processable string
    // segments
    ArrayList<String> arrayListSplitInputString = new ArrayList<>();
    int intArrayListSize;  //number of components in arrayListSplitInputString ArrayList

    // Constructor?
    Stack<String> stackSRPN = new Stack<>(); // creation of empty stack that stores inputs of string-type

    // - processCommand is the main method within the SRPN class. It's responsible for
    //   processing the input String s pushed from the Main method by calling the rest of
    //   methods withing this class making up the SRPN logic.
    // - It prints out the output from the SRPN logic when applicable.
    public void processCommand(String s) {

        //Call to method that splits the input String s into segments containing chain of
        //digits (numbers) & non-numerical characters and stores them in an Arraylist.
        splitStringIntoArrayList(s);

        //Lets print arrayListSplitInputString and see what we've got over theree - FOR TESTING ONLY
        System.out.println("arrayListSplitInputString=" + arrayListSplitInputString.toString());

        //Call to method that process each component stored in the ArrayList
        //(arrayListSplitInputString) in accordance with the intended SRPN logic.
        intArrayListSize = arrayListSplitInputString.size();
        System.out.println("ArrayListSize = " + intArrayListSize);

        int i = 0;//for (int i = 0; i < intArrayListSize; i++) {
            processArrayList(i, arrayListSplitInputString.get(i));
        //}

        //Empty the contents of arrayListSplitInputString after each call to this
        //method (processCommand) before it is called again from the Main method.
        clearArrayList(arrayListSplitInputString);
        System.out.println("arrayListSplitInputString=" + arrayListSplitInputString.toString());

    }

    //- processArrayList is the method that implements the SRPN logic including arithmetic
    //  operations, Stack functionality, and handling of exceptions.
    //- It pulls the array components stored in the ArrayList input (arrayListSplitInputString)
    //  one at a time, classifies them as integers, operator symbols and non-operator symbols,
    //  and process them in accordance with the intended legacy SRPN logic.
    public void processArrayList(int i, String s){ //public void processArrayList(ArrayList ArrL){
        //Parameters
        String previousArrayComponent = "";  //empty by default
        String nextArrayComponent = "";  //empty by default
        String strArithmeticOperatorSymbols = new String(charArrayArithmeticOperatorSymbols);
        String strNonNumericSymbols = new String(charArraySrpnOperatorSymbols);

        //Get previous ArrayList component
        if (i != 0) {  //only if s String is not the first component of ArrayList
            previousArrayComponent = arrayListSplitInputString.get(i-1);
        }

        //Get next ArrayList component
        if (i+1 != intArrayListSize) {  //only if s String is not the last component of ArrayList
            nextArrayComponent = arrayListSplitInputString.get(i+1);
        }

        //DELETEEEEEE
        System.out.println("currentArrayComponent = " + s);
        System.out.println("previousArrayComponent = " + previousArrayComponent);
        System.out.println("nextArrayComponent = " + nextArrayComponent);
        //DELETEEEEEE

        //CASE 0: input String s is a number (it's formed by digit characters).
        //Number is converted to a Long integer processed as follows:
        // - if its the first component or of the ArrayList or is preceded by a space
        //   (" ") then push integer into the stack.
        // - if the next component is an arithmetic operation symbol, the do nothing.
        // - perform stack overflow check before pushing integer into stack.
        // - perform saturation check and process integer accordingly with max/min
        //   Int limits.
        if (isLong(s) && ( intArrayListSize==1 || !(strArithmeticOperatorSymbols.contains(nextArrayComponent)))) {  //s is the single component in the array of s preceded by a non-arithmetic
            if (previousArrayComponent == asdfghi)

            //System.out.println("--------------------------->NUMBER TO PUSH = " + s);
        } else {  //s component is followed by an arithmetic operation symbol
            //IMPLEMENT LOGIC: Evaluate a math expression given in string form using
            return;  //do nothing FOR NOW
        }
        return;

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

        //Parameters
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

    // Method that verifies if there is stack overflow (true if stack overflow)
    public boolean stackOverflow(Stack inputStack) {
        if (inputStack.size() >= intMaxStackSize) {
            return true;
        } else {
            return false;
        }
    }

    // Method that verifies if there is stack underflow (true if stack underflow)
    public boolean stackUnderflow(Stack inputStack) {
        if (inputStack.size() <= 1) {
            return true;
        } else {
            return false;
        }
    }

    // Method that process a signed numeric string argument by:
    //  - converting it into a Long integer (64-bit).
    //  - returning a signed Long integer of a max/min value in accordance
    //  - with Integer (32-bit) saturation limits.
    public Long processSaturation(String s) {
        long testLong = Long.parseLong(s);  //convert numeric string into Long integer

        if (testLong<0) {
            testLong = Math.max(testLong,intMinSaturationVal);
        } else {
            testLong = Math.min(testLong,intMaxSaturationVal);
        }
        return testLong;
    }

}