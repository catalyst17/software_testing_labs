import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        TaskDispatcher dispatcher = new TaskDispatcher();

        System.out.print(dispatcher.generateHelpMessage());

        while (true) {
            System.out.println(dispatcher.execute(reader.readLine()));
        }
    }
}
