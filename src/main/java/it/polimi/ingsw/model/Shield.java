package it.polimi.ingsw.model;

public class Shield extends Resource{
    private static Shield instance = null;
    private Shield(){}

    /**
     * Singleton pattern method to obtain the single instance
     * @return single Shield instance of the game
     */
    public static Shield getInstance(){
        if (instance == null){
            instance = new Shield();
        }
        return instance;
    }
}
