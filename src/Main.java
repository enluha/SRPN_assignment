import java.io.*;

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
        String manualcommand = "-00000000000000000012";
        srpn.processCommand(manualcommand);


        /*------------------ commented section; WIP -------------------------------

        //Method processing input from input source (script test file or keyboard)
        try {
            //Keep on accepting input from the command-line
            while(true) {
                String command = reader.readLine();
                //Close on an End-of-file (EOF) (Ctrl-D on the terminal)
                if(command == null){
                    //Exit code 0 for a graceful exit
                    System.exit(0);
                }
                //Otherwise, (attempt to) process the character
                sprn.processCommand(command);
            }
        }
        //Stop while(true) loop if an exception when accessing input is found
        catch(IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

       ------------------ commented section; WIP -------------------------------*/
       //
    }
}