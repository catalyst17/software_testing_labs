import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ComputerBank {
    @Getter private Condition condition;
    private List<ComputerPart> parts = Stream.generate(ComputerPart::new).limit(10).collect(Collectors.toList());

    ComputerBank() {
        condition = Environment.getTempState() == Environment.TempState.HOT ? Condition.MELTING : Condition.OPERATING;
    }

    enum Condition {
        OPERATING {
            @Override
            public String toString() {
                return "рабочее состояние";
            }
        },
        MELTING {
            @Override
            public String toString() {
                return "плавится";
            }
        },
        ANNIHILATED {
            @Override
            public String toString() {
                return "уничтожен";
            }
        }
    }

    private class ComputerPart extends Metal {
        private final State state = State.SOLID;
    }

    void melt() {
        System.out.print("Компьютерный банк " + condition + ", живых комплектующих осталось всего: ");
        while (!parts.isEmpty()) {
            parts.remove(0);
            System.out.print(parts.size() + "! ");
        }
        condition = Condition.ANNIHILATED;
        System.out.println("Компьютерный банк " + condition + ".");
    }
}
