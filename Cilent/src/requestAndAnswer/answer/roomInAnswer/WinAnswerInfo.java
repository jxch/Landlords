package requestAndAnswer.answer.roomInAnswer;

import requestAndAnswer.answer.command.roomInAnswer.AfterStartAnswer;
import requestAndAnswer.roomIn.SeatNum;

public class WinAnswerInfo extends AfterStartAnswerInfo {
    private SeatNum seatNum;
    private String username;
    private boolean win;

    public WinAnswerInfo() {
        super(AfterStartAnswer.win);
    }

    public SeatNum getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(SeatNum seatNum) {
        this.seatNum = seatNum;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
