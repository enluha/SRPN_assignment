import java.io.*;
import java.util.regex.Pattern;

class Main {

    // main method
    // reads in input from the command line
    // and passes this input to the processCommand method in SRPN

    public static void main(String[] args) {
        // Code to take input from the command line
        // This input is passed to the processCommand
        // method in SRPN.java

        //Constructor
        SRPN srpn = new SRPN();  //new object "srpn"
        //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));  //new object "reader"

        //Manual hardcoded input
        //String strManualCommand = "-1234567898765432101234567899"
        String strManualCommand =   "2147483647";//"-1--5";//"-22.98+1234-2s";
        srpn.processCommand(strManualCommand);

    }
}