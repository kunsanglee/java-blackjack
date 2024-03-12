package blackjack.domain;

import java.util.List;

@FunctionalInterface
public interface Shuffler {
    List<Card> shuffle(List<Card> cards);
}