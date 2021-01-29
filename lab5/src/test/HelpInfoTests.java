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
        StringBuilder sb = new StringBuilder("Tracks Catalog Manager v0.1 Here is the help information on the commands you can use:\n");
        sb.append("create <Author> - <Track> \t lets you to add a new track info\n");
        sb.append("delete <Author> - <Track> \t lets you to delete the track info\n");
        sb.append("purge \t lets you to delete all the info from the catalog\n");
        sb.append("list \t lets you to get all the info from the catalog\n");
        sb.append("list-sorted \t lets you to get all the info from the catalog sorted by the author name\n");
        sb.append("search-by-author <Author> \t lets you to get all the tracks from the specified author\n");
        sb.append("search-by-name <Track> \t lets you to get all the tracks from the specified author\n");
        sb.append("find <Author> - <Track> \t lets you to find the exact track\n");
        sb.append("help \t lets you to see the help message when you want it\n");
        sb.append("exit \t closes the app\n");
        assertEquals(sb.toString(), taskDispatcher.execute("help"));
    }
}
