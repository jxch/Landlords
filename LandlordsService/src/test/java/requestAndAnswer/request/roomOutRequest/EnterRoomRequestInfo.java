package requestAndAnswer.request.roomOutRequest;

import requestAndAnswer.request.RoomOutRequestInfo;
import requestAndAnswer.request.command.RoomOutRequest;

public class EnterRoomRequestInfo extends RoomOutRequestInfo {
    private String roomName;

    public EnterRoomRequestInfo() {
        super(RoomOutRequest.enterRoom);
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
