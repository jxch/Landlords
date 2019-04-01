package requestAndAnswer.poke;

import java.io.Serializable;
import java.util.Random;

public class Deck implements Serializable {
    public static final int cardsNumber = 54;
    private Card[] deck = new Card[cardsNumber];
    private int remainingCards = 0;

    public Deck() {
        initialization();
    }

    private void initialization() {
        CardValue[] cardValues = CardValue.values();
        Suit[] suits = Suit.values();

        for (CardValue c : cardValues) {
            for (Suit s : suits) {
                deck[remainingCards++] = new Card(c, s);
            }
        }
    }

    public void shuffle() {
        Random random = new Random();

        for (int i = 0; i < remainingCards; i++) {
            int index = random.nextInt(remainingCards);
            Card temp = deck[0];
            deck[0] = deck[index];
            deck[index] = temp;
        }
    }

    public Card deal() {
        if (remainingCards > 0) {
            return deck[--remainingCards];
        } else {
            return null;
        }
    }

    public Card[] deal(int number) {
        if (remainingCards >= number) {
            Card[] cards = new Card[number];
            System.arraycopy(deck, remainingCards - number, cards, 0, number);
            remainingCards -= number;
            return cards;
        } else {
            return null;
        }
    }

    public Card[] getDeck() {
        return deck;
    }

    public int getRemainingCards() {
        return remainingCards;
    }

    public void reset(){
        remainingCards = cardsNumber;
    }

    public Card getCard(Card card){
        for (int i = 0; i < cardsNumber; i++){
            if (card.equals(deck[i])){
                return deck[i];
            }
        }

        return null;
    }

    public Card[] getCards(Card[] cards){
        Card[] myCards = new Card[cards.length];

        for (int i = 0; i < cards.length; i++){
            myCards[i] = getCard(cards[i]);
        }

        return myCards;
    }
}
