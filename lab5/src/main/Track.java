import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class Track {
    private ObjectId id;

    @BsonProperty(value = "author")
    private String author;

    @BsonProperty(value = "track_name")
    private String trackName;

    public Track() {
    }

    public Track(String author, String trackName) {
        this.author = author;
        this.trackName = trackName;
    }

    public ObjectId getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }
}
