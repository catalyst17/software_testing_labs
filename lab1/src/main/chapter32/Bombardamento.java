import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Bombardamento {
    private final int needed_quantity = 5;
    @Getter private boolean isOngoing = false;

    private class Bomb {
        @Getter private boolean ready = false;

        void prepare() {
            ready = true;
        }
    }

    static class NotAllBombsAreReadyException extends IllegalStateException {
        NotAllBombsAreReadyException() {
            super("Не все бобмы готовы. Бомбардировка отменяется.");
        }
    }

    private List<Bomb> bombs = Stream.generate(Bomb::new).limit(needed_quantity).collect(Collectors.toList());

    String resume(double failureProbability) throws NotAllBombsAreReadyException {
        if (bombs.size() >= needed_quantity) {
            for (Bomb bomb : bombs) {
                if (Math.random() >= failureProbability) bomb.prepare();
            }
        }

        if (bombs.stream().allMatch(bomb -> bomb.isReady())) {
            isOngoing = true;
            Environment.setTempState(Environment.TempState.HOT);
            Environment.setNoiseState(Environment.NoiseState.NOISY);
        }
        else {
            isOngoing = false;
            throw new NotAllBombsAreReadyException();
        }
        return "Бомбардировка возобновилась";
    }

    String stop() {
        isOngoing = false;
        Environment.setTempState(Environment.TempState.PLEASANT);
        Environment.setNoiseState(Environment.NoiseState.NORMAL);
        return "Бомбардировка остановилась";
    }
}
