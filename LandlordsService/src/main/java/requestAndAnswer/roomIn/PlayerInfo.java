package requestAndAnswer.roomIn;

import java.io.Serializable;

public class PlayerInfo implements Serializable {
    private String playerName;
    private int integral;
    private int cardsRemaining;
    private int timeRemaining;
    private SeatNum seatNum;

    public PlayerInfo(String playerName, int integral, SeatNum seatNum){
        this.playerName = playerName;
        this.integral = integral;
        this.seatNum = seatNum;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public int getCardsRemaining() {
        return cardsRemaining;
    }

    public void setCardsRemaining(int cardsRemaining) {
        this.cardsRemaining = cardsRemaining;
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(int timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public SeatNum getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(SeatNum seatNum) {
        this.seatNum = seatNum;
    }
}
