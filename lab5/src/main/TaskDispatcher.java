import com.mongodb.client.FindIterable;

import java.util.Arrays;
import java.util.List;

public class TaskDispatcher {
    public static final String CREATE = "create";
    public static final String DELETE = "delete";
    public static final String PURGE = "purge";
    public static final String LIST = "list";
    public static final String LIST_SORTED = "list-sorted";
    public static final String SEARCH_BY_AUTHOR = "search-by-author";
    public static final String SEARCH_BY_NAME = "search-by-name";
    public static final String FIND = "find";
    public static final String HELP = "help";
    public static final String EXIT = "exit";

    private MongoTrackService mongoTrackService;

    public TaskDispatcher() {
        mongoTrackService = new MongoTrackService();
    }

    public String execute(String message) {
        String author;
        String track_name;
        StringBuilder sb;

        List<String> commandParts = Arrays.asList(message.split(" "));
        switch (commandParts.get(0)) {
            case CREATE:
                if (commandParts.size() < 4 || commandParts.stream().filter(c -> c.equals("-")).count() != 1 ||
                commandParts.indexOf("-") == 1 || commandParts.indexOf("-") == commandParts.size()-1)
                    return "Please, check the specified arguments!\n";
                author = message.substring(message.indexOf(" ") + 1, message.indexOf(" -"));
                track_name = message.substring(message.indexOf("- ") + 2);
                if (mongoTrackService.insert(new Track(author, track_name)))
                    return "Track info has been added\n";
                else return "This info has already existed in the catalog!\n";
            case DELETE:
                if (commandParts.size() < 4 || commandParts.stream().filter(c -> c.equals("-")).count() != 1 ||
                        commandParts.indexOf("-") == 1 || commandParts.indexOf("-") == commandParts.size()-1)
                    return "Please, check the specified arguments!\n";
                author = message.substring(message.indexOf(" ") + 1, message.indexOf(" -"));
                track_name = message.substring(message.indexOf("- ") + 2);
                if (mongoTrackService.delete(new Track(author, track_name)))
                    return "Track info has been deleted\n";
                else return "Could not find this track in the catalog!\n";
            case PURGE:
                if (commandParts.size() != 1)
                    return "Please, check the specified arguments!\n";
                if (mongoTrackService.purge())
                    return "The catalog has been emptied\n";
                else return "The catalog was already empty!\n";
            case LIST:
                if (commandParts.size() != 1)
                    return "Please, check the specified arguments!\n";
                sb = new StringBuilder();
                for (Track track : mongoTrackService.getAll()) {
                    sb.append(track.getAuthor()).append(" - ").append(track.getTrackName()).append("\n");
                }
                return sb.toString();
            case LIST_SORTED:
                if (commandParts.size() != 1)
                    return "Please, check the specified arguments!\n";
                sb = new StringBuilder();
                for (Track track : mongoTrackService.getAllSorted()) {
                    sb.append(track.getAuthor()).append(" - ").append(track.getTrackName()).append("\n");
                }
                return sb.toString();
            case SEARCH_BY_AUTHOR:
                if (commandParts.size() == 1)
                    return "Please, check the specified arguments!\n";
                sb = new StringBuilder();
                FindIterable<Track> result = mongoTrackService.searchByAuthor(message.substring(message.indexOf(" ") + 1));
                for (Track track : result) {
                    sb.append(track.getAuthor()).append(" - ").append(track.getTrackName()).append("\n");
                }
                return sb.toString();
        }
        return null;
    }
}
