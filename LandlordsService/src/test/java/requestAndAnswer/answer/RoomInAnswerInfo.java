package requestAndAnswer.answer;

import requestAndAnswer.answer.command.Answer;
import requestAndAnswer.request.command.RoomInRequest;

public class RoomInAnswerInfo extends AnswerInfo {
    private RoomInRequest roomInRequest;

    public RoomInAnswerInfo(RoomInRequest roomInRequest) {
        super(Answer.roomInAnswer);
        this.roomInRequest = roomInRequest;
    }

    public RoomInRequest getRoomInRequest() {
        return roomInRequest;
    }

    public void setRoomInRequest(RoomInRequest roomInRequest) {
        this.roomInRequest = roomInRequest;
    }
}
