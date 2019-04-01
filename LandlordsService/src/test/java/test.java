import requestAndAnswer.poke.Card;
import requestAndAnswer.poke.Deck;
import requestAndAnswer.poke.HandCards;

import java.util.Arrays;

public class test {
    private int a = 1;
    public static void main(String[] args) {
        Deck deck = new Deck();
        deck.shuffle();
        Card[] cards = deck.deal(3);

        HandCards handCards = new HandCards(cards);

        Card[] cards1 = handCards.getHandCards().toArray(new Card[3]);

        System.out.println(Arrays.toString(cards1));
    }
}
