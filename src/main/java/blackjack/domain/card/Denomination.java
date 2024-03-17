package blackjack.domain.card;

import blackjack.domain.participant.Score;

public enum Denomination {
    ACE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(10),
    QUEEN(10),
    KING(10),
    ;

    private final Score value;

    Denomination(int value) {
        this.value = Score.from(value);
    }

    public Score getValue() {
        return this.value;
    }
}