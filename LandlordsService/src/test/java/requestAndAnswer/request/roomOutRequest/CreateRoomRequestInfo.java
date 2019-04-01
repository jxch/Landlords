package requestAndAnswer.request.roomOutRequest;

import requestAndAnswer.request.RoomOutRequestInfo;
import requestAndAnswer.request.command.RoomOutRequest;

public class CreateRoomRequestInfo extends RoomOutRequestInfo{
    private String roomName;

    public CreateRoomRequestInfo() {
        super(RoomOutRequest.createRoom);
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
