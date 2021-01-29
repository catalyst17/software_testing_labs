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

    public String execute(String message) {
        String command = message.substring(0, message.indexOf(" "));
        switch (command) {
            case CREATE:

        }
        return null;
    }
}
