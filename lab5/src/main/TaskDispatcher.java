import com.mongodb.client.FindIterable;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private String message;
    private List<String> commandParts;

    private Logger logger;

    public TaskDispatcher() {
        logger = Logger.getLogger("org.mongodb.driver");
        logger.setLevel(Level.SEVERE);

        mongoTrackService = new MongoTrackService();
    }

    public String execute(String msg) {
        message = msg;
        commandParts = Arrays.asList(message.split(" "));

        switch (commandParts.get(0)) {
            case CREATE:
                return create();
            case DELETE:
                return delete();
            case PURGE:
                return purge();
            case LIST:
                return list(false);
            case LIST_SORTED:
                return list(true);
            case SEARCH_BY_AUTHOR:
                return searchBy(SEARCH_BY_AUTHOR);
            case SEARCH_BY_NAME:
                return searchBy(SEARCH_BY_NAME);
            case FIND:
                return find();
            case HELP:
                return generateHelpMessage();
            case EXIT:
                System.out.println("Goodbye!");
                System.exit(0);
            default:
                return "Specified command doesn't exist!\n";
        }
    }

    private String create() {
        if (commandParts.size() < 4 || commandParts.stream().filter(c -> c.equals("-")).count() != 1 ||
                commandParts.indexOf("-") == 1 || commandParts.indexOf("-") == commandParts.size()-1)
            return "Please, check the specified arguments!\n";

        String author = message.substring(message.indexOf(" ") + 1, message.indexOf(" -"));
        String track_name = message.substring(message.indexOf("- ") + 2);

        if (mongoTrackService.insert(new Track(author, track_name)))
            return "Track info has been added\n";
        else return "This info already exists in the catalog!\n";
    }

    private String delete() {
        if (commandParts.size() < 4 || commandParts.stream().filter(c -> c.equals("-")).count() != 1 ||
                commandParts.indexOf("-") == 1 || commandParts.indexOf("-") == commandParts.size()-1)
            return "Please, check the specified arguments!\n";

        String author = message.substring(message.indexOf(" ") + 1, message.indexOf(" -"));
        String track_name = message.substring(message.indexOf("- ") + 2);

        if (mongoTrackService.delete(new Track(author, track_name)))
            return "Track info has been deleted\n";
        else return "Could not find this track in the catalog!\n";
    }

    private String purge() {
        if (commandParts.size() != 1)
            return "Please, check the specified arguments!\n";
        if (mongoTrackService.purge())
            return "The catalog has been emptied\n";
        else return "The catalog was already empty!\n";
    }

    private String list(boolean sorted) {
        if (commandParts.size() != 1)
            return "Please, check the specified arguments!\n";
        StringBuilder sb = new StringBuilder();

        FindIterable<Track> result = sorted ? mongoTrackService.getAllSorted() : mongoTrackService.getAll();
        if (result.first() == null)
            return "Catalog is empty for now\n";
        for (Track track : result) {
            sb.append(track.getAuthor()).append(" - ").append(track.getTrackName()).append("\n");
        }
        return sb.toString();
    }

    private String searchBy(String byWhat) {
        if (commandParts.size() == 1 || commandParts.stream().filter(c -> c.equals("-")).count() > 0)
            return "Please, check the specified arguments!\n";
        StringBuilder sb = new StringBuilder();

        FindIterable<Track> result;
        if (byWhat.equals(SEARCH_BY_AUTHOR))
            result = mongoTrackService.searchByAuthor(message.substring(message.indexOf(" ") + 1));
        else result = mongoTrackService.searchByTrackName(message.substring(message.indexOf(" ") + 1));

        if (result.first() == null)
            return "Nothing has been found!\n";
        for (Track track : result) {
            sb.append(track.getAuthor()).append(" - ").append(track.getTrackName()).append("\n");
        }
        return sb.toString();
    }

    private String find() {
        if (commandParts.size() < 4 || commandParts.stream().filter(c -> c.equals("-")).count() != 1 ||
                commandParts.indexOf("-") == 1 || commandParts.indexOf("-") == commandParts.size()-1)
            return "Please, check the specified arguments!\n";

        String author = message.substring(message.indexOf(" ") + 1, message.indexOf(" -"));
        String track_name = message.substring(message.indexOf("- ") + 2);

        if (mongoTrackService.findExact(new Track(author, track_name)))
            return author + " - " + track_name + "\n";
        else return "The track has not been found!\n";
    }

    String generateHelpMessage() {
        StringBuilder sb = new StringBuilder("Tracks Catalog Manager v0.1\n\nHere is the help information on the commands you can use:\n\n");
        sb.append("create <Author> - <Track> \t lets you to add a new track info\n");
        sb.append("delete <Author> - <Track> \t lets you to delete the track info\n");
        sb.append("purge \t\t\t\t\t\t lets you to delete all the info from the catalog\n");
        sb.append("list \t\t\t\t\t\t lets you to get all the info from the catalog\n");
        sb.append("list-sorted \t\t\t\t lets you to get all the info from the catalog sorted by the author name\n");
        sb.append("search-by-author <Author> \t lets you to get all the tracks from the specified author\n");
        sb.append("search-by-name <Track> \t\t lets you to get all the tracks from the specified author\n");
        sb.append("find <Author> - <Track> \t lets you to find the exact track\n");
        sb.append("help \t\t\t\t\t\t lets you to see the help message when you want it\n");
        sb.append("exit \t\t\t\t\t\t closes the app\n");

        return sb.toString();
    }
}
