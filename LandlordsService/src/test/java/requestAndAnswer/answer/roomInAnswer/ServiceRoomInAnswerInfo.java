package requestAndAnswer.answer.roomInAnswer;

import requestAndAnswer.answer.AnswerInfo;
import requestAndAnswer.answer.command.Answer;
import requestAndAnswer.answer.command.RoomInAnswer;

import java.io.Serializable;

public class ServiceRoomInAnswerInfo extends AnswerInfo implements Serializable {
    private RoomInAnswer roomInAnswer;

    public ServiceRoomInAnswerInfo(RoomInAnswer roomInAnswer) {
        super(Answer.roomInAnswer);
        this.roomInAnswer = roomInAnswer;
    }

    public RoomInAnswer getRoomInAnswer() {
        return roomInAnswer;
    }

    public void setRoomInAnswer(RoomInAnswer roomInAnswer) {
        this.roomInAnswer = roomInAnswer;
    }
}
