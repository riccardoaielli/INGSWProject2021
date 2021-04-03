package it.polimi.ingsw.model;

public class Faith extends Resource{
    private static Faith instance = null;
    private Faith(){}

    /**
     * Singleton pattern method to obtain the single instance
     * @return single Faith instance of the game
     */
    public static Faith getInstance(){
        if (instance == null){
            instance = new Faith();
        }
        return instance;
    }
}
