package it.polimi.ingsw.client.CLI.LocalModel;

public enum cliColor {

    COLOR_BLUE("\033[38;5;12m"),
    COLOR_YELLOW("\033[38;5;11m"),
    COLOR_GREY("\033[38;5;8m"),
    COLOR_PURPLE("\033[38;5;5m"),
    COLOR_GREEN("\033[38;5;10m"),
    COLOR_WHITE("\033[38;5;15m"),
    COLOR_RED("\033[38;5;9m"),
    RESET("\033[0m");


    private final String string;

    cliColor(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }
}