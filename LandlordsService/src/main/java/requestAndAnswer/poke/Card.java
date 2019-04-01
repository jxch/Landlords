package requestAndAnswer.poke;

import java.io.Serializable;
import java.util.Objects;

public class Card implements Serializable{
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return cardValue == card.cardValue && suit == card.suit;
    }

    public boolean equals(Card card){
        return cardValue == card.cardValue;
    }

    @Override
    public int hashCode() {

        return Objects.hash(cardValue);
    }
}
