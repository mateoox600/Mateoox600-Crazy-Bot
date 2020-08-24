package fr.mateoox600.mcb.utils;

import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Config {

    private JSONObject config;
    private final File dataFolder;
    private  String token;
    private String status;
    private final String resourcePath;

    public Config(String resourcePath) {
        this.resourcePath = resourcePath;
        String path = getClass().getProtectionDomain().getCodeSource().getLocation().getPath().replace("%20", " ");
        File dataFolder = new File(path.substring(0, path.length() - (path.split("/")[path.split("/").length - 1].length() + 1)) + "/data");
        if (!dataFolder.exists()) dataFolder.mkdirs();
        this.dataFolder = dataFolder;
    }

    public void launch() throws IOException {
        InputStreamReader inr = new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(resourcePath)), StandardCharsets.UTF_8);
        char[] charBuffer = new char[2048];
        StringBuilder jsonFile = new StringBuilder();
        while (inr.read(charBuffer, 0, charBuffer.length) != -1) {
            jsonFile.append(new String(charBuffer)).append("\n");
        }
        config = new JSONObject(jsonFile.toString());
        status = config.getString("status_prefix") + config.getString("status_message");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(dataFolder, "token.txt")));
        token = bufferedReader.readLine();
        bufferedReader.close();
    }

    public String getPrefix() {
        return config.getString("prefix");
    }

    public String getToken() {
        return token;
    }

    public String getOwnerId() {
        return config.getString("owner_id");
    }

    public String getStatus() {
        return status;
    }

    public String setStatus(String status) {
        return this.status = config.getString("status_prefix") + status;
    }

    public File getDataFolder() {
        return dataFolder;
    }

}
