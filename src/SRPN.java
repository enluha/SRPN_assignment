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
    String strModuloSymbol = "%";
    String strEqualSymbol = "=";
    String strCircumflexSymbol = "^";
    String strHashSymbol = "#";
    String strArSymbol = "r";
    String strStackOverflow = "Stack overflow.";
    String strStackUnderflow = "Stack underflow.";
    String strNegativePower = "Negative power.";
    String strStackEmpty = "Stack empty.";
    String strDivideByZero = "Divide by 0.";


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


    //Method that executes a push() operation in a stack and verifies there is no stack overflow.
    public void stackPush(Stack inputStack, String s) {
        if (stackOverflow(inputStack)) {
            return;
        } else {
            inputStack.push(Long.toString(processParseSaturation(s)));  //push positive number
            return;
        }
    }


    // - processCommand is the main method within the SRPN class. It's responsible for
    //   processing the input String s pushed from the Main method by calling the rest of
    //   methods withing this class making up the SRPN logic.
    // - It prints out the output from the SRPN logic when applicable.
    public void processCommand(String s) {

        //Call to method that splits the input String s into segments containing chain of
        //digits (numbers) & non-numerical characters and stores them in an Arraylist.
        splitStringIntoArrayList(s);


        //Call to method that process each component stored in the ArrayList
        //(arrayListSplitInputString) in accordance with the intended SRPN logic.
        intArrayListSize = arrayListSplitInputString.size();

        /*int i = 0;*/
        for (int i = 0; i < intArrayListSize; i++) {
            processArrayList(i, arrayListSplitInputString.get(i));
        }

        //Empty the contents of arrayListSplitInputString after each call to this
        //method (processCommand) before it is called again from the Main method.
        clearArrayList(arrayListSplitInputString);

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
        String strTwoNextArrayComponent = "";  //empty by default

        //Get two-previous ArrayList component
        if (i >= 2) {  //only if s String is the third or subsequent component of ArrayList
            strTwoPreviousArrayComponent = arrayListSplitInputString.get(i-2);
        }

        //Get previous ArrayList component
        if (i != 0) {  //only if s String is the second or subsequent component of ArrayList
            strPreviousArrayComponent = arrayListSplitInputString.get(i-1);
        }

        //Get next ArrayList component
        if (i < (intArrayListSize-1)) {  //only if s String is not the last component of ArrayList
            strNextArrayComponent = arrayListSplitInputString.get(i+1);
        }

        //Get two-next ArrayList component
        if (i < (intArrayListSize-2)) {  //only if s String is not the last component of ArrayList
            strTwoNextArrayComponent = arrayListSplitInputString.get(i+2);
        }


        //CASE 1: input String s is a number (it's formed by digit characters).
        //Number is converted to a Long integer processed as follows:
        // - if it is the first component or of the ArrayList or is preceded by a space
        //   (" ") then push integer into the stack.
        // - if the next component is an arithmetic operation symbol, the do nothing.
        // - perform stack overflow check before pushing integer into stack.
        // - perform saturation check and process integer accordingly with max/min
        //   Int limits.
        if ( isLong(s) && //s is a numeric string AND (
                ( !(strArithmeticOperatorSymbols.contains(strPreviousArrayComponent) && isLong(strTwoPreviousArrayComponent)) ||  // s NOT preceded by an arithmetic operation symbol and a number OR
                        !(strArithmeticOperatorSymbols.contains(strTwoNextArrayComponent) && isLong(strTwoNextArrayComponent)) ) ) {  // s NOT followed by an arithmetic operation symbol and a number )

            //Push numeric string s including stack overflow check
            stackPush(stackSRPN, Long.toString(processParseSaturation(s)));
            return;

        } else if ( isLong(s) && //s is a numeric string AND (
                ( (strArithmeticOperatorSymbols.contains(strPreviousArrayComponent) && isLong(strTwoPreviousArrayComponent)) ||  // s not preceded by an arithmetic operation symbol and a number NOR
                        (strArithmeticOperatorSymbols.contains(strTwoNextArrayComponent) && isLong(strTwoNextArrayComponent)) ) ) {  // s not followed by an arithmetic operation symbol and a number )

            //In-line equation solving functionality  goes here. It has not been implemented yet due to time constraints
            return; //do nothing with string s
        }


        //CASE 2: input String s is a "-" symbol.
        //Processing logic is as follows:
        //
        // - identifies the type of arrayListSplitInputString component following the
        //   "-" symbol.
        //
        // - if the input string (current arrayListSplitInputString component) is
        //   the only component in arrayListSplitInputString OR if the following
        //   component of the string is any non-numeric symbol, then execute the
        //   subtraction operation to the two first items of the stack.
        //
        // - if the following component of arrayListSplitInputString is numeric string,
        //   then sign the following numeric string of arrayListSplitInputString and
        //   replace the input string (current arrayListSplitInputString component)
        //   with a space " " symbol.
        if (Objects.equals(s, strMinusSymbol) && ((arrayListSplitInputString.size() == 1) ||  //string s is the only existing component in ArrayList
                !isLong(strNextArrayComponent))) {  //the ArrayList component following the minus symbol is non-numeric

                if (stackSRPN.size() < 2) {  //at least 2 elements need to be present in stack
                    System.out.println(strStackUnderflow);
                    return;
                } else {  //SRPN subtraction is performed

                    //Store pop() elements into auxiliary variables and process them for integer saturation
                    String stringBufferNum1 = stackSRPN.pop();  //extract first element in stack
                    String stringBufferNum2 = stackSRPN.pop();  //extract second element in stack
                    Long longBufferNum1 = processParseSaturation(stringBufferNum1);
                    Long longBufferNum2 = processParseSaturation(stringBufferNum2);

                    //Execute arithmetic operation
                    Long longResult = longBufferNum2 - longBufferNum1;
                    stringBufferNum1 = Long.toString(longResult);

                    //Push result including stack overflow check
                    stackPush(stackSRPN, Long.toString(processParseSaturation(stringBufferNum1)));

                    return;
                }

        } else if ((Objects.equals(s, strMinusSymbol)) && isLong(strNextArrayComponent)) {  //if following component in arrayListSplitInputString is a numerical string

            arrayListSplitInputString.set(i+1,strMinusSymbol + strNextArrayComponent);  //sign following numerical component
            arrayListSplitInputString.set(i,strSpaceSymbol);  //sign following numerical component
            return;

        }


        //CASE 3: input String s is a "+" symbol.
        //Processing logic is as follows:
        //
        // - performs the addition arithmetic operation of the two first elements in
        //   the stack.
        //
        // - the in-line equation solving functionality from the legacy SRPN has not
        //   been implemented due to time constraints. This algorithm will detect this
        //   obscure scenario and no execute any push() nor arithmetic operation.
        //
        // - if the input string (current arrayListSplitInputString component) is
        //   preceded by an arithmetic operation symbol and a number (in-line equation
        //   solving functionality) NOR followed by an arithmetic operation
        //   symbol and a number (in-line equation solving functionality).
        //
        // - otherwise it will then execute the addition  operation to the two first
        //   items of the stack.
        if ( Objects.equals(s, strPlusSymbol) && //s is a "+" symbol AND (
                ( !(isLong(strPreviousArrayComponent) && strArithmeticOperatorSymbols.contains(strTwoPreviousArrayComponent)) ||  // s NOT preceded by a number and an arithmetic operation symbol NOR
                        !(isLong(strNextArrayComponent) && strArithmeticOperatorSymbols.contains(strTwoNextArrayComponent)) ) ) {  // s NOT followed by a number and an arithmetic operation symbol )

            if (stackSRPN.size() < 2) {  //at least 2 elements need to be present in stack
                System.out.println(strStackUnderflow);
                return;
            } else {  //SRPN addition is performed

                //Store pop() elements into auxiliary variables and process them for integer saturation
                String stringBufferNum1 = stackSRPN.pop();  //extract first element in stack
                String stringBufferNum2 = stackSRPN.pop();  //extract second element in stack
                Long longBufferNum1 = processParseSaturation(stringBufferNum1);
                Long longBufferNum2 = processParseSaturation(stringBufferNum2);

                //Execute arithmetic operation
                Long longResult = longBufferNum2 + longBufferNum1;
                stringBufferNum1 = Long.toString(longResult);

                //Push result including stack overflow check
                stackPush(stackSRPN, Long.toString(processParseSaturation(stringBufferNum1)));

                return;
            }

        } else if ( Objects.equals(s, strPlusSymbol) && //s is a "+" symbol AND (
                ( (isLong(strPreviousArrayComponent) && strArithmeticOperatorSymbols.contains(strTwoPreviousArrayComponent)) ||  // s IS preceded by a number and an arithmetic operation symbol OR
                        (isLong(strNextArrayComponent) && strArithmeticOperatorSymbols.contains(strTwoNextArrayComponent)) ) ) {  // s IS followed by a number and an arithmetic operation symbol )

            //In-line equation solving functionality goes here. It has not been implemented yet due to time constraints
            return; //do nothing with string s
        }


        //CASE 4: input String s is a "*" symbol.
        //Processing logic is as follows:
        //
        // - performs the multiplication arithmetic operation of the two first elements in
        //   the stack.
        //
        // - the in-line equation solving functionality from the legacy SRPN has not
        //   been implemented due to time constraints. This algorithm will detect this
        //   obscure scenario and no execute any push() nor arithmetic operation.
        //
        // - if the input string (current arrayListSplitInputString component) is
        //   preceded by an arithmetic operation symbol and a number (in-line equation
        //   solving functionality) NOR followed by an arithmetic operation
        //   symbol and a number (in-line equation solving functionality).
        //
        // - otherwise it will then execute the multiplication operation to the two first
        //   items of the stack.
        if ( Objects.equals(s, strAsteriskSymbol) && //s is a "*" symbol AND (
                ( !(isLong(strPreviousArrayComponent) && strArithmeticOperatorSymbols.contains(strTwoPreviousArrayComponent)) ||  // s NOT preceded by a number and an arithmetic operation symbol NOR
                        !(isLong(strNextArrayComponent) && strArithmeticOperatorSymbols.contains(strTwoNextArrayComponent)) ) ) {  // s NOT followed by a number and an arithmetic operation symbol )

            if (stackSRPN.size() < 2) {  //at least 2 elements need to be present in stack
                System.out.println(strStackUnderflow);
                return;
            } else {  //SRPN multiplication is performed

                //Store pop() elements into auxiliary variables and process them for integer saturation
                String stringBufferNum1 = stackSRPN.pop();  //extract first element in stack
                String stringBufferNum2 = stackSRPN.pop();  //extract second element in stack
                Long longBufferNum1 = processParseSaturation(stringBufferNum1);
                Long longBufferNum2 = processParseSaturation(stringBufferNum2);

                //Execute arithmetic operation
                Long longResult = longBufferNum2 * longBufferNum1;
                stringBufferNum1 = Long.toString(longResult);

                //Push result including stack overflow check
                stackPush(stackSRPN, Long.toString(processParseSaturation(stringBufferNum1)));

                return;
            }

        } else if ( Objects.equals(s, strAsteriskSymbol) && //s is a "*" symbol AND (
                ( (isLong(strPreviousArrayComponent) && strArithmeticOperatorSymbols.contains(strTwoPreviousArrayComponent)) ||  // s IS preceded by a number and an arithmetic operation symbol OR
                        (isLong(strNextArrayComponent) && strArithmeticOperatorSymbols.contains(strTwoNextArrayComponent)) ) ) {  // s IS followed by a number and an arithmetic operation symbol )

            //In-line equation solving functionality goes here. It has not been implemented yet due to time constraints
            return; //do nothing with string s
        }


        //CASE 5: input String s is a "/" symbol.
        //Processing logic is as follows:
        //
        // - performs the division arithmetic operation of the two first elements in
        //   the stack.
        //
        // - the in-line equation solving functionality from the legacy SRPN has not
        //   been implemented due to time constraints. This algorithm will detect this
        //   obscure scenario and no execute any push() nor arithmetic operation.
        //
        // - if the input string (current arrayListSplitInputString component) is
        //   preceded by an arithmetic operation symbol and a number (in-line equation
        //   solving functionality) NOR followed by an arithmetic operation
        //   symbol and a number (in-line equation solving functionality).
        //
        // - otherwise it will then execute the division operation to the two first
        //   items of the stack.
        if ( Objects.equals(s, strSlashSymbol) && //s is a "/" symbol AND (
                ( !(isLong(strPreviousArrayComponent) && strArithmeticOperatorSymbols.contains(strTwoPreviousArrayComponent)) ||  // s NOT preceded by a number and an arithmetic operation symbol NOR
                        !(isLong(strNextArrayComponent) && strArithmeticOperatorSymbols.contains(strTwoNextArrayComponent)) ) ) {  // s NOT followed by a number and an arithmetic operation symbol )

            if (stackSRPN.size() < 2) {  //at least 2 elements need to be present in stack
                System.out.println(strStackUnderflow);
                return;
            } else {  //SRPN division is performed

                //Store pop() elements into auxiliary variables and process them for integer saturation
                String stringBufferNum1 = stackSRPN.pop();  //extract first element in stack
                String stringBufferNum2 = stackSRPN.pop();  //extract second element in stack
                Long longBufferNum1 = processParseSaturation(stringBufferNum1);
                Long longBufferNum2 = processParseSaturation(stringBufferNum2);

                //Check for division by 0 exception
                int intDivCheck = Integer.parseInt(stringBufferNum1);
                if (intDivCheck == 0) {
                    System.out.println(strDivideByZero);
                    return;
                }

                //Execute arithmetic operation
                Long longResult = longBufferNum2 / longBufferNum1;
                stringBufferNum1 = Long.toString(longResult);

                //Push result including stack overflow check
                stackPush(stackSRPN, Long.toString(processParseSaturation(stringBufferNum1)));

                return;
            }

        } else if ( Objects.equals(s, strSlashSymbol) && //s is a "/" symbol AND (
                ( (isLong(strPreviousArrayComponent) && strArithmeticOperatorSymbols.contains(strTwoPreviousArrayComponent)) ||  // s IS preceded by a number and an arithmetic operation symbol OR
                        (isLong(strNextArrayComponent) && strArithmeticOperatorSymbols.contains(strTwoNextArrayComponent)) ) ) {  // s IS followed by a number and an arithmetic operation symbol )

            //In-line equation solving functionality goes here. It has not been implemented yet due to time constraints
            return; //do nothing with string s
        }


        //CASE 6: input String s is a "%" symbol.
        //Processing logic is as follows:
        //
        // - performs the modulo arithmetic operation of the two first elements in
        //   the stack.
        //
        // - the in-line equation solving functionality from the legacy SRPN has not
        //   been implemented due to time constraints. This algorithm will detect this
        //   obscure scenario and no execute any push() nor arithmetic operation.
        //
        // - if the input string (current arrayListSplitInputString component) is
        //   preceded by an arithmetic operation symbol and a number (in-line equation
        //   solving functionality) NOR followed by an arithmetic operation
        //   symbol and a number (in-line equation solving functionality).
        //
        // - otherwise it will then execute the modulo operation to the two first
        //   items of the stack.
        if ( Objects.equals(s, strModuloSymbol) && //s is a "%" symbol AND (
                ( !(isLong(strPreviousArrayComponent) && strArithmeticOperatorSymbols.contains(strTwoPreviousArrayComponent)) ||  // s NOT preceded by a number and an arithmetic operation symbol NOR
                        !(isLong(strNextArrayComponent) && strArithmeticOperatorSymbols.contains(strTwoNextArrayComponent)) ) ) {  // s NOT followed by a number and an arithmetic operation symbol )

            if (stackSRPN.size() < 2) {  //at least 2 elements need to be present in stack
                System.out.println(strStackUnderflow);
                return;
            } else {  //SRPN modulo is performed

                //Store pop() elements into auxiliary variables and process them for integer saturation
                String stringBufferNum1 = stackSRPN.pop();  //extract first element in stack
                String stringBufferNum2 = stackSRPN.pop();  //extract second element in stack
                Long longBufferNum1 = processParseSaturation(stringBufferNum1);
                Long longBufferNum2 = processParseSaturation(stringBufferNum2);

                //Execute arithmetic operation
                Long longResult = longBufferNum2 % longBufferNum1;
                stringBufferNum1 = Long.toString(longResult);

                //Push result including stack overflow check
                stackPush(stackSRPN, Long.toString(processParseSaturation(stringBufferNum1)));

                return;
            }

        } else if ( Objects.equals(s, strModuloSymbol) && //s is a "%" symbol AND (
                ( (isLong(strPreviousArrayComponent) && strArithmeticOperatorSymbols.contains(strTwoPreviousArrayComponent)) ||  // s IS preceded by a number and an arithmetic operation symbol OR
                        (isLong(strNextArrayComponent) && strArithmeticOperatorSymbols.contains(strTwoNextArrayComponent)) ) ) {  // s IS followed by a number and an arithmetic operation symbol )

            //In-line equation solving functionality goes here. It has not been implemented yet due to time constraints
            return; //do nothing with string s
        }


        //CASE 7: input String s is a "^" symbol.
        //Processing logic is as follows:
        //
        // - performs the exponentiation arithmetic operation of the two first elements in
        //   the stack.
        //
        // - the in-line equation solving functionality from the legacy SRPN has not
        //   been implemented due to time constraints. This algorithm will detect this
        //   obscure scenario and no execute any push() nor arithmetic operation.
        //
        // - if the input string (current arrayListSplitInputString component) is
        //   preceded by an arithmetic operation symbol and a number (in-line equation
        //   solving functionality) NOR followed by an arithmetic operation
        //   symbol and a number (in-line equation solving functionality).
        //
        // - otherwise it will then execute the exponentiation operation to the two first
        //   items of the stack.
        if ( Objects.equals(s, strCircumflexSymbol) && //s is a "^" symbol AND (
                ( !(isLong(strPreviousArrayComponent) && strArithmeticOperatorSymbols.contains(strTwoPreviousArrayComponent)) ||  // s NOT preceded by a number and an arithmetic operation symbol NOR
                        !(isLong(strNextArrayComponent) && strArithmeticOperatorSymbols.contains(strTwoNextArrayComponent)) ) ) {  // s NOT followed by a number and an arithmetic operation symbol )

            if (stackSRPN.size() < 2) {  //at least 2 elements need to be present in stack
                System.out.println(strStackUnderflow);
                return;
            } else {  //SRPN exponentiation is performed

                //Store pop() elements into auxiliary variables and process them for integer saturation
                String stringBufferNum1 = stackSRPN.pop();  //extract first element in stack
                String stringBufferNum2 = stackSRPN.pop();  //extract second element in stack

                Long longBufferNum1 = processParseSaturation(stringBufferNum1);
                Long longBufferNum2 = processParseSaturation(stringBufferNum2);

                //Check for negative power exception
                if (longBufferNum1 < 0) {
                    System.out.println(strNegativePower);
                    return;
                }

                //Convert long to doubles to allow for the exponentiation primitive method
                double doubleBufferNum1 = (double) longBufferNum1;
                double doubleBufferNum2 = (double) longBufferNum2;

                //Execute arithmetic operation
                double doubleResult = Math.pow(doubleBufferNum2,doubleBufferNum1);
                long longResult = (long) doubleResult;
                stringBufferNum2 = Long.toString(longResult);

                //Push result including stack overflow check
                stackPush(stackSRPN, Long.toString(processParseSaturation(stringBufferNum2)));

                return;
            }

        } else if ( Objects.equals(s, strCircumflexSymbol) && //s is a "^" symbol AND (
                ( (isLong(strPreviousArrayComponent) && strArithmeticOperatorSymbols.contains(strTwoPreviousArrayComponent)) ||  // s IS preceded by a number and an arithmetic operation symbol OR
                        (isLong(strNextArrayComponent) && strArithmeticOperatorSymbols.contains(strTwoNextArrayComponent)) ) ) {  // s IS followed by a number and an arithmetic operation symbol )

            //In-line equation solving functionality goes here. It has not been implemented yet due to time constraints
            return; //do nothing with string s
        }


        //CASE 8: input String s is a "=" symbol.
        //Processing logic is as follows:
        // - existing components of the stack are pulled and printed one at a time.
        // - if the stack is empty, the Stack Underflow msg is printed.
        if (Objects.equals(s, strEqualSymbol)) {
            if (stackSRPN.empty()) {
                System.out.println(strStackEmpty);
                return;
            } else {
                System.out.println(stackSRPN.peek());
                return;
            }
        }


        //CASE 9: input String s is a "d" symbol.
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


        //CASE 10: input String s is a non-numeric, a non-arithmetic or a non
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


        //CASE 11: input String s is an equal symbol. Processing logic is as follows:
        // - calls the peek() function from the stack.
        // - if the stack is empty, print the corresponding msg.
        if (Objects.equals(s, strEqualSymbol) ) {
            if (stackSRPN.empty()) {
                System.out.println(strStackEmpty);
                return;
            } else {
                System.out.println(stackSRPN.peek());
                return;
            }
        }


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

                addStringIntoArrayList(s.substring(1), arrayListSplitInputString); // add full input String s into

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

            str.deleteCharAt(0); // clear str
            //String str2 = str;

            //int intStrL = str.length();

        // CASE 2: input String s is formed by more than a single char and contains one or more non-numeric characters
        } else {

            for (int i = 0; i < intStringLength; i++) { // iterates across all characters of input String s

                // str String is the string formed by a single char being tested in the loop
                if (str.length()>=1) {  //
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

                        addStringIntoArrayList(parts[0], arrayListSplitInputString); // add left-side part of the split into

                        addStringIntoArrayList(str.toString(), arrayListSplitInputString); // add non-numeric char

                        splitStringIntoArrayList(parts[1]); // recursive call to splitStringIntoArrayList to split the right-side

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

    //Method that verifies if there is stack overflow (true if stack overflow)
    //and writes the corresponding error msg.
    public boolean stackOverflow(Stack inputStack) {
        if (inputStack.size() >= intMaxStackSize) {
            System.out.println(strStackOverflow);
            return true;
        } else {
            return false;
        }
    }

    //Method that verifies if there is stack underflow (true if stack underflow)
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
    String strTrimmed = "0";
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


    //Handling of redundant zeroes preceding integers and trimming accordingly
    if (strFirstChar.equals("-") && intSLength==2) {  //if number is single digit negative
        if (Objects.equals(s.substring(1, 2), "0")) {
            strTrimmed = "0";
        } else {
            strTrimmed = s;
        }
    } else if (intSLength==1) {  //if number is single digit unsigned
        if (Objects.equals(s.substring(0, 1), "0")) {
            strTrimmed = "0";
        } else {
            strTrimmed = s;
        }
    } else {  //if number is has more than one digit and is either positive or negative
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



}
