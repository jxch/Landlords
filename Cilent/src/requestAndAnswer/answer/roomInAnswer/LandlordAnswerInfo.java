package requestAndAnswer.answer.roomInAnswer;

import requestAndAnswer.answer.command.roomInAnswer.AfterStartAnswer;
import requestAndAnswer.poke.Card;
import requestAndAnswer.roomIn.SeatNum;

public class LandlordAnswerInfo extends AfterStartAnswerInfo {
    private SeatNum seatNum;
    private boolean start = false;
    private Card[] cards;

    public LandlordAnswerInfo() {
        super(AfterStartAnswer.Landlord);
    }

    public SeatNum getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(SeatNum seatNum) {
        this.seatNum = seatNum;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public Card[] getCards() {
        return cards;
    }

    public void setCards(Card[] cards) {
        this.cards = cards;
    }
}
