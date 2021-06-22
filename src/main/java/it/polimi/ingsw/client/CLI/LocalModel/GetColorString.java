package it.polimi.ingsw.client.CLI.LocalModel;

import it.polimi.ingsw.server.model.enumerations.DevelopmentCardColor;
import it.polimi.ingsw.server.model.enumerations.Marble;
import it.polimi.ingsw.server.model.enumerations.Resource;

/**
 * Class that corresponds to each coloured element in the CLI printings a colour from CliColor
 */
public class GetColorString {

    public CliColor getColorDevelopmentCard(DevelopmentCardColor color) {

        switch (color) {
            case YELLOW:
                return CliColor.COLOR_YELLOW;
            case BLUE:
                return CliColor.COLOR_BLUE;
            case PURPLE:
                return CliColor.COLOR_PURPLE;
            case GREEN:
                return CliColor.COLOR_GREEN;
        }
        return null;
    }

    public CliColor getColorResource(Resource color) {

        switch (color) {
            case COIN:
                return CliColor.COLOR_YELLOW;
            case SHIELD:
                return CliColor.COLOR_BLUE;
            case SERVANT:
                return CliColor.COLOR_PURPLE;
            case STONE:
                return CliColor.COLOR_GREY;
            case FAITH:
                return CliColor.COLOR_RED;
        }
        return null;
    }

    public CliColor getColorMarble(Marble marble) {

        switch (marble) {
            case YELLOWMARBLE:
                return CliColor.COLOR_YELLOW;
            case GREYMARBLE:
                return CliColor.COLOR_GREY;
            case REDMARBLE:
                return CliColor.COLOR_RED;
            case BLUEMARBLE:
                return CliColor.COLOR_BLUE;
            case PURPLEMARBLE:
                return CliColor.COLOR_PURPLE;
            case WHITEMARBLE:
                return CliColor.COLOR_WHITE;
        }
        return null;
    }

}
