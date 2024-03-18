package blackjack.domain.participant;

import static org.assertj.core.api.Assertions.assertThatCode;

import blackjack.domain.card.BlackjackCardsFactory;
import blackjack.domain.card.Deck;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PlayerBetAmountsTest {

    @DisplayName("베팅내역을 생성한다")
    @Test
    public void create() {
        assertThatCode(() -> new PlayerBetAmounts(Map.of(new Name("이상"), new BetAmount(1000))))
                .doesNotThrowAnyException();
    }

    @DisplayName("무승부면 베팅 금액의 0배를 수익금으로 갖는다")
    @Test
    public void calculateProfit() {
        Players players = Players.createInitialPlayers(List.of(new Name("이상")));
        Dealer dealer = Dealer.createInitialStateDealer();
        PlayerBetAmounts playerBetAmounts = new PlayerBetAmounts(Map.of(new Name("이상"), new BetAmount(1000)));

        Deck playerDeck = Deck.of(new BlackjackCardsFactory(), cards -> cards);
        players = players.takeFirstHand(playerDeck);
        Deck dealerDeck = Deck.of(new BlackjackCardsFactory(), cards -> cards);
        dealer = dealer.draw(dealerDeck);

        players = players.getPlayers().stream()
                .map(player -> player.draw(playerDeck))
                .collect(Collectors.collectingAndThen(Collectors.toList(), Players::updatePlayers));
        dealer = dealer.draw(dealerDeck);

        playerBetAmounts.calculateProfit(players, dealer);
    }
}