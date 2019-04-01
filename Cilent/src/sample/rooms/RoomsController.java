package sample.rooms;

import account.Account;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import requestAndAnswer.answer.RoomOutAnswerInfo;
import requestAndAnswer.answer.roomOutAnswer.EnterRoomAnswerInfo;
import requestAndAnswer.answer.roomOutAnswer.RoomsInfoAnswerInfo;
import requestAndAnswer.answer.command.RoomOutAnswer;
import requestAndAnswer.request.roomOutRequest.CreateRoomRequestInfo;
import requestAndAnswer.request.roomOutRequest.EnterRoomRequestInfo;
import requestAndAnswer.request.roomOutRequest.RoomsInfoRequestInfo;
import sample.Main;
import requestAndAnswer.RoomsInfo;
import sample.loginAndRegistered.FieldTest;
import sample.room.Room;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;

public class RoomsController {
    @FXML
    private Label integral;
    @FXML
    private Label massage;
    @FXML
    private TextField roomName;
    @FXML
    private TextField createRoomName;
    @FXML
    private Button findRoom;
    @FXML
    private Button account;
    @FXML
    private Button Standings;
    @FXML
    private VBox table;

    public void createRoom() {
        if (createRoomName.getText().trim().isEmpty()) {
            createRoomName.setPromptText("请输入房间名");
        } else {
            try {
                ObjectInputStream inputStream = Account.getObjectInputStream();
                ObjectOutputStream outputStream = Account.getObjectOutputStream();

                String roomName = createRoomName.getText().trim();
                CreateRoomRequestInfo createRoomRequestInfo = new CreateRoomRequestInfo();
                createRoomRequestInfo.setRoomName(roomName);

                outputStream.writeObject(createRoomRequestInfo);
                RoomOutAnswerInfo roomOutAnswerInfo = (RoomOutAnswerInfo) inputStream.readObject();

                createRoomAnswerProcess(roomOutAnswerInfo, roomName);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void createRoomAnswerProcess(RoomOutAnswerInfo roomOutAnswerInfo, String roomName) throws IOException {
        RoomOutAnswer roomOutAnswer = roomOutAnswerInfo.getRoomOutAnswer();

        switch (roomOutAnswer) {
            case roomAlreadyExists:
                createRoomName.setPromptText(RoomOutAnswer.roomAlreadyExists.toString());
                break;
            case success:
                Main.nextStage(createRoomName, "room/room.fxml", "room - " + roomName);
                Room.setRoomName(roomName);
                break;
            default:
        }
    }

    public void refresh() {
        table.getChildren().clear();

        try {
            ObjectInputStream inputStream = Account.getObjectInputStream();
            ObjectOutputStream outputStream = Account.getObjectOutputStream();

            RoomsInfoRequestInfo roomsInfoRequestInfo = new RoomsInfoRequestInfo();

            outputStream.writeObject(roomsInfoRequestInfo);
            RoomsInfoAnswerInfo roomsInfoAnswerInfo = (RoomsInfoAnswerInfo) inputStream.readObject();

            HashSet<RoomsInfo> roomsInfo = roomsInfoAnswerInfo.getRoomHashSet();

            tableRoom(roomsInfo);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void tableRoom(HashSet<RoomsInfo> roomsInfo) {
        for (RoomsInfo ri : roomsInfo) {
            HBox hBox = new HBox(10);
            Label roomName = new Label(ri.getRoomName());
            Label count = new Label(String.valueOf(ri.getCount()));
            Button enter = new Button("进入");
            enter.setOnAction(event -> enterRoom(ri.getRoomName()));

            hBox.getChildren().addAll(roomName, count, enter);
            table.getChildren().addAll(hBox);
        }
    }

    private void enterRoom(String roomName) {
        try {
            ObjectInputStream inputStream = Account.getObjectInputStream();
            ObjectOutputStream outputStream = Account.getObjectOutputStream();

            EnterRoomRequestInfo roomRequestInfo = new EnterRoomRequestInfo();
            roomRequestInfo.setRoomName(roomName);

            outputStream.writeObject(roomRequestInfo);

            EnterRoomAnswerInfo roomAnswerInfo = (EnterRoomAnswerInfo) inputStream.readObject();

            enterRoomAnswerProcess(roomAnswerInfo.getRoomOutAnswer(), roomName);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void enterRoomAnswerProcess(RoomOutAnswer roomOutAnswer, String roomName) throws IOException {
        switch (roomOutAnswer) {
            case roomIsFull:
                FieldTest.setFieldTestMessage(massage, roomOutAnswer.toString(), Color.RED);
                break;
            case notFindTheRoom:
                FieldTest.setFieldTestMessage(massage, roomOutAnswer.toString(), Color.RED);
                refresh();
                break;
            case success:
                Main.nextStage(createRoomName, "room/room.fxml", "room - " + roomName);
                Room.setRoomName(roomName);
                break;
            default:
        }
    }
}
