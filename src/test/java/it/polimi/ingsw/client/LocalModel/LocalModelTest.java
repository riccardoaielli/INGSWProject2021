package it.polimi.ingsw.client.LocalModel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocalModelTest {

    int[][] matrix = new int[3][4];
    int k = 20;
    LocalModel localModel = new LocalModel();

    @Test
    void setCardGridTest() {
        for(int i=0; i<3; i++) {
            for (int j = 0; j < 4; j++) {
                matrix[i][j] = k++;
            }
        }

        localModel.setCardGrid(matrix);
        //localModel.printCardGrid();
        localModel.printCard(40);
    }
}