package it.polimi.ingsw.model.enumerations;

/**
 * This class represents the abstract resource
 */
public enum Resource {
    COIN{
        @Override
        void dispatch() {
            System.out.println("COIN");
        }
    },
    SHIELD{
        @Override
        void dispatch() {
            System.out.println("SHIELD");
        }
    },
    SERVANT{
        @Override
        void dispatch() {
            System.out.println("SERVANT");
        }
    },
    STONE{
        @Override
        void dispatch() {
            System.out.println("STONE");
        }
    },
    FAITH{
        @Override
        void dispatch() {
            System.out.println("FAITH");
        }
    };
    abstract  void dispatch();
}
