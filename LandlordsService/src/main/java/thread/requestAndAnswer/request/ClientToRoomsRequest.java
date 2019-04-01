package thread.requestAndAnswer.request;

import requestAndAnswer.request.command.RoomOutRequest;
import thread.ClientThread;

public class ClientToRoomsRequest {
    private RoomOutRequest roomOutRequest;
    private String roomName;
    private ClientThread thread;

    public ClientToRoomsRequest(RoomOutRequest roomOutRequest, ClientThread thread){
        this.roomOutRequest = roomOutRequest;
        this.thread = thread;
    }

    public RoomOutRequest getRoomOutRequest() {
        return roomOutRequest;
    }

    public void setRoomOutRequest(RoomOutRequest roomOutRequest) {
        this.roomOutRequest = roomOutRequest;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public ClientThread getThread() {
        return thread;
    }

    public void setThread(ClientThread thread) {
        this.thread = thread;
    }
}
