import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CreationTests {
    private static String creationResult;
    private static MongoTrackService mongoTrackService;
    private static TaskDispatcher taskDispatcher;

    @BeforeAll
    static void createTestTrack() {
        taskDispatcher = new TaskDispatcher();
        mongoTrackService = new MongoTrackService();

        creationResult = taskDispatcher.execute("create Martin Garrix - Animals");
    }

    @Test
    void afterCreateTrackConfirmationIsReceived() {
        assertEquals("Track info has been added\n", creationResult);
    }

    @Test
    void redundantCreateIsFollowedByDescriptionalMessage() {
        assertEquals("This info has already existed in the catalog!\n", taskDispatcher.execute("create Martin Garrix - Animals"));
    }

    @Test
    void afterCreateTrackCouldRetrieveItFromDB() {
        assertTrue(mongoTrackService.findExact(new Track("Martin Garrix", "Animals")));
    }

    @AfterAll
    static void deleteTestTrack() {
        mongoTrackService.delete(new Track("Martin Garrix", "Animals"));

        mongoTrackService.finish();
    }
}
