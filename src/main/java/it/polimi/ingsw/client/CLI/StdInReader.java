package it.polimi.ingsw.client.CLI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StdInReader extends Thread{
    private boolean questionAsked;
    private String line = "";

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
        return line;
    }

    private synchronized void handleLine(){
        questionAsked = false;
        //sveglia readUserInput
        notifyAll();
    }

}
