import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PurgeTests {
    private static TaskDispatcher taskDispatcher;
    private static MongoTrackService mongoTrackService;

    @BeforeAll
    static void createTestTracks() {
        taskDispatcher = new TaskDispatcher();
        mongoTrackService = new MongoTrackService();

        mongoTrackService.insert(new Track("Martin Garrix", "Animals"));
        mongoTrackService.insert(new Track("Martin Garrix", "Tsunami"));
        mongoTrackService.insert(new Track("Watsky", "Ugly Faces"));
        mongoTrackService.insert(new Track("Watsky", "Tsunami"));
        mongoTrackService.insert(new Track("Linkin Park", "Powerless"));
    }

    @Test
    void checkCatalogIsEmptyAfterPurge() {
         mongoTrackService.purge();
    }

    @AfterAll
    static void finish() {
        mongoTrackService.finish();
    }
}
