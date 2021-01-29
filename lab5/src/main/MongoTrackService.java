import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoTrackService {
    private MongoCollection<Track> collection;
    private final MongoClient mongoClient;

    public MongoTrackService() {
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        mongoClient = new MongoClient(new MongoClientURI("mongodb://u9zwdrhjwwzvvtsuweky:iHfSTuPiOt94ocFCDZHr@bkgvfdjal5omakq-mongodb.services.clever-cloud.com:27017/?authSource=bkgvfdjal5omakq"));

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

    boolean delete(Track track) {
        return false;
    }

    FindIterable<Track> getAll() {
        return collection.find();
    }

    void purge() {

    }

    void finish() {
        mongoClient.close();
    }
}
