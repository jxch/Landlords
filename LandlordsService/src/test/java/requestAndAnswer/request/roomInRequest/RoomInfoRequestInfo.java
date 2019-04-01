package requestAndAnswer.request.roomInRequest;

import requestAndAnswer.request.RoomInRequestInfo;
import requestAndAnswer.request.command.RoomInRequest;

public class RoomInfoRequestInfo extends RoomInRequestInfo {

    public RoomInfoRequestInfo(String roomName) {
        super(RoomInRequest.roomInfo, roomName);
    }
}
