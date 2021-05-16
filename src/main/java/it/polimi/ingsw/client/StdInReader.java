package it.polimi.ingsw.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

/**
 * This thread reads from stdin stream of the client and returns the line
 */
public class StdInReader implements Callable<String> {

    String line = "";

    @Override
    public String call() {
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        try {
            while ((line = stdIn.readLine()) != null) {
                //System.out.println("StdinReader thread reading line... :" + line);
                return line;
            }
            System.out.println("Aborted StdinReader thread");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}

