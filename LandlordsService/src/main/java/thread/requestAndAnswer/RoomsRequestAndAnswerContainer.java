package thread.requestAndAnswer;

import thread.requestAndAnswer.answer.RoomToRoomsAnswer;
import thread.requestAndAnswer.answer.RoomsToClientAnswer;
import thread.requestAndAnswer.answer.RoomsToRoomAnswer;
import thread.requestAndAnswer.request.ClientToRoomsRequest;
import thread.requestAndAnswer.request.RoomToRoomsRequest;

import java.util.HashMap;
import java.util.Stack;

public interface RoomsRequestAndAnswerContainer {
    Stack<ClientToRoomsRequest> clientToRoomsRequest = new Stack<ClientToRoomsRequest>();
    Stack<RoomToRoomsRequest> roomToRoomsRequest = new Stack<RoomToRoomsRequest>();
//    HashMap<String, RoomsToClientAnswer> roomsToClientContainer = new HashMap<String, RoomsToClientAnswer>();
//    HashMap<String, RoomsToRoomAnswer> roomsToRoomContainer = new HashMap<String, RoomsToRoomAnswer>();
    Stack<RoomToRoomsAnswer> roomToRoomsAnswer = new Stack<RoomToRoomsAnswer>();
}
