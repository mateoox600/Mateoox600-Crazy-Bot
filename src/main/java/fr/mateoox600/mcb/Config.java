package fr.mateoox600.mcb;

import org.json.JSONObject;

import java.io.*;

public class Config {

    private String status;
    private JSONObject config;

    public Config(String ressourcesPath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File(getClass().getClassLoader().getResource(ressourcesPath).getPath().replace("%20", " "))));
        StringBuilder jsonFile = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonFile.append(line).append("\n");
        }
        config = new JSONObject(jsonFile.toString());
        status = config.getString("status_prefix") + config.getString("status_message");
    }

    public String getPrefix(){
        return config.getString("prefix");
    }

    public String getToken(){
        return config.getString("token");
    }

    public String getOwnerId(){
        return config.getString("owner_id");
    }

    public String getStatus(){
        return status;
    }

    public String setStatus(String status){
        return this.status = config.getString("status_prefix") + status;
    }

}
