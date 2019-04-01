package requestAndAnswer.answer.roomInAnswer;

import requestAndAnswer.answer.command.RoomInAnswer;
import requestAndAnswer.answer.command.roomInAnswer.BeforeStartAnswer;

public class RoomStateAnswerInfo extends ServiceRoomInAnswerInfo {
    private static final long serialVersionUID = 8603345905663532244L;
    private BeforeStartAnswer beforeStartAnswer;
    private RoomInfoAnswerInfo roomInfoAnswerInfo;

    public RoomStateAnswerInfo(BeforeStartAnswer beforeStartAnswer) {
        super(RoomInAnswer.beforeStartAnswer);
        this.beforeStartAnswer = beforeStartAnswer;
    }

    public RoomInfoAnswerInfo getRoomInfoAnswerInfo() {
        return roomInfoAnswerInfo;
    }

    public void setRoomInfoAnswerInfo(RoomInfoAnswerInfo roomInfoAnswerInfo) {
        this.roomInfoAnswerInfo = roomInfoAnswerInfo;
    }

    public BeforeStartAnswer getBeforeStartAnswer() {
        return beforeStartAnswer;
    }

    public void setBeforeStartAnswer(BeforeStartAnswer beforeStartAnswer) {
        this.beforeStartAnswer = beforeStartAnswer;
    }
}
