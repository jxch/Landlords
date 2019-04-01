package requestAndAnswer.answer.roomInAnswer;

import requestAndAnswer.answer.command.RoomInAnswer;
import requestAndAnswer.poke.Card;
import requestAndAnswer.roomIn.SeatNum;

public class PlayersStateAnswerInfo extends ServiceRoomInAnswerInfo {
    private boolean deal = false;
    private SeatNum seatNum;
    private Card[] cards;

    public PlayersStateAnswerInfo() {
        super(RoomInAnswer.afterStartAnswer);
    }

    public SeatNum getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(SeatNum seatNum) {
        this.seatNum = seatNum;
    }

    public Card[] getCards() {
        return cards;
    }

    public void setCards(Card[] cards) {
        this.cards = cards;
    }

    public boolean isDeal() {
        return deal;
    }

    public void setDeal(boolean deal) {
        this.deal = deal;
    }
}
