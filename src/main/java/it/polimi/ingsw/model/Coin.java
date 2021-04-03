package it.polimi.ingsw.model;

public class Coin extends Resource {
    private static Coin instance = null;
    private Coin(){}

    /**
     * Singleton pattern method to obtain the single instance
     * @return single Coin instance of the game
     */
    public static Coin getInstance(){
        if (instance == null){
            instance = new Coin();
        }
        return instance;
    }
}
