package tset;

import requestAndAnswer.poke.Card;
import requestAndAnswer.poke.CardValue;
import requestAndAnswer.poke.Suit;
import sample.room.Rules;

public class man {
    public static void main(String[] args) {


        Card[] cards = {
                new Card(CardValue.A, Suit.diamond),
                new Card(CardValue.A, Suit.club),
                new Card(CardValue.A, Suit.heard),
                new Card(CardValue.A, Suit.spade),
        };

        System.err.println(Rules.CheckCards(cards));
    }
}
