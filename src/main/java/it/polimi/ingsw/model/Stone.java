package it.polimi.ingsw.model;

public class Stone extends Resource {
    private static Stone instance = null;
    private Stone(){}

    /**
     * Singleton pattern method to obtain the single instance
     * @return single Stone instance of the game
     */
    public static Stone getInstance(){
        if (instance == null){
            instance = new Stone();
        }
        return instance;
    }
}
