package api.tests.utils;

import com.google.gson.Gson;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class JsonHelper {
    String path;

    public JsonHelper(String filePath) {
        this.path = filePath;
    }

    public Map<?, ?> getJson(){
        Map<?, ?> data = null;
        try {
            // create Gson instance
            Gson gson = new Gson();

            // create a reader
            Reader reader = Files.newBufferedReader(Paths.get(path));

            // convert JSON file to map
            data = gson.fromJson(reader, Map.class);

            // close reader
            reader.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return data;
    }
}
