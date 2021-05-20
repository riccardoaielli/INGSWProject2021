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
            clientView.askCreateMatch();
        }
    },
    NICKNAME {
        @Override
        public void handlePhase(ClientView clientView) {
            clientView.askNickname();
        }
    },
    LEADER_CHOICE {
        @Override
        public void handlePhase(ClientView clientView) { clientView.askForLeaderCards(); }
    };


    public abstract void handlePhase(ClientView clientView);
}
