package requestAndAnswer.poke;

public enum CardValue {
    three("3"),
    four("4"),
    five("5"),
    six("6"),
    seven("7"),
    eight("8"),
    nine("9"),
    ten("10"),
    J("J"),
    Q("Q"),
    K("K"),
    A("A"),
    two("2"),
    blackJoker("blackJoker"),
    redJoker("redJoker");

    private String value;

    CardValue(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
