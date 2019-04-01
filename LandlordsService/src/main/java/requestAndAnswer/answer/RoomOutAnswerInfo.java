package requestAndAnswer.answer;

import requestAndAnswer.answer.command.Answer;
import requestAndAnswer.answer.command.RoomOutAnswer;
import requestAndAnswer.request.command.RoomOutRequest;

public class RoomOutAnswerInfo extends AnswerInfo {
    private static final long serialVersionUID = 3625671199920588665L;
    private RoomOutAnswer roomOutAnswer;
    private RoomOutRequest roomOutRequest;

    public RoomOutAnswerInfo(RoomOutRequest roomOutRequest) {
        super(Answer.roomOutAnswer);
        this.roomOutRequest = roomOutRequest;
    }

    public RoomOutAnswerInfo(){
        super(Answer.roomOutAnswer);
    }

    public RoomOutAnswer getRoomOutAnswer() {
        return roomOutAnswer;
    }

    public void setRoomOutAnswer(RoomOutAnswer roomOutAnswer) {
        this.roomOutAnswer = roomOutAnswer;
    }

    public RoomOutRequest getRoomOutRequest() {
        return roomOutRequest;
    }

    public void setRoomOutRequest(RoomOutRequest roomOutRequest) {
        this.roomOutRequest = roomOutRequest;
    }
}
