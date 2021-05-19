package it.polimi.ingsw.client.LocalModel;

import it.polimi.ingsw.client.ClientView;

public enum LocalPhase {
    DEFAULT{
        @Override
        public void handlePhase(ClientView clientView) {

        }
    },
    FIRST_PLAYER{
        @Override
        public void handlePhase(ClientView clientView) {
            clientView.showUpdateFirstConnection(true);
        }
    },
    NICKNAME {
        @Override
        public void handlePhase(ClientView clientView) {
            clientView.showUpdateFirstConnection(false);
        }
    },
    LEADER_CHOICE {
        @Override
        public void handlePhase(ClientView clientView) {

        }
    };


    public abstract void handlePhase(ClientView clientView);
}
