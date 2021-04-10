package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PowerOfProductionTest {

    @Test
    void PowerOfProduction(){
        HashMap<Resource,Integer> costTest = new HashMap<>();
        HashMap<Resource,Integer> productionTest = new HashMap<>();
        Resource resourceTest1 = Shield.getInstance();
        Resource resourceTest2 = Servant.getInstance();
        Resource resourceTest3 = Coin.getInstance();
        costTest.put(resourceTest1,4);
        costTest.put(resourceTest2,5);
        productionTest.put(resourceTest3,3);

        PowerOfProduction popTest = new PowerOfProduction(costTest,productionTest);
        assertEquals(costTest, popTest.getCost());
        assertEquals(productionTest, popTest.getProduction());
        try {
            popTest = new PowerOfProduction(null, null);
        }
        catch (Error nullElements){
            assert true;
        }
    }

    @Test
    void setgetCost() {
        HashMap<Resource,Integer> costTest = new HashMap<>();
        HashMap<Resource,Integer> productionTest = new HashMap<>();
        Resource resourceTest1 = Shield.getInstance();
        Resource resourceTest2 = Servant.getInstance();
        costTest.put(resourceTest1,4);
        productionTest.put(resourceTest2,3);
        PowerOfProduction popTest = new PowerOfProduction(costTest,productionTest);

        costTest.put(resourceTest2,1);
        popTest.setCost(costTest);
        assertEquals(costTest, popTest.getCost());
        try {
            popTest.setCost(null);
        }
        catch (Error nullElements){
            assert true;
        }
    }

    @Test
    void setgetProduction() {
        HashMap<Resource,Integer> costTest = new HashMap<>();
        HashMap<Resource,Integer> productionTest = new HashMap<>();
        Resource resourceTest1 = Shield.getInstance();
        Resource resourceTest2 = Servant.getInstance();
        costTest.put(resourceTest1,4);
        productionTest.put(resourceTest2,3);
        PowerOfProduction popTest = new PowerOfProduction(costTest,productionTest);

        productionTest.put(resourceTest1,1);
        popTest.setProduction(productionTest);
        assertEquals(productionTest, popTest.getProduction());
        try {
            popTest.setProduction(null);
        }
        catch (Error nullElements){
            assert true;
        }
    }
}