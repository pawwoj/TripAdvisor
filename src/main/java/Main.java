import application.App;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(Arrays.asList("Warsaw", "London", "Honolulu"));
        LocalDate fromD = LocalDate.now();
        LocalDate toD = LocalDate.now().plusDays(6);

        App app = new App();
        app.run(fromD, toD, list);
    }
}
