package it.polimi.ingsw.client.CLI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class is a thread that reads from the standard input stream
 */
public class StdInReader extends Thread{
    private boolean questionAsked;
    private String line = "";

    /**
     * Run method that reads from input stream
     */
    @Override
    public void run() {
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String line = "";

        try {
            while ((line = stdIn.readLine()) != null) {
                if(questionAsked) {
                    this.line = line;
                    handleLine();
                }
                else {
                    switch (line.toLowerCase()){
                        case "quit":
                            System.out.println("Quitting...");
                            System.exit(0);
                            break;
                        case "help":
                            System.out.println("Wait for your turn...");
                            break;
                        default:
                            System.out.println("Command not valid");
                    }
                }
            }
            System.out.println("Aborted StdinReader thread");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Run method that reads from input stream
     */
    public synchronized String readUserInput(){
        questionAsked = true;
        //while in cui aspetta le l'input (con la notify)
        while (questionAsked) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(line.equals("quit")){
            System.out.println("Quitting...");
            System.exit(0);
        }
        return line;
    }

    private synchronized void handleLine(){
        questionAsked = false;
        //wake readUserInput
        notifyAll();
    }

}
