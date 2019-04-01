package requestAndAnswer.request.roomInRequest;

import requestAndAnswer.poke.Card;
import requestAndAnswer.poke.Combination;
import requestAndAnswer.request.RoomInRequestInfo;
import requestAndAnswer.request.command.RoomInRequest;

public class CardsRequestInfo extends RoomInRequestInfo {
    private Card[] cards;
    private Combination combination;
    private boolean play;

    public CardsRequestInfo(String roomName) {
        super(RoomInRequest.takeOutCards_Room, roomName);
    }

    public Card[] getCards() {
        return cards;
    }

    public void setCards(Card[] cards) {
        this.cards = cards;
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
