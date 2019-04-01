package sample.room;

import account.Account;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import requestAndAnswer.answer.command.RoomInAnswer;
import requestAndAnswer.answer.command.roomInAnswer.BeforeStartAnswer;
import requestAndAnswer.answer.roomInAnswer.*;
import requestAndAnswer.poke.Card;
import requestAndAnswer.poke.Combination;
import requestAndAnswer.poke.HandCards;
import requestAndAnswer.request.roomInRequest.CardsRequestInfo;
import requestAndAnswer.request.roomInRequest.LandlordRequestInfo;
import requestAndAnswer.roomIn.SeatNum;
import sample.loginAndRegistered.FieldTest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Room implements Runnable {
    private static String roomName;
    private boolean listening = true;
    private RoomController room;
    private HandCards handCards;
    private SeatNum currentCardsSeatNum;
    private Card[] currentCards;
    private SeatNum currentSeatNum;
    private SeatNum landlordSeatNum;
    private Combination currentCombination;

    Room(RoomController room) {
        this.room = room;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream outputStream = Account.getObjectOutputStream();
            ObjectInputStream inputStream = Account.getObjectInputStream();

            while (listening) {
                ServiceRoomInAnswerInfo roomInAnswerInfo = (ServiceRoomInAnswerInfo) inputStream.readObject();
                RoomInAnswer roomInAnswer = roomInAnswerInfo.getRoomInAnswer();

                Platform.runLater(new Runnable() {
                                      @Override
                                      public void run() {
                                          Room.this.process(roomInAnswer, roomInAnswerInfo);
                                      }
                                  }
                );

                if (roomInAnswerInfo instanceof WinAnswerInfo) {
                    exit();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void process(RoomInAnswer roomInAnswer, ServiceRoomInAnswerInfo roomInAnswerInfo) {
        switch (roomInAnswer) {
            case afterStartAnswer:
                AfterStartAnswerInfo afterStartAnswerInfo = (AfterStartAnswerInfo) roomInAnswerInfo;
                afterStartProcess(afterStartAnswerInfo);
                break;
            case beforeStartAnswer:
                beforeStartProcess(roomInAnswerInfo);
                break;
            default:
        }
    }

    private void afterStartProcess(AfterStartAnswerInfo afterStartAnswerInfo) {
        switch (afterStartAnswerInfo.getAfterStartAnswer()) {
            case play:
                play(afterStartAnswerInfo);
                break;
            case Landlord:
                landlord(afterStartAnswerInfo);
                break;
            case win:
                win(afterStartAnswerInfo);
                break;
            default:
        }
    }

    private void win(AfterStartAnswerInfo afterStartAnswerInfo) {
        WinAnswerInfo winAnswerInfo = (WinAnswerInfo) afterStartAnswerInfo;
        String message = winAnswerInfo.getSeatNum().ordinal() + "号座位的" + winAnswerInfo.getUsername() + "已经出完牌了。";
        if (winAnswerInfo.isWin()) {
            message += "恭喜你赢了！";
        } else {
            message += "你输了，再接再厉。";
        }
        FieldTest.setFieldTestMessage(room.getMessage(), message, Color.ORANGE);

        recovery();
    }

    private void recovery() {
        setButtonAction(false, false);
        room.getReady().setText("准备");
        room.getReady().setOnAction(event -> room.ready());
    }

    private void exit() {
        listening = false;
    }

    private void landlord(AfterStartAnswerInfo afterStartAnswerInfo) {
        LandlordAnswerInfo landlordAnswerInfo = (LandlordAnswerInfo) afterStartAnswerInfo;
        currentSeatNum = landlordAnswerInfo.getSeatNum();
        boolean isStart = landlordAnswerInfo.isStart();
        setLandlord(isStart, landlordAnswerInfo);

        if (landlordAnswerInfo.isStart()) {
            if (currentSeatNum == room.getSeatNum()) {
                setButtonAction(true, true);
            } else {
                setButtonAction(true, false);

            }
            currentCardsSeatNum = currentSeatNum;
            landlordSeatNum = landlordAnswerInfo.getSeatNum();

            room.getLandlordsCards().getChildren().clear();
            Card[] cards = landlordAnswerInfo.getCards();
            for (Card c : cards) {
                ImageView imageView = new ImageView(room.cardURL(c));
                room.getLandlordsCards().getChildren().add(imageView);
            }

            if (landlordSeatNum == room.getSeatNum()) {
                handCards.getHandCards().addAll(Arrays.asList(cards));
                handCards.sort();
                room.deal(handCards.getHandCards().toArray(new Card[handCards.getHandCards().size()]));
            }

            setCardsRemaining();
        } else if (currentSeatNum == room.getSeatNum()) {
            setButtonAction(false, true);
        } else {
            setButtonAction(false, false);
        }
    }

    private void setCardsRemaining(SeatNum seatNum, int num) {
        if ((seatNum.ordinal() + 1) % 3 == room.getSeatNum().ordinal()) {
            num = Integer.parseInt(room.getCardsRemainingLeft().getText()) - num;
            FieldTest.setFieldTestMessage(room.getCardsRemainingLeft(), String.valueOf(num), Color.ORANGE);
        } else if (seatNum == room.getSeatNum()) {
            num = Integer.parseInt(room.getCardsRemaining().getText()) - num;
            FieldTest.setFieldTestMessage(room.getCardsRemaining(), String.valueOf(num), Color.ORANGE);
        } else {
            num = Integer.parseInt(room.getCardsRemainingRight().getText()) - num;
            FieldTest.setFieldTestMessage(room.getCardsRemainingRight(), String.valueOf(num), Color.ORANGE);
        }
    }

    private void setCardsRemaining() {
        setLabel("17", room.getCardsRemaining(), room.getCardsRemainingLeft(), room.getCardsRemainingRight());
        if ((landlordSeatNum.ordinal() + 1) % 3 == room.getSeatNum().ordinal()) {
            FieldTest.setFieldTestMessage(room.getCardsRemainingLeft(), String.valueOf(20), Color.ORANGE);
        } else if (landlordSeatNum == room.getSeatNum()) {
            FieldTest.setFieldTestMessage(room.getCardsRemaining(), String.valueOf(20), Color.ORANGE);
        } else {
            FieldTest.setFieldTestMessage(room.getCardsRemainingRight(), String.valueOf(20), Color.ORANGE);
        }
    }

    private void setLandlord(boolean start, LandlordAnswerInfo landlordAnswerInfo) {
        setLabel("", room.getLandlord(), room.getLandlordLeft(), room.getLandlordRight());
        String label;
        if (start) {
            label = "地主";
        } else {
            label = "叫地主";
        }

        SeatNum seatNum = room.getSeatNum();
        SeatNum landlord = landlordAnswerInfo.getSeatNum();
        if (landlord == seatNum) {
            FieldTest.setFieldTestMessage(room.getLandlord(), label, Color.ORANGE);
        } else if ((landlord.ordinal() + 1) % 3 == seatNum.ordinal()) {
            FieldTest.setFieldTestMessage(room.getLandlordLeft(), label, Color.ORANGE);
        } else {
            FieldTest.setFieldTestMessage(room.getLandlordRight(), label, Color.ORANGE);
        }
    }

    private void setLabel(String s, Label... labels) {
        for (Label label : labels) {
            label.setText(s);
        }
    }

    private void setButtonAction(boolean start, boolean set) {
        Platform.runLater(new Runnable() {
                              @Override
                              public void run() {
                                  if (set) {
                                      if (start) {
                                          room.getCards().setText("出牌");
                                          room.getPass().setText("不要");

                                          room.getCards().setOnAction(event -> {
                                              cards();
                                          });
                                          room.getPass().setOnAction(event -> {
                                              pass();
                                          });
                                      } else {
                                          room.getCards().setText("叫地主");
                                          room.getPass().setText("不叫");

                                          room.getCards().setOnAction(event -> {
                                              landlord();
                                          });
                                          room.getPass().setOnAction(event -> {
                                              noLandlord();
                                          });
                                      }
                                  } else {
                                      room.getCards().setText("等待");
                                      room.getPass().setText("等待");

                                      room.getCards().setOnAction(event -> {
                                      });
                                      room.getPass().setOnAction(event -> {
                                      });
                                  }
                              }
                          }
        );
    }

    private void cards() {//出牌
        ArrayList<Card> currentCardsClicked = room.getCurrentCards();
        currentCardsClicked.sort(Comparator.comparing(Card::getCarValue));

        Card[] cards = currentCardsClicked.toArray(new Card[currentCardsClicked.size()]);
        Combination combination = Rules.CheckCards(cards);

        System.err.println("clicked :: " + currentCardsClicked.size() + ". cards :: " + cards.length + ". combination :: " + combination);
        System.err.println("meCurrSeat :: " + room.getSeatNum() + ". currSeat :: " + currentCardsSeatNum + ". currCards :: " + (currentCards == null ? "null" : currentCards.length) + ". currCom :: " + currentCombination);

        if (currentCardsSeatNum == room.getSeatNum()) {
            if (combination != null) {
                play(currentCardsClicked, cards, combination);
            } else {
                FieldTest.setFieldTestMessage(room.getMessage(), "请重新选择 - 无此牌型", Color.RED);
            }
        } else {
            if (currentCombination == combination && play(cards)) {
                play(currentCardsClicked, cards, combination);
            } else if (combination == Combination.bomb) {
                if (currentCombination != Combination.rocket && currentCombination != combination) {
                    play(currentCardsClicked, cards, combination);
                }else if (currentCombination != Combination.rocket && play(cards)){
                    play(currentCardsClicked, cards, combination);
                }
            } else if (combination == Combination.rocket) {
                play(currentCardsClicked, cards, combination);
            } else {
                FieldTest.setFieldTestMessage(room.getMessage(), "请重新选择 - 此牌型无法打出", Color.RED);
            }
        }

        System.err.println("handCard :: " + handCards.getHandCards().size());
    }

    private boolean play(Card[] cards) {//比较拍的大小
        int flag = Rules.getCarsValue(cards);

        if (flag == -1){
            int sum = 0;
            for (Card c : cards) {
                sum += c.getCarValue().ordinal();
            }
            int f = 0;
            for (Card c :
                    currentCards) {
                f += c.getCarValue().ordinal();
            }
            return (cards.length == currentCards.length) && (sum > f);
        }else {
            return (cards.length == currentCards.length) && (flag > Rules.getCarsValue(currentCards));
        }


    }

    private void pass() {//过牌
        boolean f = currentSeatNum != room.getSeatNum();

        System.err.println("pass :: " + f);

        if (currentCardsSeatNum != room.getSeatNum()) {
            CardsRequestInfo cardsRequestInfo = new CardsRequestInfo(roomName);
            cardsRequestInfo.setPlay(false);

            try {
                Account.getObjectOutputStream().writeObject(cardsRequestInfo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void play(ArrayList<Card> currentCardsClicked, Card[] cards, Combination combination) {//向服务端发牌
        CardsRequestInfo cardsRequestInfo = new CardsRequestInfo(roomName);
//        Card[] cards = currentCardsClicked.toArray(new Card[currentCardsClicked.size()]);
        cardsRequestInfo.setCards(cards);
        cardsRequestInfo.setPlay(true);
        cardsRequestInfo.setCombination(combination);

        currentCardsClicked.clear();
        handCards.play(cards);
        room.deal(handCards.getHandCards().toArray(new Card[handCards.getHandCards().size()]));

        try {
            Account.getObjectOutputStream().writeObject(cardsRequestInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void landlord() {
        try {
            ObjectOutputStream outputStream = Account.getObjectOutputStream();

            LandlordRequestInfo landlordRequestInfo = new LandlordRequestInfo(roomName);
            landlordRequestInfo.setLandlord(true);

            outputStream.writeObject(landlordRequestInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void noLandlord() {
        try {
            ObjectOutputStream outputStream = Account.getObjectOutputStream();

            LandlordRequestInfo landlordRequestInfo = new LandlordRequestInfo(roomName);
            landlordRequestInfo.setLandlord(false);

            outputStream.writeObject(landlordRequestInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void play(AfterStartAnswerInfo afterStartAnswerInfo) {
        PlayersStateAnswerInfo playersStateAnswerInfo = (PlayersStateAnswerInfo) afterStartAnswerInfo;
        Card[] cards = playersStateAnswerInfo.getCards();
        SeatNum seatNum = playersStateAnswerInfo.getSeatNum();

        if (playersStateAnswerInfo.isDeal()) {
            handCards = new HandCards(cards);
            room.deal(cards);
            setButtonAction(true, false);
        } else if (playersStateAnswerInfo.isPlay()) {
            currentCards = cards;
            currentCombination = playersStateAnswerInfo.getCombination();
            currentCardsSeatNum = seatNum;

            room.displayCards(cards);
            setCardsRemaining(seatNum, cards.length);
        }
        currentSeatNum = seatNum;
//        setPlayLabelTest(seatNum, Color.ORANGE);
        if ((seatNum.ordinal() + 1) % 3 == room.getSeatNum().ordinal()) {
            setButtonAction(true, true);
        } else {
            setButtonAction(true, false);
        }
    }

    private void setPlayLabelTest(SeatNum seatNum, Color color) {
        setLabel("", room.getPlay(), room.getPlayRight(), room.getPlayLeft());

        if (seatNum == room.getSeatNum()) {
            FieldTest.setFieldTestMessage(room.getPlayRight(), "出牌", Color.RED);
        } else if ((seatNum.ordinal() + 1) % 3 == room.getSeatNum().ordinal()) {
            FieldTest.setFieldTestMessage(room.getPlay(), "出牌", Color.RED);
        } else {
            FieldTest.setFieldTestMessage(room.getLandlordLeft(), "出牌", Color.RED);
        }
    }

    private void beforeStartProcess(ServiceRoomInAnswerInfo roomInAnswerInfo) {

        RoomStateAnswerInfo roomStateAnswerInfo = (RoomStateAnswerInfo) roomInAnswerInfo;
        BeforeStartAnswer beforeStartAnswer = roomStateAnswerInfo.getBeforeStartAnswer();

        switch (beforeStartAnswer) {
            case start:
                break;
            case wait:
                break;
            case someoneLeft:
                room.roomInfoProcess(roomStateAnswerInfo.getRoomInfoAnswerInfo());
                break;
            case someoneEnters:
                room.roomInfoProcess(roomStateAnswerInfo.getRoomInfoAnswerInfo());
            default:
        }
    }

    public void setListening(boolean listening) {
        this.listening = listening;
    }

    public static String getRoomName() {
        return roomName;
    }

    public static void setRoomName(String roomName) {
        Room.roomName = roomName;
    }
}
