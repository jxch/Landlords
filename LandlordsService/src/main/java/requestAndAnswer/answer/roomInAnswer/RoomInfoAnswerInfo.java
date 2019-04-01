package requestAndAnswer.answer.roomInAnswer;

import requestAndAnswer.answer.RoomInAnswerInfo;
import requestAndAnswer.request.command.RoomInRequest;
import requestAndAnswer.roomIn.RoomInfo;

public class RoomInfoAnswerInfo extends RoomInAnswerInfo{
    private RoomInfo roomInfo;

    public RoomInfoAnswerInfo() {
        super(RoomInRequest.roomInfo);
    }

    public RoomInfo getRoomInfo() {
        return roomInfo;
    }

    public void setRoomInfo(RoomInfo roomInfo) {
        this.roomInfo = roomInfo;
    }
}
