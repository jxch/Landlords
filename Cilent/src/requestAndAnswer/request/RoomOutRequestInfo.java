package requestAndAnswer.request;

import requestAndAnswer.request.command.Request;
import requestAndAnswer.request.command.RoomOutRequest;

public abstract class RoomOutRequestInfo extends RequestInfo {
    private RoomOutRequest roomOutRequest;

    public RoomOutRequestInfo(RoomOutRequest roomOutRequest) {
        super(Request.roomOutRequest);
        this.roomOutRequest = roomOutRequest;
    }

    public RoomOutRequest getRoomOutRequest() {
        return roomOutRequest;
    }
}
