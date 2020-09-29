public class They {
    They (ComputerBank computerBank) {
        if (computerBank.getCondition() == ComputerBank.Condition.OPERATING) chill();
        else if (computerBank.getCondition() == ComputerBank.Condition.MELTING) huddle();
        else commitSuicide();
    }

    void chill() {
        System.out.print("Всё в норме, они сидят и отдыхают");
    }

    void huddle() {
        System.out.print("Они сгрудились плотнее и ждут конца");
    }

    void commitSuicide() {
        System.out.print("Компьютерный банк уничтожен, выход всего один - самовыпиливание");
    }
}
