package it.polimi.ingsw.common.messages;

/**
 * Abstract class extended by every message, containing nickname of the sender in case of MessageToServer or of the one whose elements in the game are changed in case of MessageToClient
 */
public abstract class Message {
    private final String nickname;
    MessageType messageType;

    /**
     * Constructor of the message
     * @param nickname the nickname of the player that sends or receives the message
     * @param messageType the type of the message
     */
    public Message(String nickname, MessageType messageType) {
        this.messageType = messageType;
        this.nickname = nickname;
    }

    /**
     * Getter for the nickname of the player associated with this message
     * @return a nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Getter for the message type
     * @return message type of this message
     */
    public MessageType getMessageType() {
        return messageType;
    }
}