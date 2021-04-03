package it.polimi.ingsw.model;

public class Servant extends Resource {
    private static Servant instance = null;
    private Servant(){}

    /**
     * Singleton pattern method to obtain the single instance
     * @return single Servant instance of the game
     */
    public static Servant getInstance(){
        if (instance == null){
            instance = new Servant();
        }
        return instance;
    }

}
