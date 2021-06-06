package it.polimi.ingsw.client.CLI.LocalModel;

import it.polimi.ingsw.server.model.enumerations.DevelopmentCardColor;
import it.polimi.ingsw.server.model.enumerations.Marble;
import it.polimi.ingsw.server.model.enumerations.Resource;

public class GetColorString {

    public cliColor getColorDevelopmentCard(DevelopmentCardColor color) {

        switch (color) {
            case YELLOW:
                return cliColor.COLOR_YELLOW;
            case BLUE:
                return cliColor.COLOR_BLUE;
            case PURPLE:
                return cliColor.COLOR_PURPLE;
            case GREEN:
                return cliColor.COLOR_GREEN;
        }
        return null;
    }

    public cliColor getColorResource(Resource color) {

        switch (color) {
            case COIN:
                return cliColor.COLOR_YELLOW;
            case SHIELD:
                return cliColor.COLOR_BLUE;
            case SERVANT:
                return cliColor.COLOR_PURPLE;
            case STONE:
                return cliColor.COLOR_GREY;
            case FAITH:
                return cliColor.COLOR_RED;
        }
        return null;
    }

    public cliColor getColorMarble(Marble marble) {

        switch (marble) {
            case YELLOWMARBLE:
                return cliColor.COLOR_YELLOW;
            case GREYMARBLE:
                return cliColor.COLOR_GREY;
            case REDMARBLE:
                return cliColor.COLOR_RED;
            case BLUEMARBLE:
                return cliColor.COLOR_BLUE;
            case PURPLEMARBLE:
                return cliColor.COLOR_PURPLE;
            case WHITEMARBLE:
                return cliColor.COLOR_WHITE;
        }
        return null;
    }

}
