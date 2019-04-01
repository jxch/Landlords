package requestAndAnswer.poke;

public enum Suit {
    heard("红桃"),
    diamond("方块"),
    club("黑桃"),
    spade("梅花"),
    empty("");

    private String suit;

    Suit(String suit){
        this.suit = suit;
    }

    public String getSuit() {
        return suit;
    }
}
