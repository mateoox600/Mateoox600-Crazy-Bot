package fr.mateoox600.mcb.utils;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public class Config {

    /*private String status;

    public final String STATUS_PREFIX = "(In Dev) ";
    public final String STATUS_MESSAGE = "Existing... little bit";
    public final String PREFIX = ".";
    public final String TOKEN = "NzE0MTU1NzY5NTE1MjEyODMx.XvuwAw.uKTpJj9DKK5AfxHbUSEnkxWaEcw";
    public final String OWNER_ID = "251978573139673088";

    public Config() {
        status = this.STATUS_PREFIX + this.STATUS_MESSAGE;
    }

    public String getPrefix() {
        return this.PREFIX;
    }

    public String getToken() {
        return this.TOKEN;
    }

    public String getOwnerId() {
        return this.OWNER_ID;
    }

    public String getStatus() {
        return status;
    }

    public String setStatus(String status) {
        return this.status = this.STATUS_PREFIX + status;
    }*/


    private final JSONObject config;
    private final File dataFolder;
    private String status;

    public Config(String ressourcesPath) throws IOException, URISyntaxException {
        InputStream in = getClass().getClassLoader().getResourceAsStream(ressourcesPath);
        InputStreamReader inr = new InputStreamReader(in, StandardCharsets.UTF_8);
        char[] cbuf = new char[2048];
        StringBuilder jsonFile = new StringBuilder();
        while (inr.read(cbuf, 0, cbuf.length) != -1) {
            jsonFile.append(new String(cbuf)).append("\n");
        }
        config = new JSONObject(jsonFile.toString());
        status = config.getString("status_prefix") + config.getString("status_message");
        dataFolder = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath().replace("%20", " ") + "/data");
    }

    public String getPrefix() {
        return config.getString("prefix");
    }

    public String getToken() {
        return config.getString("token");
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
