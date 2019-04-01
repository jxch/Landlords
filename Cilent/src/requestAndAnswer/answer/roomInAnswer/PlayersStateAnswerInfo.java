package requestAndAnswer.answer.roomInAnswer;

import requestAndAnswer.answer.command.roomInAnswer.AfterStartAnswer;
import requestAndAnswer.poke.Card;
import requestAndAnswer.poke.Combination;
import requestAndAnswer.roomIn.SeatNum;

public class PlayersStateAnswerInfo extends AfterStartAnswerInfo {
    private boolean deal = false;
    private SeatNum seatNum;
    private Combination combination;
    private Card[] cards;
    private boolean play;

    public PlayersStateAnswerInfo() {
        super(AfterStartAnswer.play);
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

    public Combination getCombination() {
        return combination;
    }

    public void setCombination(Combination combination) {
        this.combination = combination;
    }

    public boolean isPlay() {
        return play;
    }

    public void setPlay(boolean play) {
        this.play = play;
    }
}
