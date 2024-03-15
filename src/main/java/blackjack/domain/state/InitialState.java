package blackjack.domain.state;

import blackjack.domain.Deck;
import blackjack.domain.Hand;

public class InitialState extends State {

    public InitialState() {
        super(Hand.of());
    }

    @Override
    public State draw(Deck deck) {
        Hand hand = Hand.of(deck.draw(), deck.draw());
        if (hand.isBlackJack()) {
            return new BlackJackState(hand);
        }
        return new HitState(hand);
    }

    @Override
    public State stand() {
        throw new UnsupportedOperationException("초기 상태에서는 할 수 없습니다.");
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public Hand getHand() {
        throw new UnsupportedOperationException("초기 상태에서는 할 수 없습니다.");
    }
}
