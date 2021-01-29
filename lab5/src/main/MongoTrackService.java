import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static com.mongodb.client.model.Filters.eq;
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
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("author", track.getAuthor());
        searchQuery.put("track_name", track.getTrackName());

        if (collection.find(searchQuery).first() != null) {
            return false;
        }

        collection.insertOne(track);
        return true;
    }

    boolean findExact(Track track) {
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("author", track.getAuthor());
        searchQuery.put("track_name", track.getTrackName());

        return collection.find(searchQuery).first() != null;
    }

    boolean delete(Track track) {
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("author", track.getAuthor());
        searchQuery.put("track_name", track.getTrackName());

        return collection.deleteOne(searchQuery).getDeletedCount() == 1;
    }

    FindIterable<Track> getAll() {
        return collection.find();
    }

    FindIterable<Track> getAllSorted() {
        return collection.find().sort(new BasicDBObject("author", 1));
    }

    FindIterable<Track> searchByAuthor(String author) {
        return collection.find(eq("author", author));
    }

    FindIterable<Track> searchByTrackName(String trackName) {
        return collection.find(eq("track_name", trackName));
    }

    boolean purge() {
        return collection.deleteMany(new BasicDBObject()).getDeletedCount() > 0;
    }

    void finish() {
        mongoClient.close();
    }
}
