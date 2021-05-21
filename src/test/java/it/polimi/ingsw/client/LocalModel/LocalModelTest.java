package it.polimi.ingsw.client.LocalModel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocalModelTest {

    LocalModel localModel = new LocalModel();

    @Test
    void cliCardStringCreator() {
        localModel.cliCardStringCreator();
    }
}