package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.PowerOfProduction;
import it.polimi.ingsw.server.model.enumerations.Resource;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PowerOfProductionTest {

    /**
     * This test tests setting and getting cost of a powerOfProduction
     */
    @Test
    void setgetCost() throws Exception{
        Map<Resource,Integer> costTest = new HashMap<>();
        Map<Resource,Integer> productionTest = new HashMap<>();
        Resource resourceTest1 = Resource.SHIELD;
        Resource resourceTest2 = Resource.SERVANT;
        costTest.put(resourceTest1,4);
        productionTest.put(resourceTest2,3);
        PowerOfProduction popTest = new PowerOfProduction(costTest,productionTest);

        costTest.put(resourceTest2,1);
        popTest.setCost(costTest);
        assertEquals(costTest, popTest.getCost());

        try {
            popTest.setCost(null);
        }
        catch (Exception exception){
            assert true;
        }
    }

    /**
     * This test tests setting and getting production of a powerOfProduction
     */
    @Test
    void setgetProduction() throws Exception{
        Map<Resource,Integer> costTest = new HashMap<>();
        Map<Resource,Integer> productionTest = new HashMap<>();
        Resource resourceTest1 = Resource.SHIELD;
        Resource resourceTest2 = Resource.SERVANT;
        costTest.put(resourceTest1,4);
        productionTest.put(resourceTest2,3);
        PowerOfProduction popTest = new PowerOfProduction(costTest,productionTest);

        productionTest.put(resourceTest1,1);
        popTest.setProduction(productionTest);
        assertEquals(productionTest, popTest.getProduction());
        try {
            popTest.setProduction(null);
        }
        catch (Exception exception){
            assert true;
        }
    }
}