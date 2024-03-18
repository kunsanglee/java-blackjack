package blackjack.domain.participant;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProfitAmountDetailsTest {

    @DisplayName("모든 플레이어의 수익금을 더한만큼의 음수를 딜러 수익금으로 계산한다")
    @Test
    public void calculateDealerProfit() {
        ProfitRecord profitRecord = new ProfitRecord(Map.of(new Name("이상"), new ProfitAmount(1000)));

        assertThat(profitRecord.calculateDealerProfit().getValue()).isEqualTo(-1000);
    }
}