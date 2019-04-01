package requestAndAnswer.poke;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class HandCards  implements Serializable {
    private int remainingCards = 0;
    private ArrayList<Card> handCards = new ArrayList<>();

    public HandCards() {
    }

    public HandCards(Card[] cards) {
        handCards.addAll(Arrays.asList(cards));
        remainingCards += cards.length;
    }

    public void addCard(Card card) {
        handCards.add(card);
        remainingCards++;
    }

    public void addCards(Card[] cards) {
        handCards.addAll(Arrays.asList(cards));
        remainingCards += cards.length;
    }

    public void sort() {
        handCards.sort(Comparator.comparing(Card::getCarValue));
    }

    public void play(Card[] cards) {
        if (cards != null) {
            handCards.removeAll(Arrays.asList(cards));
            remainingCards -= cards.length;
        }
    }

    public int getRemainingCards() {
        return remainingCards;
    }

    public ArrayList<Card> getHandCards() {
        return handCards;
    }

    public void clear(){
        remainingCards = 0;
        handCards.clear();
    }
}
