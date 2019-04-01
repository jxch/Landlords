package requestAndAnswer.request;

import requestAndAnswer.request.command.Request;
import requestAndAnswer.request.command.RoomInRequest;

public abstract class RoomInRequestInfo extends RequestInfo {
    private static final long serialVersionUID = 6014439583295557133L;
    private RoomInRequest roomInRequest;
    private String roomName;

    public RoomInRequestInfo(RoomInRequest roomInRequest, String roomName) {
        super(Request.roomInRequest);
        this.roomInRequest = roomInRequest;
        this.roomName = roomName;
    }

    public RoomInRequest getRoomInRequest() {
        return roomInRequest;
    }

    public void setRoomInRequest(RoomInRequest roomInRequest) {
        this.roomInRequest = roomInRequest;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomName() {
        return roomName;
    }
}
