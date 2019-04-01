package thread;

import account.Player;
import gameService.GameService;
import requestAndAnswer.answer.command.RoomOutAnswer;
import requestAndAnswer.request.command.RoomOutRequest;
import thread.requestAndAnswer.RoomsRequestAndAnswerContainer;
import thread.requestAndAnswer.answer.RoomsToClientAnswer;
import thread.requestAndAnswer.request.ClientToRoomsRequest;
import thread.requestAndAnswer.request.RoomToRoomsRequest;

import java.util.HashMap;

public class RoomsThread implements Runnable, RoomsRequestAndAnswerContainer {
    private static HashMap<String, RoomThread> roomThreads = new HashMap<>();
    private boolean listening = true;

    @Override
    public void run() {
        while (listening) {
            if (!clientToRoomsRequest.empty()) {
                ClientToRoomsRequest clientRequest = clientToRoomsRequest.pop();
                RoomOutRequest roomOutRequest = clientRequest.getRoomOutRequest();

                clientToRoomsRequestProcess(roomOutRequest, clientRequest);
            }

            if (!roomToRoomsRequest.empty()){
                RoomToRoomsRequest roomRequest = roomToRoomsRequest.pop();
                // TODO: 2018/1/7
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void clientToRoomsRequestProcess(RoomOutRequest roomOutRequest, ClientToRoomsRequest clientRequest) {
        switch (roomOutRequest) {
            case createRoom:
                createRoom(clientRequest);
                break;
            case roomsInfo:

                break;
            case roomInfo:
                break;
            case enterRoom:
                break;
            case findRoom:
                break;
            default:
        }
    }

    private void createRoom(ClientToRoomsRequest clientRequest){
        if (RoomsThread.getRoomThreads().containsKey(clientRequest.getRoomName())){
            clientRequest.getThread().getRoomsToClientAnswers().push(new RoomsToClientAnswer(RoomOutAnswer.roomAlreadyExists));
        }else {
            RoomThread roomThread = new RoomThread(clientRequest.getRoomName());
            Player player = new Player(clientRequest.getThread());
            player.setIntegral(clientRequest.getThread().getAccount().getIntegral());

            clientRequest.getThread().getRoomsToClientAnswers().push(new RoomsToClientAnswer(RoomOutAnswer.success));

            roomThread.addPlayer(player);
            Thread room = new Thread(roomThread);
            room.start();

            RoomsThread.getRoomThreads().put(clientRequest.getRoomName(), roomThread);
            GameService.getThreadHashMap().put(roomThread.toString(), room);

        }
    }

    public void setListening(boolean listening) {
        this.listening = listening;
    }

    public static HashMap<String, RoomThread> getRoomThreads() {
        return roomThreads;
    }

    @Override
    public String toString() {
        return "RoomsThread{" +
                "room number=" + roomThreads.size() +
                '}';
    }
}
