import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Chapter32Test {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(System.out);
    }

    @Test
    void givenZeroFailureResumptionProbability_shouldResumeBombardment() {
        Bombardamento bombardamento = new Bombardamento();

        assertEquals("Бомбардировка возобновилась", bombardamento.resume(0));
    }

    @Test
    void givenOneFailureResumptionProbability_throwsException() {
        Bombardamento bombardamento = new Bombardamento();

        assertThrows(Bombardamento.NotAllBombsAreReadyException.class, () -> bombardamento.resume(1));
    }

    @Test
    void givenZeroFailureResumptionProbability_shouldChangeTemperature() {
        Bombardamento bombardamento = new Bombardamento();
        bombardamento.resume(0);

        assertEquals(Environment.TempState.HOT, Environment.getTempState());
    }

    @Test
    void givenZeroFailureResumptionProbability_shouldChangeNoiseLevel() {
        Bombardamento bombardamento = new Bombardamento();
        bombardamento.resume(0);

        assertEquals(Environment.NoiseState.NOISY, Environment.getNoiseState());
    }

    @Test
    void givenNoBombardment_checkTheyAreChilling() {
        They they = new They(new ComputerBank());

        assertEquals("Всё в норме, они сидят и отдыхают",
                outContent.toString());
    }

    @Test
    void givenBombardmentResumed_checkTheyAreHuddling() {
        new Bombardamento().resume(0);
        They they = new They(new ComputerBank());

        assertEquals("Они сгрудились плотнее и ждут конца", outContent.toString());
    }

    @Test
    void givenBombardmentResumed_and_RivuletsMeltingTheBankDown_checkTheyCommitSuicide() {
        Bombardamento bombardamento = new Bombardamento();
        bombardamento.resume(0);
        ComputerBank computerBank = new ComputerBank();
        EvilRivulets rivulets = new EvilRivulets(computerBank);
        rivulets.meltTheBankDown();
        They they = new They(computerBank);

        assertEquals("Ручейки металла текут в угол, где сидят они...\n" +
                "Компьютерный банк плавится, живых комплектующих осталось всего: 9! 8! 7! 6! 5! 4! 3! 2! 1! 0! " +
                "Компьютерный банк уничтожен.\n" +
                "Компьютерный банк уничтожен, выход всего один - самовыпиливание", outContent.toString());
    }

    @Test
    void givenBombardmentNotResumed_checkRivuletsDoNotFlow() {
        ComputerBank computerBank = new ComputerBank();
        EvilRivulets rivulets = new EvilRivulets(computerBank);

        assertEquals("Компьютерный банк не плавит, ручейков нет\n", outContent.toString());
    }

    @Test
    void givenBombardmentHasStopped_checkMessage() {
        Bombardamento bombardamento = new Bombardamento();
        bombardamento.resume(0);

        assertEquals("Бомбардировка остановилась", bombardamento.stop());
    }

    @Test
    void givenBombardmentHasStopped_checkEnvironmentIsBackToNormal() {
        Bombardamento bombardamento = new Bombardamento();
        bombardamento.resume(0);
        bombardamento.stop();

        assertEquals("нежарко и нехолодно, суперски, в общем; обычные звуки повседневной вселенной",
                Environment.getTempState().toString() + "; " + Environment.getNoiseState());
    }
}
