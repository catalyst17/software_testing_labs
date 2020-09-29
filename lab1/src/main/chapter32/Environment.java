import lombok.Getter;
import lombok.Setter;

public class Environment {
    @Getter @Setter
    private static TempState tempState = TempState.PLEASANT;
    @Getter @Setter
    private static NoiseState noiseState = NoiseState.NORMAL;

    enum TempState {
        COLD {
            @Override
            public String toString() {
                return "холодно";
            }
        },
        PLEASANT {
            @Override
            public String toString() {
                return "нежарко и нехолодно, суперски, в общем";
            }
        },
        HOT {
            @Override
            public String toString() {
                return "жарко";
            }
        }
    }

    enum NoiseState {
        SILENT {
            @Override
            public String toString() {
                return "слышно только сверчков...";
            }
        },
        NORMAL {
            @Override
            public String toString() {
                return "обычные звуки повседневной вселенной";
            }
        },
        NOISY {
            @Override
            public String toString() {
                return "шумно";
            }
        }
    }
}
