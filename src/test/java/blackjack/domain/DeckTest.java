package blackjack.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DeckTest {

    @DisplayName("덱에서 카드를 한 장 뽑을 수 있다")
    @Test
    public void draw() {
        CardFactory cardFactory = new CardFactory();
        Deck deck = Deck.of(cardFactory, cards -> cards);

        Card card = deck.draw();

        assertThat(card.getSuit()).isEqualTo(Suit.SPADE);
        assertThat(card.getDenomination()).isEqualTo(Denomination.KING);
    }

    @DisplayName("덱에 카드가 없는데 한 장을 뽑을 경우 에러가 발생한다")
    @Test
    public void drawFail() {
        Deck deck = Deck.of(new CardFactory(), cards -> cards);

        for (int i = 0; i < 52; i++) {
            deck.draw();
        }

        assertThatCode(deck::draw)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("덱에 모든 카드가 소진되었습니다.");
    }

    @DisplayName("덱에서 2장을 뽑아 핸드로 반환한다")
    @Test
    public void initializeHand() {
        Deck deck = Deck.of(new CardFactory(), cards -> cards);

        Hand hand = deck.initializeHand();
        List<Card> cards = hand.getCards();

        assertThat(cards.size()).isEqualTo(2);
        assertThat(cards.get(0).getSuit()).isEqualTo(Suit.SPADE);
        assertThat(cards.get(0).getDenomination()).isEqualTo(Denomination.KING);
        assertThat(cards.get(1).getSuit()).isEqualTo(Suit.SPADE);
        assertThat(cards.get(1).getDenomination()).isEqualTo(Denomination.QUEEN);
    }
}