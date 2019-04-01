package requestAndAnswer;

import java.io.Serializable;

public class RoomsInfo implements Serializable{
    private String roomName;
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
