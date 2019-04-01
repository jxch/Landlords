package sample.room;

import account.Account;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import requestAndAnswer.answer.roomInAnswer.RoomInfoAnswerInfo;
import requestAndAnswer.poke.Card;
import requestAndAnswer.request.command.RoomInRequest;
import requestAndAnswer.request.roomInRequest.RoomInfoRequestInfo;
import requestAndAnswer.roomIn.PlayerInfo;
import requestAndAnswer.roomIn.SeatNum;
import sample.loginAndRegistered.FieldTest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class RoomController {
    @FXML
    private Label landlordLeft;
    @FXML
    private Label landlordRight;
    @FXML
    private Label landlord;

    public Label getLandlordLeft() {
        return landlordLeft;
    }

    public Label getLandlordRight() {
        return landlordRight;
    }

    public Label getLandlord() {
        return landlord;
    }

    @FXML
    private Label play;
    @FXML
    private Label playLeft;
    @FXML
    private Label playRight;

    public Label getPlay() {
        return play;
    }

    public Label getPlayLeft() {
        return playLeft;
    }

    public Label getPlayRight() {
        return playRight;
    }

    @FXML
    private Label integral;
    @FXML
    private Label integralLeft;
    @FXML
    private Label integralRight;
    @FXML
    private Label username;
    @FXML
    private Label usernameLeft;
    @FXML
    private Label usernameRight;
    @FXML
    private Label timeRemaining;
    @FXML
    private Label timeRemainingLeft;
    @FXML
    private Label timeRemainingRight;
    @FXML
    private Label cardsRemaining;
    @FXML
    private Label cardsRemainingLeft;
    @FXML
    private Label cardsRemainingRight;
    @FXML
    private Label message;

    public Label getMessage() {
        return message;
    }

    public Label getCardsRemaining() {
        return cardsRemaining;
    }

    public Label getCardsRemainingLeft() {
        return cardsRemainingLeft;
    }

    public Label getCardsRemainingRight() {
        return cardsRemainingRight;
    }

    @FXML
    private HBox poke;
    @FXML
    private Button ready;
    @FXML
    private Button cards;
    @FXML
    private Button pass;
    @FXML
    private HBox landlordsCards;
    @FXML
    private HBox playCards;

    public HBox getLandlordsCards() {
        return landlordsCards;
    }

    public HBox getPlayCards() {
        return playCards;
    }

    public Button getCards() {
        return cards;
    }

    public Button getPass() {
        return pass;
    }

    public Button getReady() {
        return ready;
    }

    private ArrayList<Card> currentCards = new ArrayList<>();
    private HashMap<ImageView, Card> imageViewCardHashMap = new HashMap<>();

    public ArrayList<Card> getCurrentCards() {
        return currentCards;
    }

    private SeatNum seatNum;

    public void ready() {
        try {
            ObjectInputStream inputStream = Account.getObjectInputStream();
            ObjectOutputStream outputStream = Account.getObjectOutputStream();

            RoomInfoRequestInfo roomInfoRequestInfo = new RoomInfoRequestInfo(Room.getRoomName());
            roomInfoRequestInfo.setRoomInRequest(RoomInRequest.ready);

            outputStream.writeObject(roomInfoRequestInfo);
//            RoomInfoAnswerInfo roomInfoAnswerInfo = (RoomInfoAnswerInfo) inputStream.readObject();
//            roomInfoProcess(roomInfoAnswerInfo);

            Room room = new Room(this);
            Thread roomThread = new Thread(room);
            roomThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

        ready.setText("取消");
        ready.setOnAction(event -> {
            // TODO: 2018/1/8
        });
        landlordsCards.getChildren().clear();
        message.setText("");
        playCards.getChildren().clear();
        cardsRemaining.setText("");
        cardsRemainingLeft.setText("");
        cardsRemainingRight.setText("");
    }

    public void roomInfoProcess(RoomInfoAnswerInfo roomInfoAnswerInfo) {
        HashMap<String, PlayerInfo> playersInfo = roomInfoAnswerInfo.getRoomInfo().getPlayersInfo();
        PlayerInfo me = playersInfo.get(Account.getAccount().getUsername());
        SeatNum seatNum = me.getSeatNum();
        this.seatNum = seatNum;

        FieldTest.setFieldTestMessage(username, me.getPlayerName(), Color.BLUE);
        FieldTest.setFieldTestMessage(integral, String.valueOf(me.getIntegral()), Color.GREEN);

        switch (seatNum) {
            case one:
                seat(me, playersInfo, SeatNum.two);
                break;
            case two:
                seat(me, playersInfo, SeatNum.three);
                break;
            case three:
                seat(me, playersInfo, SeatNum.one);
                break;
            default:
        }
    }

    private void seat(PlayerInfo me, HashMap<String, PlayerInfo> playersInfo, SeatNum nextSeat) {
        for (HashMap.Entry<String, PlayerInfo> entry : playersInfo.entrySet()) {
            PlayerInfo playerInfo = entry.getValue();

            if (playerInfo != me && nextSeat == playerInfo.getSeatNum()) {
                FieldTest.setFieldTestMessage(usernameRight, playerInfo.getPlayerName(), Color.BLUE);
                FieldTest.setFieldTestMessage(integralRight, String.valueOf(playerInfo.getIntegral()), Color.GREEN);
            } else if (playerInfo != me) {
                FieldTest.setFieldTestMessage(usernameLeft, playerInfo.getPlayerName(), Color.BLUE);
                FieldTest.setFieldTestMessage(integralLeft, String.valueOf(playerInfo.getIntegral()), Color.GREEN);
            }
        }
    }

    public void deal(Card[] cards) {
        poke.getChildren().clear();
        imageViewCardHashMap.clear();
        currentCards.clear();

        for (Card c : cards) {
            ImageView imageView = new ImageView(cardURL(c));
            imageViewCardHashMap.put(imageView, c);
            double height = imageView.getFitHeight();

            imageView.setOnMousePressed(event -> {
                if (imageView.getFitHeight() == 190) {
                    imageView.setFitHeight(height);
                    this.currentCards.remove(imageViewCardHashMap.get(imageView));
                } else {
                    imageView.setFitHeight(190);
                    this.currentCards.add(imageViewCardHashMap.get(imageView));
                }
            });

            poke.getChildren().add(imageView);
        }

//        for (int i = 0; i < 3; i++) {
//            landlordsCards.getChildren().add(new ImageView("/sample/room/image/landlordsCards.jpg"));
//        }
    }

    public String cardURL(Card card) {
        return "/sample/room/image/" + card.getSuit().getSuit() + card.getCarValue().getValue() + ".jpg";
    }

    public void displayCards(Card[] cards){
        playCards.getChildren().clear();
        for (Card c : cards){
            ImageView imageView = new ImageView(cardURL(c));
            playCards.getChildren().add(imageView);
        }
    }

    public SeatNum getSeatNum() {
        return seatNum;
    }
}
