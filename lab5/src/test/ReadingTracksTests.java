import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReadingTracksTests {
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
        StringBuilder sb = new StringBuilder("Martin Garrix - Animals\n");
        sb.append("Martin Garrix - Tsunami\n");
        sb.append("Watsky - Ugly Faces\n");
        sb.append("Watsky - Tsunami\n");
        sb.append("Linkin Park - Powerless\n");
        assertEquals(sb.toString(), taskDispatcher.execute("list"));
    }

    @AfterAll
    static void deleteTestTracks() {
        mongoTrackService.purge();

        mongoTrackService.finish();
    }
}
