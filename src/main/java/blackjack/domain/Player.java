package blackjack.domain;

public class Player {

    protected final Hand hand;
    private final String name;

    public Player(String name) {
        this.name = name;
        this.hand = new Hand();
    }

    public int calculate() {
        return hand.calculate();
    }

    public void putCard(Card card) {
        hand.put(card);
    }

    public boolean canHit() {
        return hand.calculate() <= 21;
    }

    public Hand getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }
}