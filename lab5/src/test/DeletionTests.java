import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.UnknownHostException;

public class DeletionTests {
    private static TaskDispatcher taskDispatcher;
    private static MongoTrackService mongoTrackService;

    @BeforeAll
    static void createTestTrack() throws UnknownHostException{
        taskDispatcher = new TaskDispatcher();
        mongoTrackService = new MongoTrackService();

        mongoTrackService.insert(new Track("Martin Garrix", "Animals"));
    }

    @Test
    void afterDeleteTrackConfirmationIsReceived() {
        assertEquals("Track info has been deleted", taskDispatcher.execute("delete Martin Garrix - Animals"));
    }

    @Test
    void afterDeleteTrackCouldNotRetrieveItFromDB() {
        assertFalse(mongoTrackService.findExact(new Track("Martin Garrix", "Animals")));
    }

    @AfterAll
    static void deleteTestTrackIfHasNotBeenDeleted() {
        mongoTrackService.delete(new Track("Martin Garrix", "Animals"));

        mongoTrackService.finish();
    }
}
