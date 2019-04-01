package thread.requestProcess;

import account.Player;
import requestAndAnswer.RoomsInfo;
import requestAndAnswer.answer.RoomOutAnswerInfo;
import requestAndAnswer.answer.roomOutAnswer.EnterRoomAnswerInfo;
import requestAndAnswer.answer.roomOutAnswer.RoomsInfoAnswerInfo;
import requestAndAnswer.answer.command.RoomOutAnswer;
import requestAndAnswer.request.RoomOutRequestInfo;
import requestAndAnswer.request.roomOutRequest.CreateRoomRequestInfo;
import requestAndAnswer.request.roomOutRequest.EnterRoomRequestInfo;
import thread.ClientThread;
import thread.RoomThread;
import thread.RoomsThread;
import thread.requestAndAnswer.RoomsRequestAndAnswerContainer;
import thread.requestAndAnswer.answer.RoomsToClientAnswer;
import thread.requestAndAnswer.request.ClientToRoomsRequest;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.HashSet;

public class RoomOutRequestProcess {
    public static void createRoom(RoomOutRequestInfo roomOutRequestInfo, ClientToRoomsRequest clientToRoomsRequest, ClientThread thread, ObjectOutputStream outputStream) throws InterruptedException, IOException {
        RoomOutAnswerInfo roomOutAnswerInfo = new RoomOutAnswerInfo();
        CreateRoomRequestInfo createRoomRequestInfo = (CreateRoomRequestInfo) roomOutRequestInfo;

        clientToRoomsRequest.setRoomName(createRoomRequestInfo.getRoomName());
        RoomsRequestAndAnswerContainer.clientToRoomsRequest.push(clientToRoomsRequest);

        while (thread.getRoomsToClientAnswers().empty()) {
            Thread.sleep(100);
        }

        RoomsToClientAnswer roomsToClientAnswer = thread.getRoomsToClientAnswers().pop();
        RoomOutAnswer roomOutAnswer = roomsToClientAnswer.getRoomOutAnswer();

        roomOutAnswerInfo.setRoomOutAnswer(roomOutAnswer);
        outputStream.writeObject(roomOutAnswerInfo);
    }

    public static void roomsInfo(ObjectOutputStream outputStream) throws IOException {
        RoomsInfoAnswerInfo roomsInfoAnswerInfo = new RoomsInfoAnswerInfo();
        HashSet<RoomsInfo> roomsInfo = new HashSet<>();
        for (HashMap.Entry<String, RoomThread> entry : RoomsThread.getRoomThreads().entrySet()) {
            RoomsInfo room = new RoomsInfo();
            room.setRoomName(entry.getValue().getRoomName());
            room.setCount(entry.getValue().getCount());

            roomsInfo.add(room);
        }
        roomsInfoAnswerInfo.setRoomHashSet(roomsInfo);

        outputStream.writeObject(roomsInfoAnswerInfo);
    }

    public static void enterRoom(RoomOutRequestInfo roomOutRequestInfo, ObjectOutputStream outputStream, ClientThread thread) throws IOException {
        EnterRoomAnswerInfo enterRoomAnswerInfo = new EnterRoomAnswerInfo();
        EnterRoomRequestInfo enterRoomRequestInfo = (EnterRoomRequestInfo) roomOutRequestInfo;
        String roomName = enterRoomRequestInfo.getRoomName();
        if (RoomsThread.getRoomThreads().containsKey(roomName)) {
            RoomThread roomThread = RoomsThread.getRoomThreads().get(roomName);

            if (roomThread.getCount() < 3) {
                enterRoomAnswerInfo.setRoomOutAnswer(RoomOutAnswer.success);
                roomThread.addPlayer(new Player(thread));
            } else {
                enterRoomAnswerInfo.setRoomOutAnswer(RoomOutAnswer.roomIsFull);
            }
        } else {
            enterRoomAnswerInfo.setRoomOutAnswer(RoomOutAnswer.notFindTheRoom);
        }

        outputStream.writeObject(enterRoomAnswerInfo);
    }
}
