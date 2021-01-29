import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.UnknownHostException;

public class CreationTests {
    private static String creationResult;
    private static MongoTrackService mongoTrackService;

    @BeforeAll
    static void createTestTrack() throws UnknownHostException{
        TaskDispatcher taskDispatcher = new TaskDispatcher();
        mongoTrackService = new MongoTrackService();

        creationResult = taskDispatcher.execute("create Martin Garrix - Animals");
    }

    @Test
    void afterCreateTrackConfirmationIsReceived() {
        assertEquals("Track info has been added", creationResult);
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
