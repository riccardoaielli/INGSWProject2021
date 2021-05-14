package it.polimi.ingsw.common;

/**
 * Enum of every type of exchanged messages
 */
public enum MessageType {
    NICKNAME_REPLY,
    DISCARD_INITIAL_LEADER,
    CHOOSE_INITIAL_RESOURCES,
    ERROR,
    CREATE_MATCH_REPLY,
    TAKE_FROM_MARKET,
    TRANSFORM_WHITE_MARBLES,
    TRANSFORM_MARBLES,
    MARKET_UPDATE,
    CARDGRID_UPDATE
}
