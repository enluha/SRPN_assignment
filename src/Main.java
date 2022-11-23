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

        String manualcommand = "3 3 ^ 3 ^ 3 ^=";
        srpn.processCommand(manualcommand);

    }
}