package it.polimi.ingsw.model;

public interface Manageable {
    /**
     * stores a given quantity of the passed resource
     * @param resource The resource to be added, must not be Faith
     * @param quantity The quantity of the resource to be added, must be a positive number
     */
    void add(Resource resource, int quantity);

    /**
     * removes a given quantity of the passed resource
     * @param resource The resource to be removed, must not be Faith and must be previously added
     * @param quantity The quantity of the resource to be removed, must be a positive number smaller
     *                 than the quantity available
     */
    void remove(Resource resource, int quantity);

}
