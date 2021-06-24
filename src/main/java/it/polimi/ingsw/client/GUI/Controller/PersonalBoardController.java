package it.polimi.ingsw.client.GUI.Controller;

import it.polimi.ingsw.server.model.enumerations.Marble;
import it.polimi.ingsw.server.model.enumerations.Resource;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Map;

public class PersonalBoardController extends  AbstractController{

    //temp resource label
    @FXML
    private Label tempShieldLabel;
    @FXML
    private Label tempStoneLabel;
    @FXML
    private Label tempCoinLabel;
    @FXML
    private Label tempServantLabel;

    //temp marble label
    @FXML
    private Label whiteTempMarbleLabel;
    @FXML
    private Label yellowTempMarbleLabel;
    @FXML
    private Label blueTempMarbleLabel;
    @FXML
    private Label grayTempMarbleLabel;
    @FXML
    private Label redTempMarbleLabel;
    @FXML
    private Label purpleTempMarbleLabel;

    @FXML
    private ImageView leaderCard1;
    @FXML
    private ImageView leaderCard2;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private GridPane devCardSpaceZeroGP;
    @FXML
    private GridPane devCardSpaceOneGP;
    @FXML
    private GridPane devCardSpaceTwoGP;
    @FXML
    private ImageView imgViewPB;
    @FXML
    private ImageView shieldImgV;
    @FXML
    private ImageView coinImgV;
    @FXML
    private ImageView stoneImgV;
    @FXML
    private ImageView servantImgV;
    @FXML
    private ImageView redCrossImgV;
    @FXML
    private ImageView firstZero;
    @FXML
    private ImageView secondZero;
    @FXML
    private ImageView secondOne;
    @FXML
    private ImageView thirdZero;
    @FXML
    private ImageView thirdOne;
    @FXML
    private ImageView thirdTwo;
    @FXML
    private ImageView popeZeroImgV;
    @FXML
    private ImageView popeOneImgV;
    @FXML
    private ImageView popeTwoImgV;

    //strongbox label
    @FXML
    private Label shieldStrongboxLabel;
    @FXML
    private Label stoneStrongboxLabel;
    @FXML
    private Label coinStrongboxLabel;
    @FXML
    private Label servantStrongboxLabel;

    private Image leaderCard1Image;

    private Image leaderCard2Image;

    private final DropShadow highlightEffectRed = new DropShadow(6, Color.web("#f03030")); //green #65f952, red #f03030, arancione #f9dc52
    private final DropShadow highlightEffectGreen = new DropShadow(6, Color.web("#65f952")); //green #65f952, red #f03030, arancione #f9dc52

    public void setLeaderCard1(Image leaderCard1) {
        leaderCard1Image = leaderCard1;
        this.leaderCard1.setImage(leaderCard1);
    }

    public void setLeaderCard2(Image leaderCard2) {
        leaderCard2Image = leaderCard2;
        this.leaderCard2.setImage(leaderCard2);
    }

    public Image getLeaderCard1Image() {
        return leaderCard1Image;
    }

    public Image getLeaderCard2Image() {
        return leaderCard2Image;
    }

    private Node[][] imgViewResourceArray = null;
    private Node[][] labelResourceArray = null;

    private static final Image imgShield = new Image("resourcesImage/" + "shield" + ".png");
    private static final Image imgCoin = new Image("resourcesImage/" + "coin" + ".png");
    private static final Image imgStone = new Image("resourcesImage/" + "stone" + ".png");
    private static final Image imgServant = new Image("resourcesImage/" + "servant" + ".png");

    private ArrayList<ArrayList<ImageView>> warehouseDepot = new ArrayList<ArrayList<ImageView>>();

    private ArrayList<ArrayList<ImageView>> developmentCardSpaceArray = new ArrayList<ArrayList<ImageView>>();

    private ArrayList<ImageView> popeFavourTilesImgVArray = new ArrayList<ImageView>();


    @FXML
    public void initialize() {
        //TODO also possible to set myNickname in personal board and put chosen leaderCards if personalBoard.getMyNickname.equals(gui.getmyNickname) else set bakc cover of leader cards
        //Setting the back of leader cards
        String backLeaderCardPath = "cardsImage/65.png";
        setLeaderCard1(new Image(backLeaderCardPath));
        setLeaderCard2(new Image(backLeaderCardPath));

        Image img = new Image("personalBoardImage/" + "personalBoardFront" + ".png");
        imgViewPB.setPreserveRatio(true);
        imgViewPB.setImage(img);


        shieldImgV.setImage(imgShield);
        shieldImgV.setPreserveRatio(true);
        shieldImgV.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onResourceImgClick);

        coinImgV.setImage(imgCoin);
        coinImgV.setPreserveRatio(true);
        coinImgV.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onResourceImgClick);

        stoneImgV.setImage(imgStone);
        stoneImgV.setPreserveRatio(true);
        stoneImgV.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onResourceImgClick);

        servantImgV.setImage(imgServant);
        servantImgV.setPreserveRatio(true);
        servantImgV.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onResourceImgClick);

        redCrossImgV.setImage(new Image("personalBoardImage/" + "redCross" + ".png"));
        redCrossImgV.setPreserveRatio(true);
        redCrossImgV.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onResourceImgClick);

        for(int i=0; i<5; i++){
            warehouseDepot.add(new ArrayList<>());
        }
        warehouseDepot.get(0).add(firstZero);
        warehouseDepot.get(1).add(secondZero);
        warehouseDepot.get(1).add(secondOne);
        warehouseDepot.get(2).add(thirdZero);
        warehouseDepot.get(2).add(thirdOne);
        warehouseDepot.get(2).add(thirdTwo);

        popeFavourTilesImgVArray.add(popeZeroImgV);
        popeFavourTilesImgVArray.add(popeOneImgV);
        popeFavourTilesImgVArray.add(popeTwoImgV);

        popeZeroImgV.setImage(new Image("personalBoardImage/" + "popeZeroBack" + ".png"));
        popeZeroImgV.setPreserveRatio(true);
        popeZeroImgV.setId("0");
        popeOneImgV.setImage(new Image("personalBoardImage/" + "popeOneBack" + ".png"));
        popeOneImgV.setPreserveRatio(true);
        popeOneImgV.setId("0");
        popeTwoImgV.setImage(new Image("personalBoardImage/" + "popeTwoBack" + ".png"));
        popeTwoImgV.setPreserveRatio(true);
        popeTwoImgV.setId("0");

        devCardSpaceSetUp(devCardSpaceZeroGP);
        devCardSpaceSetUp(devCardSpaceOneGP);
        devCardSpaceSetUp(devCardSpaceTwoGP);
        /*for(int i=0; i<3; i++){
            developmentCardSpaceArray.add(new ArrayList<>());
        }*/

        highlightEffectRed.setSpread(1);
        highlightEffectGreen.setSpread(1);

    }

    //serve a inizializzare l'array di devCard space dal gridPane
    private void devCardSpaceSetUp(GridPane devCardSpaceGP){

        ArrayList<ImageView> singleDevCardSpaceArray = new ArrayList<>();
        for(Node node : devCardSpaceGP.getChildren())
        {
            Integer x = GridPane.getRowIndex(node);
            Integer y = GridPane.getColumnIndex(node);
            if((x != null) && (y != null)) {
                singleDevCardSpaceArray.add(GridPane.getRowIndex(node), (ImageView) node);
            }
        }
        developmentCardSpaceArray.add(singleDevCardSpaceArray);

    }

    private void onResourceImgClick(Event event){
        ImageView imageView = (ImageView) event.getTarget();
        System.out.println(GridPane.getRowIndex(imageView) + "," +GridPane.getColumnIndex(imageView));
        //todo passo la posizione a chi serve per creare il messaggio
    }

    public void updateWarehouse(List<Map<Resource, Integer>> depots){
        int k=0;

        //cancello le immagini già presenti
        for (ArrayList<ImageView> array : warehouseDepot)
            for(ImageView img : array)
                img.setImage(null);

        for(Map<Resource, Integer> map : depots){
            for(Resource x : map.keySet()){
                if(map.containsKey(x)){
                    int j = (int) map.get(x);
                    ArrayList<ImageView> array = warehouseDepot.get(k);
                    for(int i=0; i<j; i++){
                        ImageView imgV = array.get(i);
                        setWareHouseDepotImgV(imgV, x);
                    }
                }
            }
            k++;
        }
    }

    public void updateStrongbox(Map<Resource, Integer> strongbox){

        for(Resource x : strongbox.keySet()) {
            if (strongbox.containsKey(x)) {
                int j=0;
                j = (int) strongbox.get(x);
                switch (x) {
                    case SHIELD:
                        shieldStrongboxLabel.setText(Integer.toString(j));
                        break;
                    case COIN:
                        coinStrongboxLabel.setText(Integer.toString(j));
                        break;
                    case SERVANT:
                        servantStrongboxLabel.setText(Integer.toString(j));
                        break;
                    case STONE:
                        stoneStrongboxLabel.setText(Integer.toString(j));
                        break;
                }
            }
        }
    }

    private void setWareHouseDepotImgV(ImageView imgV, Resource resource){
        imgV.setId(resource.toString());
        imgV.setImage(getImageFromResource(resource));
    }

    private Image getImageFromResource(Resource resource){
        Image img = null;
        switch (resource) {
            case SHIELD : img = imgShield;
                break;
            case COIN: img = imgCoin;
                break;
            case SERVANT: img = imgServant;
                break;
            case STONE: img = imgStone;
                break;
        }
        return img;
    }

    public void setRedCrossPosition(int position){

        int x=20, y=75;

        switch (position) {
            case 0 : x=20; y=75;
                break;
            case 1 : x=50; y=75;
                break;
            case 2 : x=75; y=75;
                break;
            case 3 : x=75; y=50;
                break;
            case 4 : x=75; y=20;
                break;
            case 5 : x=102; y=20;
                break;
            case 6 : x=129; y=20;
                break;
            case 7 : x=156; y=20;
                break;
            case 8 : x=184; y=20;
                break;
            case 9 : x=211; y=20;
                break;
            case 10 : x=211; y=50;
                break;
            case 11 : x=211; y=75;
                break;
            case 12 : x=239; y=75;
                break;
            case 13 : x=266; y=75;
                break;
            case 14 : x=294; y=75;
                break;
            case 15 : x=322; y=75;
                break;
            case 16 : x=349; y=75;
                break;
            case 17 : x=349; y=50;
                break;
            case 18 : x=349; y=20;
                break;
            case 19 : x=376; y=20;
                break;
            case 20 : x=404; y=20;
                break;
            case 21 : x=431; y=20;
                break;
            case 22 : x=458; y=20;
                break;
            case 23 : x=486; y=20;
                break;
            case 24 : x=513; y=20;
                break;
        }
        redCrossImgV.setLayoutX(x);
        redCrossImgV.setLayoutY(y);
    }

    public void setPopeFavourTiles(ArrayList<Integer> popeFavourTiles){
        int k = 0;
        for(Integer x : popeFavourTiles){

            ImageView imgV = popeFavourTilesImgVArray.get(k);

            if(Integer.parseInt(imgV.getId()) != x) {
                switch (x) {
                    case 1 : imgV.setVisible(false);
                        break;
                    case 2 :
                        imgV.setImage(new Image("personalBoardImage/" + "popeZeroFront" + ".png"));
                        imgV.setId(Integer.toString(x));
                        break;
                }
            }
            k++;
        }
    }

    public void setDevelopmentCardSpace(ArrayList<ArrayList<Integer>> cardsState) {
        int k = 0;
        for (ArrayList<Integer> array : cardsState) {
            int h = 0;
            for (int i=array.size(); i>0; i--) {
                ImageView imgV = developmentCardSpaceArray.get(k).get(h);
                int x = array.get(i);
                if ((Integer.parseInt(imgV.getId()) != x) && (Integer.parseInt(imgV.getId()) != 0)){
                    imgV.setImage(new Image("cardsImage/" + x + ".png"));
                    imgV.setId(Integer.toString(x));
                }
            }
        }
    }

    public void updateTemporaryResourceMap(Map<Resource, Integer> temporaryMapResource){
        for(Resource resource : Resource.values()){
            switch (resource){
                case SHIELD:
                    if (temporaryMapResource.containsKey(resource))
                        tempShieldLabel.setText(temporaryMapResource.get(resource).toString());
                    else
                        tempShieldLabel.setText("0");
                    break;
                case STONE:
                    if (temporaryMapResource.containsKey(resource))
                        tempStoneLabel.setText(temporaryMapResource.get(resource).toString());
                    else
                        tempStoneLabel.setText("0");
                    break;
                case COIN:
                    if (temporaryMapResource.containsKey(resource))
                        tempCoinLabel.setText(temporaryMapResource.get(resource).toString());
                    else
                        tempCoinLabel.setText("0");
                    break;
                case SERVANT:
                    if (temporaryMapResource.containsKey(resource))
                        tempServantLabel.setText(temporaryMapResource.get(resource).toString());
                    else
                        tempServantLabel.setText("0");
                    break;
            }
        }
    }

    public void updateTemporaryMarbleMap(Map<Marble,Integer> temporaryMarbles){
        for(Marble marble : Marble.values()){
            switch (marble){
                case WHITEMARBLE:
                    if (temporaryMarbles.containsKey(marble))
                        whiteTempMarbleLabel.setText(temporaryMarbles.get(marble).toString());
                    else
                        whiteTempMarbleLabel.setText("0");
                    break;
                case YELLOWMARBLE:
                    if (temporaryMarbles.containsKey(marble))
                        yellowTempMarbleLabel.setText(temporaryMarbles.get(marble).toString());
                    else
                        yellowTempMarbleLabel.setText("0");
                    break;
                case BLUEMARBLE:
                    if (temporaryMarbles.containsKey(marble))
                        blueTempMarbleLabel.setText(temporaryMarbles.get(marble).toString());
                    else
                        blueTempMarbleLabel.setText("0");
                    break;
                case GREYMARBLE:
                    if (temporaryMarbles.containsKey(marble))
                        grayTempMarbleLabel.setText(temporaryMarbles.get(marble).toString());
                    else
                        grayTempMarbleLabel.setText("0");
                    break;
                case REDMARBLE:
                    if (temporaryMarbles.containsKey(marble))
                        redTempMarbleLabel.setText(temporaryMarbles.get(marble).toString());
                    else
                        redTempMarbleLabel.setText("0");
                    break;
                case PURPLEMARBLE:
                    if (temporaryMarbles.containsKey(marble))
                        purpleTempMarbleLabel.setText(temporaryMarbles.get(marble).toString());
                    else
                        purpleTempMarbleLabel.setText("0");
                    break;
            }
        }
    }

    public void updateDiscardedLeader(int numLeaderCard){
        if(numLeaderCard==1){
            leaderCard1.setEffect(highlightEffectRed);
            //leaderCard1.setImage(null);
        }if(numLeaderCard==2){
            leaderCard2.setEffect(highlightEffectRed);
            //leaderCard1.setImage(null);
        }
    }

    public void updateActivatedLeader(int numLeaderCard, int leaderCardID){
        if(numLeaderCard==1){
            leaderCard1.setImage(new Image("cardsImage/" + leaderCardID + ".png"));
            leaderCard1.setEffect(highlightEffectGreen);
        }if(numLeaderCard==2){
            leaderCard2.setImage(new Image("cardsImage/" + leaderCardID + ".png"));
            leaderCard2.setEffect(highlightEffectGreen);
        }
    }



}
