package thread.requestAndAnswer.answer;

import requestAndAnswer.answer.command.RoomOutAnswer;

public class RoomsToClientAnswer {
    private RoomOutAnswer roomOutAnswer;

    public RoomsToClientAnswer(RoomOutAnswer roomOutAnswer){
        this.roomOutAnswer = roomOutAnswer;
    }

    public RoomOutAnswer getRoomOutAnswer() {
        return roomOutAnswer;
    }

    public void setRoomOutAnswer(RoomOutAnswer roomOutAnswer) {
        this.roomOutAnswer = roomOutAnswer;
    }
}
