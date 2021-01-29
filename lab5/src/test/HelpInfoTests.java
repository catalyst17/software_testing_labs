import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelpInfoTests {
    private static TaskDispatcher taskDispatcher;

    @BeforeAll
    static void createTestTracks() {
        taskDispatcher = new TaskDispatcher();
    }

    @Test
    void checkTheResultIsCorrect() {
        String sb = "Tracks Catalog Manager v0.1\n\nHere is the help information on the commands you can use:\n\n" + "create <Author> - <Track> \t lets you to add a new track info\n" +
                "delete <Author> - <Track> \t lets you to delete the track info\n" +
                "purge \t\t\t\t\t\t lets you to delete all the info from the catalog\n" +
                "list \t\t\t\t\t\t lets you to get all the info from the catalog\n" +
                "list-sorted \t\t\t\t lets you to get all the info from the catalog sorted by the author name\n" +
                "search-by-author <Author> \t lets you to get all the tracks from the specified author\n" +
                "search-by-name <Track> \t\t lets you to get all the tracks from the specified author\n" +
                "find <Author> - <Track> \t lets you to find the exact track\n" +
                "help \t\t\t\t\t\t lets you to see the help message when you want it\n" +
                "exit \t\t\t\t\t\t closes the app\n";
        assertEquals(sb, taskDispatcher.execute("help"));
    }
}
