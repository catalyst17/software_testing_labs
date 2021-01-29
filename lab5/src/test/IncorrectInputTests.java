import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IncorrectInputTests {
    private static TaskDispatcher taskDispatcher;

    @BeforeAll
    static void createTestTracks() {
        taskDispatcher = new TaskDispatcher();
    }

    @Test
    void checkTheOutputForIncorrectCommand() {
        assertEquals("Specified command doesn't exist!\n", taskDispatcher.execute("search Linkin Park"));
    }

    @Test
    void checkTheOutputForIncorrectCreateArgs() {
        assertEquals("Please, check the specified arguments!\n", taskDispatcher.execute("create Ugly Faces"));
    }

    @Test
    void checkTheOutputForIncorrectDeleteArgs() {
        assertEquals("Please, check the specified arguments!\n", taskDispatcher.execute("delete Watsky - Animals - Faces"));
    }

    @Test
    void checkTheOutputForIncorrectSearchByNameArgs() {
        assertEquals("Please, check the specified arguments!\n", taskDispatcher.execute("search-by-name Watsky - Animals"));
    }

    @Test
    void checkTheOutputForIncorrectSearchByAuthorArgs() {
        assertEquals("Please, check the specified arguments!\n", taskDispatcher.execute("search-by-author Watsky - Animals"));
    }

    @Test
    void checkTheOutputForIncorrectFindArgs() {
        assertEquals("Please, check the specified arguments!\n", taskDispatcher.execute("find Animals"));
    }
}
