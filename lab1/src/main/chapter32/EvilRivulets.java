public class EvilRivulets extends Metal {
    private final State state = State.LIQUID;
    private ComputerBank computerBank;

    EvilRivulets(ComputerBank fromComputerBank) {
        computerBank = fromComputerBank;
        if (computerBank.getCondition() == ComputerBank.Condition.MELTING)
            System.out.println("Ручейки металла текут в угол, где сидят они...");
        else if (computerBank.getCondition() == ComputerBank.Condition.OPERATING)
            System.out.println("Компьютерный банк не плавит, ручейков нет");
    }

    public void meltTheBankDown() {
        computerBank.melt();
    }
}
