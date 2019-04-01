package requestAndAnswer.answer.roomOutAnswer;

import requestAndAnswer.RoomsInfo;
import requestAndAnswer.answer.RoomOutAnswerInfo;
import requestAndAnswer.request.command.RoomOutRequest;

import java.io.Serializable;
import java.util.HashSet;

public class RoomsInfoAnswerInfo extends RoomOutAnswerInfo implements Serializable {
    private HashSet<RoomsInfo> roomHashSet;

    public RoomsInfoAnswerInfo() {
        super(RoomOutRequest.roomInfo);
    }

    public HashSet<RoomsInfo> getRoomHashSet() {
        return roomHashSet;
    }

    public void setRoomHashSet(HashSet<RoomsInfo> roomHashSet) {
        this.roomHashSet = roomHashSet;
    }
}
