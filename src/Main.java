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
        String strManualCommand =   " 1 2 3 4 5 6 7 8 9 10 11 -12 -13 -14 15 16 17 18 19 20 21 22 23 24 25d";//"10 2 +";//"-1--5";//"-22.98+1234-2s";
        srpn.processCommand(strManualCommand);

    }
}