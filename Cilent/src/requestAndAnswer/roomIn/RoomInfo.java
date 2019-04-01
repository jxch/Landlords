package requestAndAnswer.roomIn;

import java.io.Serializable;
import java.util.HashMap;

public class RoomInfo implements Serializable {
    private HashMap<String, PlayerInfo> playersInfo;

    public void setPlayersInfo(HashMap<String, PlayerInfo> playersInfo) {
        this.playersInfo = playersInfo;
    }

    public HashMap<String, PlayerInfo> getPlayersInfo() {
        return playersInfo;
    }
}
