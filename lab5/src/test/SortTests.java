import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SortTests {
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
    void checkTheResultIsCorrect() {
        StringBuilder sb = new StringBuilder("Linkin Park - Powerless\n");
        sb.append("Martin Garrix - Animals\n");
        sb.append("Martin Garrix - Tsunami\n");
        sb.append("Watsky - Ugly Faces\n");
        sb.append("Watsky - Tsunami\n");
        assertEquals(sb.toString(), taskDispatcher.execute("list-sorted"));
    }

    @AfterAll
    static void deleteTestTracks() {
        mongoTrackService.purge();

        mongoTrackService.finish();
    }
}
