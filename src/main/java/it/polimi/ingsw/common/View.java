package it.polimi.ingsw.common;

import it.polimi.ingsw.common.utils.observe.MessageObserver;

public interface View extends MessageObserver {
    public void setNickname(String nickname);
}
