package requestAndAnswer.answer.roomOutAnswer;

import requestAndAnswer.answer.RoomOutAnswerInfo;
import requestAndAnswer.request.command.RoomOutRequest;

public class EnterRoomAnswerInfo extends RoomOutAnswerInfo {

    public EnterRoomAnswerInfo() {
        super(RoomOutRequest.enterRoom);
    }
}
