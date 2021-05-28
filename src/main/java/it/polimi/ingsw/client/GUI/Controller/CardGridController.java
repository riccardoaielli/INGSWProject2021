package it.polimi.ingsw.client.GUI.Controller;

import it.polimi.ingsw.client.GUI.Controller.AbstractController;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class CardGridController extends AbstractController {

    private Node[][] gridPaneArray = null;
    private int[][] cardGridMatrixCurrent = new int[maxRow][maxColumn];
    private static final int maxColumn = 4, maxRow = 3;
    private static final double h = 175, w = 116;

    @FXML
    private GridPane gridPane;

    @FXML
    public void initialize() {

        initializeGridPaneArray();

        for(int i=0; i<maxRow; i++) {
            for (int j = 0; j < maxColumn; j++) {
                Node node = gridPaneArray[i][j];
                ImageView imgView = (ImageView) node;
                imgView.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onImgClick);
            }
        }

        for(int i=0; i<maxRow; i++) {
            for (int j = 0; j < maxColumn; j++) {
                cardGridMatrixCurrent[i][j] = 0;
            }
        }
    }

    /**
     *Calls method drawCards to print cards in gridPane
     * @param cardGridMatrixUpdate is the cardGrid matrix from model
     */
    public void setCardGrid(int[][] cardGridMatrixUpdate){

        for(int i=0; i<maxRow; i++) {
            for (int j = 0; j < maxColumn; j++) {
                if(cardGridMatrixUpdate[i][j] != cardGridMatrixCurrent[i][j]) {
                    drawCards(cardGridMatrixUpdate[i][j], i, j);
                    cardGridMatrixCurrent[i][j] = cardGridMatrixUpdate[i][j];
                }
            }
        }
    }

    /**
     *This method sets the correct image in the correct ImageView in the right position of gridPane
     * @param id is the id of the image card
     * @param i is the row
     * @param j is the column
     */
    private void drawCards(int id, int i, int j) {
        Image img = new Image("cardsImage/" + id + ".png");
        Node node = gridPaneArray[i][j];
        ImageView imgView = (ImageView) node;
        imgView.setFitWidth(w);
        imgView.setFitHeight(h);
        imgView.setPreserveRatio(true);
        imgView.setId(Integer.toString(id));
        imgView.setImage(img);
    }

    private void initializeGridPaneArray()
    {
        gridPaneArray = new Node[maxRow][maxColumn];
        for(Node node : gridPane.getChildren())
        {
            Integer x = GridPane.getRowIndex(node);
            Integer y = GridPane.getColumnIndex(node);
            if((x != null) && (y != null)) {
                gridPaneArray[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)] = node;
            }
        }
    }

    private void onImgClick(Event event){
        ImageView imageView = (ImageView) event.getTarget();
        System.out.println(GridPane.getRowIndex(imageView) + "," +GridPane.getColumnIndex(imageView));
        //todo passo la posizione a chi serve per creare il messaggio
    }
}
