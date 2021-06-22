package it.polimi.ingsw.server.model.enumerations;

/**
 * This class represents the phase of a personal board during a turn
 */
public enum PersonalBoardPhase {
    LEADER_CHOICE,
    RESOURCE_CHOICE,
    ADD_INITIAL_RESOURCES,
    MAIN_TURN_ACTION_AVAILABLE,
    PRODUCTION,
    TAKE_FROM_MARKET,
    MAIN_TURN_ACTION_DONE
}
