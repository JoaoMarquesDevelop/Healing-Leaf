package isle.academy.healing_leaf.helpers;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonDataRetriever {

    public static <T> T[] retrieveFromJson(String fileName, Class<T[]> type) {

        URL resource = JsonDataRetriever.class.getResource(fileName);
        assert resource != null;
        try {
            String jsonString = Files.readString(Paths.get(resource.toURI()));
            Gson g = new Gson();

            return g.fromJson(jsonString, type);

        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
