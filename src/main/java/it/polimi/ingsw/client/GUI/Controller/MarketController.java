package it.polimi.ingsw.client.GUI.Controller;

import it.polimi.ingsw.common.messages.messagesToServer.TakeFromMarketMessage;
import it.polimi.ingsw.server.model.enumerations.Marble;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class MarketController extends AbstractController{
    private final int NUM_OF_ROW = 3;
    private final int NUM_OF_COLUMN = 4;

    private Circle[][] circlesGrid = new Circle[NUM_OF_ROW][NUM_OF_COLUMN];

    @FXML
    private GridPane marketGrid;
    @FXML
    private Circle marbleOut;
    @FXML
    private Button firstRow;
    @FXML
    private Button secondRow;
    @FXML
    private Button thirdRow;
    @FXML
    private Button firstColumn;
    @FXML
    private Button secondColumn;
    @FXML
    private Button thirdColumn;
    @FXML
    private Button fourthColumn;


    public void initialize(){

        for(Node node: marketGrid.getChildren()){
            circlesGrid[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)] = (Circle) node;
        }

        firstRow.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onRowClick);
        secondRow.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onRowClick);
        thirdRow.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onRowClick);

        firstColumn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onColumnClick);
        secondColumn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onColumnClick);
        thirdColumn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onColumnClick);
        fourthColumn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onColumnClick);

    }

    private void onRowClick(Event event){
        int row = Integer.parseInt(((Button) event.getTarget()).getId());
        this.getGui().getMessageSender().sendMessage(new TakeFromMarketMessage(this.getGui().getNickname(),0,row));
    }

    private void onColumnClick(Event event){
        int column = Integer.parseInt(((Button) event.getTarget()).getId());
        this.getGui().getMessageSender().sendMessage(new TakeFromMarketMessage(this.getGui().getNickname(),0,column));
    }

    private Color getMarbleColor(Marble marble){
        switch (marble){
            case WHITEMARBLE:
                return Color.WHITE;
            case REDMARBLE:
                return Color.RED;
            case BLUEMARBLE:
                return Color.BLUE;
            case GREYMARBLE:
                return Color.GRAY;
            case PURPLEMARBLE:
                return Color.PURPLE;
            case YELLOWMARBLE:
                return Color.YELLOW;
        }
        return null;
    }

    public void setMarbles(Marble[][] marketMatrix, Marble marbleOut){
        for(int row = 0;row < NUM_OF_ROW; row++){
            for (int column = 0; column < NUM_OF_COLUMN; column++){
                circlesGrid[row][column].setFill(getMarbleColor(marketMatrix[row][column]));
            }
        }
        this.marbleOut.setFill(getMarbleColor(marbleOut));
    }


}
