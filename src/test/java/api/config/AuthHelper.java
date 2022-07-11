package api.config;

import com.google.gson.Gson;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class AuthHelper {
    String path;

    public AuthHelper(String filePath) {
        this.path = filePath;
    }

    public Map<?, ?> getJson(){
        Map<?, ?> authData = null;
        try {
            // create Gson instance
            Gson gson = new Gson();

            // create a reader
            Reader reader = Files.newBufferedReader(Paths.get(path));

            // convert JSON file to map
            authData = gson.fromJson(reader, Map.class);

            // close reader
            reader.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return authData;
    }
}
