package it.polimi.ingsw.client.GUI.Controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class CardGridController extends AbstractController {

    private Node[][] gridPaneArray = null;
    private int[][] cardGridMatrixCurrent = new int[maxRow][maxColumn];
    private static final int maxColumn = 4, maxRow = 3;
    private static final double h = 150, w = 100;

    @FXML
    private GridPane gridPane;

    @FXML
    public void initialize() {

        initializeCardGridPaneArray();
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

    private void initializeCardGridPaneArray()
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

    private void onImgClick(Event event){
        ImageView imageView = (ImageView) event.getTarget();
        System.out.println(GridPane.getRowIndex(imageView) + "," +GridPane.getColumnIndex(imageView));
        //todo passo la posizione a chi serve per creare il messaggio
    }
}
