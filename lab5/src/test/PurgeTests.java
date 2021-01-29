import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PurgeTests {
    private static TaskDispatcher taskDispatcher;
    private static MongoTrackService mongoTrackService;
    private static String purgeResult;

    @BeforeAll
    static void createTestTracksAndPerformPurge() {
        taskDispatcher = new TaskDispatcher();
        mongoTrackService = new MongoTrackService();

        mongoTrackService.insert(new Track("Martin Garrix", "Animals"));
        mongoTrackService.insert(new Track("Martin Garrix", "Tsunami"));
        mongoTrackService.insert(new Track("Watsky", "Ugly Faces"));
        mongoTrackService.insert(new Track("Watsky", "Tsunami"));
        mongoTrackService.insert(new Track("Linkin Park", "Powerless"));

        purgeResult = taskDispatcher.execute("purge");
    }

    @Test
    void checkCatalogIsEmptyAfterPurge() {
        assertNull(mongoTrackService.getAll().first());
    }

    @Test
    void theCatalogWasAlreadyEmptyMessage() {
        assertEquals("The catalog was already empty!\n", taskDispatcher.execute("purge"));
    }

    @Test
    void afterPurgeConfirmationIsReceived() {
        assertEquals("The catalog has been emptied\n", purgeResult);
    }

    @AfterAll
    static void finish() {
        mongoTrackService.finish();
    }
}
