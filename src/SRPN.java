import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * Program class for an SRPN calculator. Current it outputs "0" for every "="
 * sign.
 */

public class SRPN {

    //Fields, parameters
    int intMaxStackSize = 23;  //controls max. size of stack, which is 23 for the legacy SRPN calculator
    long intMaxSaturationVal = 2147483647;
    long intMinSaturationVal = -2147483648;
    char[] charArraySrpnOperatorSymbols = { ' ', 'd', '+', '-', '*', '/', '%', '=', '^', '#', 'r' };
    char[] charArrayArithmeticOperatorSymbols = { '+', '-', '*', '/', '%', '^' };
    String strArithmeticOperatorSymbols = new String(charArrayArithmeticOperatorSymbols);
    String strSrpnOperatorSymbols = new String(charArraySrpnOperatorSymbols);
    String strPeriodSymbol = ".";
    String strSpaceSymbol = " ";
    String strDeeSymbol = "d";
    String strPlusSymbol = "+";
    String strMinusSymbol = "-";
    String strAsteriskSymbol = "*";
    String strSlashSymbol = "/";
    String strModulusSymbol = "%";
    String strEqualSymbol = "=";
    String strCircumflexSymbol = "^";
    String strHashSymbol = "#";
    String strArSymbol = "r";
    String strStackOverflow = "Stack overflow.";
    String strStackUnderflow = "Stack underflow.";
    String strNegativePower = "Negative power.";
    String strStackEmpty = "Stack empty.";


    //CONSTRUCTORS
    //Method that adds a string element into an ArrayList<String>*/
    public void addStringIntoArrayList(String s, ArrayList ArrL) {
        ArrL.add(s);
    }

    //Method that removes all the existing elements from an ArrayList<String>
    public void clearArrayList(ArrayList ArrL) {
        ArrL.clear();
    }


    //Define ArrayList to store the split input string into processable string segments
    ArrayList<String> arrayListSplitInputString = new ArrayList<>();
    int intArrayListSize;  //number of components in arrayListSplitInputString ArrayList


    //Define new object instance from the native Stack Class
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

        /*int i = 0;*/
        for (int i = 0; i < intArrayListSize; i++) {
            processArrayList(i, arrayListSplitInputString.get(i));
        }

        //Empty the contents of arrayListSplitInputString after each call to this
        //method (processCommand) before it is called again from the Main method.
        clearArrayList(arrayListSplitInputString);
        System.out.println("arrayListSplitInputString = " + arrayListSplitInputString.toString());
        //TESTING DELETE
        System.out.println("stackSRPN = " + stackSRPN.toString());

    }

    //- processArrayList is the method that implements the SRPN logic including arithmetic
    //  operations, Stack functionality, and handling of exceptions.
    //- It pulls the array components stored in the ArrayList input (arrayListSplitInputString)
    //  one at a time, classifies them as integers, operator symbols and non-operator symbols,
    //  and process them in accordance with the intended legacy SRPN logic.
    public void processArrayList(int i, String s){ //public void processArrayList(ArrayList ArrL){
        //Parameters
        String strTwoPreviousArrayComponent = "";  //empty by default
        String strPreviousArrayComponent = "";  //empty by default
        String strNextArrayComponent = "";  //empty by default

        //Get two-previous ArrayList component
        if (i >= 2) {  //only if s String is the third or subsequent component of ArrayList
            strTwoPreviousArrayComponent = arrayListSplitInputString.get(i-2);
        }

        //Get previous ArrayList component
        if (i != 0) {  //only if s String is the second or subsequent component of ArrayList
            strPreviousArrayComponent = arrayListSplitInputString.get(i-1);
        }

        //Get next ArrayList component
        if (i+1 != intArrayListSize) {  //only if s String is not the last component of ArrayList
            strNextArrayComponent = arrayListSplitInputString.get(i+1);
        }

        /** //DELETEEEEEE
        System.out.println("currentArrayComponent = " + s);
        System.out.println("strTwoPreviousArrayComponent = " + strTwoPreviousArrayComponent);
        System.out.println("strPreviousArrayComponent = " + strPreviousArrayComponent);
        System.out.println("strNextArrayComponent = " + strNextArrayComponent);
        //DELETEEEEEE*/

        //CASE 0: input String s is a number (it's formed by digit characters).
        //Number is converted to a Long integer processed as follows:
        // - if it is the first component or of the ArrayList or is preceded by a space
        //   (" ") then push integer into the stack.
        // - if the next component is an arithmetic operation symbol, the do nothing.
        // - perform stack overflow check before pushing integer into stack.
        // - perform saturation check and process integer accordingly with max/min
        //   Int limits.
        if (isLong(s) &&  //s is a numeric string AND
                ( intArrayListSize==1 || //( s is the single component in array OR
                        !(strArithmeticOperatorSymbols.contains(strNextArrayComponent)) ||  //following component in array in not an arithmetic symbol OR
                                strNextArrayComponent.isEmpty())) {  //following component in array is empty )

            if (Objects.equals(strPreviousArrayComponent, strMinusSymbol) && //check if number is negative: there is a preceding minus AND
                    (strTwoPreviousArrayComponent.isEmpty() || !isLong(strTwoPreviousArrayComponent))) {  //(there is no two-previous component OR the minus doesn't denote subtraction)
                        System.out.println("NEGATIVE TO STACK!!!!!!!!!!!!!!!!!!!! Number pushed: "+ processParseSaturation(strPreviousArrayComponent+s));
                        stackSRPN.push(Long.toString(processParseSaturation(strPreviousArrayComponent+s)));  //push negative number
                        return;
            } else {  //s is a positive number that qualifies to be pushed into stack
                        System.out.println("POSITIVE TO STACK!!!!!!!!!!!!!!!!!!!! Number pushed: "+ processParseSaturation(s));
                        stackSRPN.push(Long.toString(processParseSaturation(strPreviousArrayComponent+s)));  //push negative number
                        return;
            }
        } else if (isLong(s) &&  //s is a numeric string AND
                strArithmeticOperatorSymbols.contains(strNextArrayComponent) ) {  //s component is followed by an arithmetic operation symbol

            //IMPLEMENT LOGIC: Evaluate a math expression given in string form using
            System.out.println("DO NOTHING ACTION - LOGIC FOR IN-LINE OPERATIONS STILL NOT IMPLEMENTED");
            return;  //do nothing FOR NOW
        }

        //CASE XX: input String s is a "d" symbol.
        //Processing logic is as follows:
        // - existing components of the stack are pulled and printed one at a time.
        // - if the stack is empty, the Stack Underflow msg is printed.
        if (Objects.equals(s, strDeeSymbol)) {
            if (stackSRPN.empty()) {
                System.out.println(strStackEmpty);
                return;
            } else {
                for (String strComponent : stackSRPN) {
                    System.out.println(strComponent);
                }
                return;
            }
        }

        //CASE XX: input String s is a non-numeric, a non-arithmetic or a non
        //SRPN operator symbol.
        //Processing logic is as follows:
        // - if the symbol is a space " " symbol then the method returns void (does nothing).
        // - if the symbol is not a space symbol then prints the appropriate error.
        if (!isLong(s) &&  //s not numeric AND
                !strArithmeticOperatorSymbols.contains(s) &&  //s not operator symbol AND
                    ( !strSrpnOperatorSymbols.contains(s) ||  // ( s not operator symbol AND
                        Objects.equals(s, strSpaceSymbol) )) {

            if (Objects.equals(s, strSpaceSymbol)) {
                return;  //do nothing
            } else {
                System.out.println("Unrecognised operator or operand \"" + s + "\".");
                return;
            }
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
        if (isLong(s)) {
            Long longS = processParseSaturation(s);
            if (longS < 0) { // input String is a negative number
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

                if (!isLong(str.toString())) { // if non-numeric char is found at i-iteration

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

    // Method that verifies if a string argument is an Int (32-bit) and returns a
    // boolean (True if integer)
    public boolean isInt(String s) {
        try {
            int testInt = Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Method that verifies if a string argument is a Long integer (64-bit)
    // (positive or negative) and returns a boolean (True if Long)
    public boolean isLong(String s) {

        String str;  //auxiliary string

        if (s.length()>19) {  //if length of string is greater than the max length of an Integer
            str = s.substring(s.length()-18,s.length());  //cut the long numerical strings and retain the 10-long string from the right-side
        } else {
            str = s;

        }
        try {
            long testLong = Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
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
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /** Method that verifies if there is stack overflow (true if stack overflow)
    and writes the corresponding error msg.*/
    public boolean stackOverflow(Stack inputStack) {
        if (inputStack.size() >= intMaxStackSize) {
            System.out.println(strStackOverflow);
            return true;
        } else {
            return false;
        }
    }

    /** Method that verifies if there is stack underflow (true if stack underflow)
    */
    public boolean stackUnderflow(Stack inputStack) {
        if (inputStack.size() <= 1) {
            System.out.println(strStackUnderflow);
            return true;
        } else {
            return false;
        }
    }

    // Method that process a signed numeric string argument by:
    //  - removing any preceding / redundant zeroes.
    //  - split the string in two strings of 19 digits max. if length is of input
    //    string is greater than 38 (max. accuracy of this calculator!).
    //  - convert the string into a Long integer (64-bit) and adjust its value
    //    to suit max/min saturation limits for integers.
    //  - return the resulting signed Long integer
    public Long processParseSaturation(String s) {

    //Parameters
    int intSLength = s.length();
    int intSLengthMinusOne = s.length()-1;
    int intStrLength;
    String strFirstChar = s.substring(0, 1);
    String strTrimmed;
    String strSaturated;
    int intTrimStartIndex;
    int intVarIndex = 0;
    String strPartLeft = "0"; // right-side split part
    String strPartRight = "0"; // left-side split part
    Long longPartLeft;
    Long longPartRight;
    Long longProcessedInt;
    Long longIntMaxVal = Long.parseLong(Integer.toString(Integer.MAX_VALUE));
    Long longIntMinVal = Long.parseLong(Integer.toString(Integer.MIN_VALUE));

    //Process to remove redundant zeroes at the left
    //Set start index depending on number sign
    if (strFirstChar.equals("-")) {
        intTrimStartIndex = 1;
    } else {
        intTrimStartIndex = 0;
    }

    //Get index where row of zeroes end
    for (int i = intTrimStartIndex; i <= intSLengthMinusOne; i++) {
        strTrimmed = s.substring(i, i+1);
        if (strTrimmed.equals("0")){
            intVarIndex = i;
        } else {
            break;
        }
    }

    //Trim string s accordingly and store it in str string
    if (strFirstChar.equals("-")) {
        strTrimmed = "-" + s.substring(intVarIndex+1, intSLength);
    } else {
        strTrimmed = s.substring(intVarIndex, intSLength);
    }

    //Trim strTrimmed if length is greater than 38 (max. accuracy of this calculator)
    intSLength = strTrimmed.length();
    if (intSLength > 38) {
        if (strFirstChar.equals("-")) {
            strSaturated = strTrimmed.substring(0, 39);
        } else {
            strSaturated = strTrimmed.substring(0, 38);
        }
    } else {
        strSaturated = strTrimmed;
    }

    //If length of string is greater than 19 (Long limit) then split the string in two
    intStrLength = strSaturated.length();
    if (intSLength <= 19) {
        strPartRight = strSaturated;
    } else {
        int intSplitIndex = intStrLength - 19;
        strPartLeft = strSaturated.substring(0,intSplitIndex);
        strPartRight = strSaturated.substring(intSplitIndex,intStrLength);
    }

    longPartLeft = Long.parseLong(strPartLeft);
    longPartRight = Long.parseLong(strPartRight);

    //Adjust string value according to max/min. Integer limits
    if (strFirstChar.equals("-")) {  //negative number
        if (longPartLeft < 0){
            longProcessedInt = longIntMinVal;
        } else {
            longProcessedInt = Math.max(longPartRight, longIntMinVal);
        }
    } else {  //positive number
        if (longPartLeft > 0){
            longProcessedInt = longIntMaxVal;
        } else {
            longProcessedInt = Math.min(longPartRight, longIntMaxVal);
        }
    }
    return longProcessedInt;
    }





        /**
        int intSLength = s.length();
        String strFirstChar = s.substring(0,1);  //obtain first character of string
        String str;  //auxiliary string
        String strPartLeft;  //right-side split part
        String strPartRight;  //left-side split part

        //Max precision of this SRPN calculator is 38 digits-long integers.
        //Otherwise the s string is trimmed from the right to fit a 38 digits-long integer
        if (intSLength > 38) {
            if (strFirstChar.equals("-")) {
                str = s.substring(0, 39);
            } else {
                str = s.substring(0, 38);
            }
        } else {
            str = s;
        }

        //Now the string is split into two parts that can fit into two Long integers
        if (intSLength <= 19) {
            strPartRight = str;  //no need of defining a right part
        } else {
            strPartRight = str.substring(0,intSLength-19);
            strPartLeft = str.substring(intSLength-19,intSLength);
        }





        String str;  //auxiliary string
        String strPar1 =

        if (s.length()>38) {  //if length of string is greater than the max length of an Integer
            if (strFirstChar.equals(Character.toString(charMinusSymbol))) {  //first character of s is the minus symbol
                str = strFirstChar + s.substring(s.length()-37,s.length());  //cut the long numerical strings and retain the 10-long string from the right-side
            } else {
                str = s.substring(s.length()-9,s.length());
            }
        } else {
            str = s;
        }











        if (s.length()>12) {  //if length of string is greater than the max length of an Integer
            if (strFirstChar.equals(Character.toString(charMinusSymbol))) {  //first character of s is the minus symbol
                str = strFirstChar + s.substring(s.length()-11,s.length());  //cut the long numerical strings and retain the 10-long string from the right-side
            } else {
                str = s.substring(s.length()-9,s.length());
            }
        } else {
            str = s;
        }
System.out.println("str (processSaturation) =" + str +"  ("+str.length()+" numbers long)");
        long longStr = Long.parseLong(str);  //convert numeric string into Long integer

        if (longStr<0) {
            longStr = Math.max(longStr,intMinSaturationVal);
        } else {
            longStr = Math.min(longStr,intMaxSaturationVal);
        }
        return longStr;

    }


 */




}
