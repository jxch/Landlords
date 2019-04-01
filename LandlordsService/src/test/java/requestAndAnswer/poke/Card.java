package requestAndAnswer.poke;

import java.io.Serializable;

public class Card  implements Serializable {
    private CardValue cardValue;
    private Suit suit;

    public static int getValue(Card card){
        return card.cardValue.ordinal() + 3;
    }

    public Card(CardValue cardValue, Suit suit){
        this.cardValue = cardValue;
        this.suit = suit;
    }

    public CardValue getCarValue() {
        return cardValue;
    }

    public void setNumber(CardValue cardValue) {
        this.cardValue = cardValue;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }
}
