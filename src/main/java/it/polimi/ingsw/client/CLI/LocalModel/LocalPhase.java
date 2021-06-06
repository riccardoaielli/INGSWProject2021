package it.polimi.ingsw.client.CLI.LocalModel;

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
    },
    RESOURCE_CHOICE {
        @Override
        public void handlePhase(ClientView clientView) {
            clientView.askResourceChoice();
        }
    },
    ADD_TO_WAREHOUSE{
        @Override
        public void handlePhase(ClientView clientView) {
            clientView.askAddToWareHouse();
        }
    },
    MENU {
        @Override
        public void handlePhase(ClientView clientView) {
            clientView.askTurnAction();
        }
    },
    MAIN_TURN_ACTION_DONE{
        @Override
        public void handlePhase(ClientView clientView) {
            clientView.askTurnAction();
        }
    },
    TAKE_FROM_MARKET{
        @Override
        public void handlePhase(ClientView clientView) { clientView.askTakeFromMarketAction();}
    },
    TEMPORARY_MARBLES{
        @Override
        public void handlePhase(ClientView clientView) {
            clientView.askForLeaderPower();
        }
    },
    BUY_DEV_CARD{
        @Override
        public void handlePhase(ClientView clientView) {
            clientView.askBuyDevCard();
        }
    },
    ACTIVATE_PRODUCTION{
        @Override
        public void handlePhase(ClientView clientView) {
            clientView.askProduction();

        }
    },
    ACTIVATE_LEADER {
        @Override
        public void handlePhase(ClientView clientView) {
            clientView.askActivateLeader();
        }
    },
    DISCARD_LEADER {
        @Override
        public void handlePhase(ClientView clientView) {
            clientView.askDiscardLeader();
        }
    },
    REARRANGE_WAREHOUSE {
        @Override
        public void handlePhase(ClientView clientView) {
            clientView.askRearrange();
        }
    };


    public abstract void handlePhase(ClientView clientView);
}
