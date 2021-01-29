import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.net.UnknownHostException;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoTrackService {
    private MongoCollection<Track> collection;

    public MongoTrackService() throws UnknownHostException {
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://u9zwdrhjwwzvvtsuweky:iHfSTuPiOt94ocFCDZHr@bkgvfdjal5omakq-mongodb.services.clever-cloud.com:27017/?authSource=bkgvfdjal5omakq"));

        MongoDatabase database = mongoClient.getDatabase("bkgvfdjal5omakq");

        collection = database.getCollection("tracks", Track.class);

        collection = collection.withCodecRegistry(pojoCodecRegistry);
    }

    boolean insert(Track track) {
        return false;
    }

    boolean findExact(Track track) {
        return false;
    }
}
