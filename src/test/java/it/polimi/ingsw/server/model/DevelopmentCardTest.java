package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.DevelopmentCard;
import it.polimi.ingsw.server.model.PowerOfProduction;
import it.polimi.ingsw.server.model.enumerations.DevelopmentCardColor;
import it.polimi.ingsw.server.model.enumerations.Resource;
import it.polimi.ingsw.server.model.exceptions.InvalidParameterException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DevelopmentCardTest {
    /**
     * Tish test tests basic construction of a development card
     */
    @Test
    void constructorTest() throws InvalidParameterException {
        DevelopmentCardColor colorTest = DevelopmentCardColor.PURPLE;
        int levelTest = 1;
        Map<Resource,Integer> priceTest = new HashMap<>();
        int victoryPointsTest = 2;

        Map<Resource,Integer> costTest = new HashMap<>();
        Map<Resource,Integer> productionTest = new HashMap<>();
        PowerOfProduction powerOfProductionTest = new PowerOfProduction(costTest, productionTest);

        DevelopmentCard developmentCardTest = new DevelopmentCard(colorTest,levelTest,priceTest,victoryPointsTest,powerOfProductionTest);
        assertEquals(developmentCardTest.getColor(), DevelopmentCardColor.PURPLE);
        assertEquals(developmentCardTest.getLevel(), 1);
        assertEquals(developmentCardTest.getPrice(), priceTest);
        assertEquals(developmentCardTest.getVictoryPoints(), 2);
        assertEquals(developmentCardTest.getPowerOfProduction(), powerOfProductionTest);

        try{
            levelTest = 4;
            developmentCardTest = new DevelopmentCard(colorTest,levelTest,priceTest,victoryPointsTest,powerOfProductionTest);
        }catch (Exception exception){
            assert true;
        }
        levelTest = 1;

        try{
            priceTest = null;
            developmentCardTest = new DevelopmentCard(colorTest,levelTest,priceTest,victoryPointsTest,powerOfProductionTest);
        }catch (Exception exception){
            assert true;
        }
        priceTest = new HashMap<>();

        try{
            victoryPointsTest = -1;
            developmentCardTest = new DevelopmentCard(colorTest,levelTest,priceTest,victoryPointsTest,powerOfProductionTest);
        }catch (Exception exception){
            assert true;
        }
        victoryPointsTest = 2;

        try{
            powerOfProductionTest = null;
            developmentCardTest = new DevelopmentCard(colorTest,levelTest,priceTest,victoryPointsTest,powerOfProductionTest);
        }catch (Exception exception){
            assert true;
        }

    }
}