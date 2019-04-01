package account;

import requestAndAnswer.poke.HandCards;
import requestAndAnswer.roomIn.SeatNum;
import thread.ClientThread;

public class Player {
    private ClientThread clientThread;
    private HandCards handCards = new HandCards();
    private String username;
    private int integral;
    private SeatNum seatNum;

    public Player(ClientThread clientThread) {
        this.username = clientThread.getAccount().getUsername();
        this.clientThread = clientThread;
        this.integral = clientThread.getAccount().getIntegral();
    }

    public Player() {
    }

    public ClientThread getClientThread() {
        return clientThread;
    }

    public void setClientThread(ClientThread clientThread) {
        this.clientThread = clientThread;
    }

    public HandCards getHandCards() {
        return handCards;
    }

    public void setHandCards(HandCards handCards) {
        this.handCards = handCards;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public SeatNum getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(SeatNum seatNum) {
        this.seatNum = seatNum;
    }

    public void reset(){
        handCards.clear();
    }
}
