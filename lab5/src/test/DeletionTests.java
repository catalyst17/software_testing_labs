import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DeletionTests {
    private static TaskDispatcher taskDispatcher;
    private static MongoTrackService mongoTrackService;
    private static String deleteResult;

    @BeforeAll
    static void createAndDeleteTestTrack() {
        taskDispatcher = new TaskDispatcher();
        mongoTrackService = new MongoTrackService();

        mongoTrackService.insert(new Track("Martin Garrix", "Animals"));

        deleteResult = taskDispatcher.execute("delete Martin Garrix - Animals");
    }

    @Test
    void afterDeleteTrackConfirmationIsReceived() {
        assertEquals("Track info has been deleted\n", deleteResult);
    }

    @Test
    void couldNotDeleteTheTrackThatDoesNotExistMessage() {
        assertEquals("Could not find this track in the catalog!\n", taskDispatcher.execute("delete Martin Garrix - Animals"));
    }

    @Test
    void afterDeleteTrackCouldNotRetrieveItFromDB() {
        assertFalse(mongoTrackService.findExact(new Track("Martin Garrix", "Animals")));
    }

    @AfterAll
    static void deleteTestTrackIfHasNotBeenDeletedViaDispatcher() {
        mongoTrackService.delete(new Track("Martin Garrix", "Animals"));

        mongoTrackService.finish();
    }
}
