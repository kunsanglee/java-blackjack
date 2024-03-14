package blackjack.domain.state;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import blackjack.domain.CardFactory;
import blackjack.domain.Deck;
import blackjack.domain.Denomination;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InitialStateTest {

    @DisplayName("초기 상태에서 블랙잭이 아닌 핸드를 받으면 HitState가 반횐된다")
    @Test
    public void hitState() {
        InitialState initialState = new InitialState();
        Deck deck = Deck.of(new CardFactory(), cards -> cards);

        State newState = initialState.draw(deck);

        assertThat(newState).isInstanceOf(HitState.class);
    }

    @DisplayName("초기 상태에서 블랙잭인 핸드를 받으면 BlackJackState가 반환된다")
    @Test
    public void blackJackState() {
        InitialState initialState = new InitialState();
        Deck deck = Deck.of(new CardFactory(),
                cards -> cards.stream().filter(card -> card.isAce() || card.getDenomination().equals(Denomination.KING))
                        .toList());

        State newState = initialState.draw(deck);

        assertThat(newState).isInstanceOf(BlackJackState.class);
    }

    @DisplayName("초기 상태에서 스탠드를 하면 에러가 발생한다")
    @Test
    public void standFail() {
        InitialState initialState = new InitialState();

        assertThatCode(() -> initialState.stand())
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessage("초기 상태에서는 할 수 없습니다.");
    }

    @DisplayName("초기 상태에서 핸드를 계산하면 에러가 발생한다")
    @Test
    public void calculateHandFail() {
        InitialState initialState = new InitialState();

        assertThatCode(() -> initialState.calculateHand())
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessage("초기 상태에서는 할 수 없습니다.");
    }

    @DisplayName("초기 상태에서 핸드 반환하면 에러가 발생한다")
    @Test
    public void getHandFail() {
        InitialState initialState = new InitialState();

        assertThatCode(() -> initialState.getHand())
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessage("초기 상태에서는 할 수 없습니다.");
    }

    @DisplayName("초기 상태에서 끝났는지 판단하면 false를 반환한다")
    @Test
    public void isFinishedFalse() {
        InitialState initialState = new InitialState();

        assertThat(initialState.isFinished()).isFalse();
    }
}