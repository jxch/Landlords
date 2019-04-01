package sample.room;

import requestAndAnswer.poke.Card;
import requestAndAnswer.poke.CardValue;
import requestAndAnswer.poke.Combination;
import requestAndAnswer.poke.Suit;

public class Rules {

    public static Combination CheckCards(Card[] cards) {
        if (isaCard(cards)) {
            return Combination.aCard;
        }
        if (isPairs(cards)) {
            return Combination.pairs;
        }
        if (isThreeCards(cards)) {
            return Combination.threeCards;
        }
        if (isTripletWithAnAttachedCard(cards)) {
            if (isBomb(cards)) {
                return Combination.bomb;
            }
            return Combination.tripletWithAnAttachedCard;
        }

        if (istripletWithAnAttachedPair(cards)) {
            return Combination.tripletWithAnAttachedPair;
        }
        if (isSequence(cards)) {
            return Combination.sequence;
        }
        if (isSequenceOfPairs(cards)) {
            return Combination.sequenceOfPairs;
        }

        if (isRocke0t(cards)) {
            return Combination.rocket;
        }
        if (isQuadplexSet(cards)) {
            return Combination.quadplexSet;
        }
        if (isSequenceOfTriplets(cards)) {
            return Combination.sequenceOfTriplets;
        }
        return null;
    }

    public static int getCarsValue(Card[] cards){
        if(isTripletWithAnAttachedCard(cards)){
            return cards[1].getCarValue().ordinal()*3;
        }else if(istripletWithAnAttachedPair(cards)){
            return cards[2].getCarValue().ordinal()*3;
        }
        else if(isSequenceOfTriplets(cards)){
            for (int i = 0; i < 7; i++) {
                if (cards[i].equals(cards[i + 2]) && cards[i + 3].equals(cards[i + 5]) && cards[i + 3].getCarValue().ordinal() - cards[i + 2].getCarValue().ordinal() == 1) {
                    return cards[i].getCarValue().ordinal()*3;
                }
            }
        }
            return -1;
    }

    public static boolean isaCard(Card[] cards) {
        if (cards.length == 1) {
            return true;
        }
        return false;
    }

    public static boolean isPairs(Card[] cards) {
        if (cards.length == 2 && cards[0].equals(cards[1])) {
            return true;
        }
        return false;
    }

    public static boolean isThreeCards(Card[] cards) {
        if (cards.length == 3 && cards[0].equals(cards[2])) {
            return true;
        }
        return false;
    }

    public static boolean isBomb(Card[] cards) {
        if (cards.length == 4 && cards[0].equals(cards[3])) {
            return true;
        }
        return false;
    }

    public static boolean isTripletWithAnAttachedCard(Card[] cards) {
        if (cards.length == 4 && (cards[0].equals(cards[2]) || cards[3].equals(cards[1]))) {
            return true;
        }
        return false;
    }

    public static boolean istripletWithAnAttachedPair(Card[] cards) {
        if (cards.length == 5 && ((cards[0].equals(cards[2]) && cards[3].equals(cards[4]) && !cards[2].equals(cards[3])) || cards[0].equals(cards[1]) && cards[2].equals(cards[4]) && !cards[1].equals(cards[2]))) {
            return true;
        }
        return false;
    }

    public static boolean isSequence(Card[] cards) {
        boolean flag = true;
        if (cards.length > 4 && CardValue.A.ordinal() >= cards[cards.length - 1].getCarValue().ordinal()) {
            for (int i = 0; i < cards.length - 1; i++) {
                if (cards[i + 1].getCarValue().ordinal() - cards[i].getCarValue().ordinal() != 1) {
                    flag = false;
                }
            }
        } else {
            flag = false;
        }
        return flag;
    }

    public static boolean isSequenceOfPairs(Card[] cards) {
        boolean flag = true;
        if (cards.length % 2 == 0 && cards.length > 5 && CardValue.A.ordinal() >= cards[cards.length - 1].getCarValue().ordinal()) {
            for (int i = 0; i < cards.length - 1; i++) {
                if (i % 2 == 0) {
                    if (!cards[i].equals(cards[i + 1])) {
                        flag = false;
                    }
                } else {
                    if (cards[i + 1].getCarValue().ordinal() - cards[i].getCarValue().ordinal() != 1) {
                        flag = false;
                    }
                }
            }
        } else {
            flag = false;
        }
        return flag;
    }

    public static boolean isSequenceOfTriplets(Card[] cards) {
        if (((cards.length % 4 == 0) || (cards.length % 5 == 0)) && cards.length > 7) {
            for (int i = 0; i < 7; i++) {
                if (cards[i].equals(cards[i + 2]) && cards[i + 3].equals(cards[i + 5]) && cards[i + 3].getCarValue().ordinal() - cards[i + 2].getCarValue().ordinal() == 1) {
                    if (cards.length > 11) {
                        if (cards.length > 15) {
                            if (cards[i + 9].equals(cards[i + 11]) && cards[i + 9].getCarValue().ordinal() - cards[i + 8].getCarValue().ordinal() == 1) {
                                return true;
                            }
                        } else {
                            if (cards[i + 6].equals(cards[i + 8]) && cards[i + 6].getCarValue().ordinal() - cards[i + 5].getCarValue().ordinal() == 1) {
                                return true;
                            }
                        }
                    } else {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isRocke0t(Card[] cards) {
        if (cards.length == 2 && cards[0].getCarValue() == CardValue.blackJoker) {
            return true;
        }
        return false;
    }

    public static boolean isQuadplexSet(Card[] cards) {
        if (cards.length == 6 && ((cards[0].equals(cards[3]) && cards[4].equals(cards[5]) && !cards[3].equals(cards[4]) )|| (cards[0].equals(cards[1]) && cards[2].equals(cards[5]) && !cards[1].equals(cards[2])))) {
            return true;
        }
        return false;
    }

//    public static void main(String[] args) {
//        Card[] cards = {
//                new Card(CardValue.three, Suit.club),
//                new Card(CardValue.three, Suit.diamond),
//                new Card(CardValue.four, Suit.club),
//                new Card(CardValue.four, Suit.diamond),
//                new Card(CardValue.five, Suit.club),
//                new Card(CardValue.five, Suit.diamond),
//        };
//
//        System.err.println(Rules.CheckCards(cards));
//    }
}
