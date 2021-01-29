import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.UnknownHostException;

public class CreationTest {
    private static String creationResult;
    private static TaskDispatcher taskDispatcher;

    @BeforeAll
    static void createTestTrack() {
        taskDispatcher = new TaskDispatcher();

        creationResult = taskDispatcher.execute("create Martin Garrix - Animals");
    }

    @Test
    void afterCreateTrackConfirmationIsReceived() {
        assertEquals("Track info has been added", creationResult);
    }

    @Test
    void afterCreateTrackCouldRetrieveItFromDB() throws UnknownHostException {
        MongoTrackService mongoTrackService = new MongoTrackService();

        assertTrue(mongoTrackService.findExact(new Track("Martin Garrix", "Animals")));
    }
}
