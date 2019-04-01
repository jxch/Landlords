package thread;

import account.Player;
import gameService.GameService;
import requestAndAnswer.answer.ForgetPasswordAnswerInfo;
import requestAndAnswer.answer.LoginAnswerInfo;
import requestAndAnswer.answer.RegisteredAnswerInfo;
import requestAndAnswer.answer.command.ForgetPasswordAnswer;
import requestAndAnswer.answer.command.LoginAnswer;
import requestAndAnswer.answer.command.RegisteredAnswer;
import requestAndAnswer.answer.roomInAnswer.RoomInfoAnswerInfo;
import requestAndAnswer.request.*;
import requestAndAnswer.request.command.Request;
import requestAndAnswer.request.command.RoomInRequest;
import requestAndAnswer.request.command.RoomOutRequest;
import requestAndAnswer.request.roomInRequest.CardsRequestInfo;
import requestAndAnswer.request.roomInRequest.LandlordRequestInfo;
import requestAndAnswer.roomIn.PlayerInfo;
import requestAndAnswer.roomIn.RoomInfo;
import thread.requestAndAnswer.request.ClientToRoomRequest;
import thread.requestAndAnswer.request.ClientToRoomsRequest;
import thread.requestProcess.RoomOutRequestProcess;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

class RequestProcess {
    static void processingTheRequest(Request request, RequestInfo requestInfo, ObjectOutputStream outputStream, ClientThread thread) throws IOException, InterruptedException {
        switch (request) {
            case login:
                login(requestInfo, outputStream, thread);
                break;
            case registered:
                registered(requestInfo, outputStream, thread);
                break;
            case forgetPassword:
                forgetPassword(requestInfo, outputStream, thread);
                break;
            case roomInRequest:
                processingTheRoomInRequest(requestInfo, outputStream, thread);
                break;
            case roomOutRequest:
                processingTheRoomOutRequest(requestInfo, outputStream, thread);
                break;
            default:
        }
    }

    private static void login(RequestInfo requestInfo, ObjectOutputStream outputStream, ClientThread thread) throws IOException {
        LoginAnswerInfo loginAnswerInfo = new LoginAnswerInfo();
        LoginRequestInfo loginRequestInfo = (LoginRequestInfo) requestInfo;

        if (thread.hasLoggedIn(loginRequestInfo.getUsername())) {
            loginAnswerInfo.setLoginAnswer(LoginAnswer.userHasLoggedIn);
        } else {
            LoginAnswer loginAnswer = thread.login(loginRequestInfo.getUsername(), loginRequestInfo.getPassword());

            loginAnswerInfo.setLoginAnswer(loginAnswer);
            loginAnswerInfo.setIntegral(thread.getAccount().getIntegral());
        }

        outputStream.writeObject(loginAnswerInfo);
        GameService.getClientThreadHashMap().put(loginRequestInfo.getUsername(), thread);
    }

    private static void registered(RequestInfo requestInfo, ObjectOutputStream outputStream, ClientThread thread) throws IOException {
        RegisteredAnswerInfo registeredAnswerInfo = new RegisteredAnswerInfo();
        RegisteredRequestInfo registeredRequestInfo = (RegisteredRequestInfo) requestInfo;

        RegisteredAnswer registeredAnswer = thread.registration(registeredRequestInfo.getUsername(), registeredRequestInfo.getPassword(), registeredRequestInfo.getEmail());

        registeredAnswerInfo.setRegisteredAnswer(registeredAnswer);
        registeredAnswerInfo.setIntegral(thread.getAccount().getIntegral());

        outputStream.writeObject(registeredAnswerInfo);
        GameService.getClientThreadHashMap().put(registeredRequestInfo.getUsername(), thread);
    }

    private static void forgetPassword(RequestInfo requestInfo, ObjectOutputStream outputStream, ClientThread thread) throws IOException {
        ForgetPasswordAnswerInfo forgetPasswordAnswerInfo = new ForgetPasswordAnswerInfo();
        ForgetPasswordRequestInfo forgetPasswordRequestInfo = (ForgetPasswordRequestInfo) requestInfo;

        String password = thread.getPassword(forgetPasswordRequestInfo.getUsername().trim());
        if (password == null) {
            forgetPasswordAnswerInfo.setForgetPasswordAnswer(ForgetPasswordAnswer.noSuchUser);
        } else {
            forgetPasswordAnswerInfo.setForgetPasswordAnswer(ForgetPasswordAnswer.success);
        }
        forgetPasswordAnswerInfo.setPassword(password);

        outputStream.writeObject(forgetPasswordAnswerInfo);
    }

    private static void processingTheRoomOutRequest(RequestInfo requestInfo, ObjectOutputStream outputStream, ClientThread thread) throws InterruptedException, IOException {
        RoomOutRequestInfo roomOutRequestInfo = (RoomOutRequestInfo) requestInfo;
        RoomOutRequest roomOutRequest = roomOutRequestInfo.getRoomOutRequest();
        ClientToRoomsRequest clientToRoomsRequest = new ClientToRoomsRequest(roomOutRequest, thread);

        switch (roomOutRequest) {
            case findRoom:
                break;
            case enterRoom:
                RoomOutRequestProcess.enterRoom(roomOutRequestInfo, outputStream, thread);
                break;
            case createRoom:
                RoomOutRequestProcess.createRoom(roomOutRequestInfo, clientToRoomsRequest, thread, outputStream);
                break;
            case roomInfo:

                break;
            case roomsInfo:
                RoomOutRequestProcess.roomsInfo(outputStream);
                break;
            default:
        }
    }

    private static void processingTheRoomInRequest(RequestInfo requestInfo, ObjectOutputStream outputStream, ClientThread thread) throws IOException {
        RoomInRequestInfo roomInRequestInfo = (RoomInRequestInfo) requestInfo;
        RoomInRequest roomInRequest = roomInRequestInfo.getRoomInRequest();

        switch (roomInRequest) {
            case ready:
                RoomsThread.getRoomThreads().get(roomInRequestInfo.getRoomName()).ready(thread);
                break;
            case roomInfo:
                RoomInfoAnswerInfo roomInfoAnswerInfo = new RoomInfoAnswerInfo();
                RoomInfo roomInfo = new RoomInfo();
                HashMap<String, PlayerInfo> playersInfo = new HashMap<>();
                RoomThread roomThread = RoomsThread.getRoomThreads().get(roomInRequestInfo.getRoomName());
                HashMap<ClientThread, Player> players = roomThread.getPlayers();

                for (HashMap.Entry<ClientThread, Player> entry : players.entrySet()) {
                    Player player = entry.getValue();
                    PlayerInfo playerInfo = new PlayerInfo(player.getUsername(), player.getIntegral(), player.getSeatNum());
                    playersInfo.put(entry.getKey().getAccount().getUsername(), playerInfo);
                }

                roomInfo.setPlayersInfo(playersInfo);
                roomInfoAnswerInfo.setRoomInfo(roomInfo);
                outputStream.writeObject(roomInfoAnswerInfo);
                break;
            case chat_Room:
                break;
            case pass_Room:
                break;
            case cancel_Room:
                break;
            case hosted_Room:
                break;
            case lookHideCards:
                break;
            case cancelHosted_Room:
                break;
            case displayCards_Room:
                break;
            case lookEveryOneCards:
                break;
            case takeOutCards_Room:
                CardsRequestInfo cardsRequestInfo = (CardsRequestInfo) roomInRequestInfo;
                ClientToRoomRequest clientToRoomRequestPlay = new ClientToRoomRequest(ClientToRoomRequest.ClientRequest.play);
                clientToRoomRequestPlay.setCards(cardsRequestInfo.getCards());
                clientToRoomRequestPlay.setClientThread(thread);
                clientToRoomRequestPlay.setCardsRequestInfo(cardsRequestInfo);
                RoomsThread.getRoomThreads().get(cardsRequestInfo.getRoomName()).getClientToRoomRequests().push(clientToRoomRequestPlay);
                break;
            case landlord:
                ClientToRoomRequest clientToRoomRequestLandlord = new ClientToRoomRequest(ClientToRoomRequest.ClientRequest.landlord);
                clientToRoomRequestLandlord.setLandlordRequestInfo((LandlordRequestInfo) roomInRequestInfo);
                clientToRoomRequestLandlord.setClientThread(thread);
                RoomsThread.getRoomThreads().get(roomInRequestInfo.getRoomName()).getClientToRoomRequests().push(clientToRoomRequestLandlord);
                break;
            default:
        }
    }
}
