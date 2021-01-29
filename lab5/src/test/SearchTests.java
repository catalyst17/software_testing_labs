import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchTests {
    private static TaskDispatcher taskDispatcher;
    private static MongoTrackService mongoTrackService;

    @BeforeAll
    static void createTestTracks() {
        taskDispatcher = new TaskDispatcher();
        mongoTrackService = new MongoTrackService();

        mongoTrackService.purge();

        mongoTrackService.insert(new Track("Martin Garrix", "Animals"));
        mongoTrackService.insert(new Track("Martin Garrix", "Tsunami"));
        mongoTrackService.insert(new Track("Watsky", "Ugly Faces"));
        mongoTrackService.insert(new Track("Watsky", "Tsunami"));
        mongoTrackService.insert(new Track("Linkin Park", "Powerless"));
    }

    @Test
    void checkTheResultIsCorrectForWatsky() {
        StringBuilder sb = new StringBuilder("Watsky - Ugly Faces\n");
        sb.append("Watsky - Tsunami\n");
        assertEquals(sb.toString(), taskDispatcher.execute("search-by-author Watsky"));
    }

    @Test
    void checkTheResultIsCorrectForLinkinPark() {
        assertEquals("Linkin Park - Powerless\n", taskDispatcher.execute("search-by-author Linkin Park"));
    }

    @Test
    void checkThereIsNoResultForSearchByAuthor() {
        assertEquals("Nothing has been found!\n", taskDispatcher.execute("search-by-author Park"));
    }

    @Test
    void checkTheResultIsCorrectForUglyFaces() {
        assertEquals("Watsky - Ugly Faces\n", taskDispatcher.execute("search-by-name Ugly Faces"));
    }

    @Test
    void checkThereIsNoResultForSearchByName() {
        assertEquals("Nothing has been found!\n", taskDispatcher.execute("search-by-name Ugly"));
    }

    @Test
    void checkTheResultIsCorrectForTsunami() {
        StringBuilder sb = new StringBuilder("Martin Garrix - Tsunami\n");
        sb.append("Watsky - Tsunami\n");
        assertEquals(sb.toString(), taskDispatcher.execute("search-by-name Tsunami"));
    }

    @Test
    void checkTheResultIsCorrectForTheExactMatch() {
        assertEquals("Martin Garrix - Tsunami\n", taskDispatcher.execute("find Martin Garrix - Tsunami"));
    }

    @Test
    void checkThereIsNoResultForFind() {
        assertEquals("The track has not been found!\n", taskDispatcher.execute("find Martin Garrix - Nonexistence"));
    }

    @AfterAll
    static void deleteTestTracks() {
        mongoTrackService.purge();

        mongoTrackService.finish();
    }
}
