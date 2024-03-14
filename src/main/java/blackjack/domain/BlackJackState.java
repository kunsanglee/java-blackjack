package blackjack.domain;

public class BlackJackState implements State {
    private final Hand hand;

    public BlackJackState(Hand hand) {
        this.hand = hand;
    }

    @Override
    public State draw(Deck deck) {
        return null;
    }

    @Override
    public State stand() {
        return null;
    }

    @Override
    public Score calculateHand() {
        return null;
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public Hand getHand() {
        return null;
    }
}
