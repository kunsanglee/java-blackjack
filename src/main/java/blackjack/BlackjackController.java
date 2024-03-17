package blackjack;

import blackjack.domain.card.CardFactory;
import blackjack.domain.card.Deck;
import blackjack.domain.card.RandomShuffler;
import blackjack.domain.participant.BetRecord;
import blackjack.domain.participant.Dealer;
import blackjack.domain.participant.Name;
import blackjack.domain.participant.Player;
import blackjack.domain.participant.Players;
import blackjack.domain.participant.ProfitDetails;
import blackjack.view.InputView;
import blackjack.view.OutputView;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class BlackjackController {
    private final InputView inputView;
    private final OutputView outputView;

    public BlackjackController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void play() {
        List<Name> playerNames = repeat(inputView::readPlayerNames);
        BetRecord betRecord = repeat(() -> inputView.readAmountOfBet(playerNames));

        ProfitDetails profitDetails = playBlackjack(playerNames, betRecord);
        outputView.printProfitDetails(profitDetails);
    }

    private ProfitDetails playBlackjack(List<Name> playerNames, BetRecord betRecord) {
        Deck deck = Deck.of(new CardFactory(), new RandomShuffler());
        Players players = createAndInitializePlayers(playerNames, deck);
        Dealer dealer = createAndInitializeDealer(deck);
        outputView.printPlayersInitialHand(players, dealer);

        players = playersTurn(players, deck);
        dealer = dealerTurn(dealer, deck);
        outputView.printParticipantResult(players, dealer);

        return betRecord.calculateProfit(players, dealer);
    }

    private Players createAndInitializePlayers(List<Name> playerNames, Deck deck) {
        Players players = Players.createInitialPlayers(playerNames);
        return players.initializePlayersHands(deck);
    }

    private Dealer createAndInitializeDealer(Deck deck) {
        Dealer dealer = Dealer.createInitialStateDealer();
        return dealer.draw(deck);
    }

    private Players playersTurn(Players players, Deck deck) {
        return players.getPlayers().stream()
                .map(player -> hitUntilPlayerStand(player, deck))
                .collect(Collectors.collectingAndThen(Collectors.toList(), Players::updatePlayers));
    }

    private Player hitUntilPlayerStand(Player player, Deck deck) {
        while (player.canDraw()) {
            player = drawOrStand(player, deck);
            outputView.printParticipantHand(player);
        }
        return player;
    }

    private Player drawOrStand(Player player, Deck deck) {
        if (repeat(() -> inputView.readHitOrStand(player))) {
            return player.draw(deck);
        }
        return player.stand();
    }

    private Dealer dealerTurn(Dealer dealer, Deck deck) {
        while (dealer.canDraw()) {
            outputView.printDealerDraw();
            dealer = dealer.draw(deck);
        }
        return dealer;
    }

    private <T> T repeat(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }
}
