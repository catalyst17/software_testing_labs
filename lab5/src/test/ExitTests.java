import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.ginsberg.junit.exit.ExpectSystemExitWithStatus;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ExitTests {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    @ExpectSystemExitWithStatus(0)
    void checkThatSystemExitsCorrectly() {
        TaskDispatcher taskDispatcher = new TaskDispatcher();

        taskDispatcher.execute("exit");
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }
}
