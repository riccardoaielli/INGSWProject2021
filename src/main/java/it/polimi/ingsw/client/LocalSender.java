package it.polimi.ingsw.client;


import it.polimi.ingsw.common.messages.messagesToServer.MessageToServer;
import it.polimi.ingsw.server.controller.Controller;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Class used to locally implement a message based communication between view and controller
 */
public class LocalSender implements MessageSender {
    private Controller controller;
    private final ClientView clientView;
    private final Executor executor;
    public LocalSender(ClientView view) {
        this.clientView = view;
        executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            controller = new Controller();
            controller.newConnection(clientView);
        });
    }

    /**
     * Handles message locally to call a method directly on the controller
     * @param message, is the message to be sent
     */
    @Override
    public void sendMessage(MessageToServer message) {
        executor.execute(() -> {
            message.handleMessage(controller, clientView);
        });
    }
}
