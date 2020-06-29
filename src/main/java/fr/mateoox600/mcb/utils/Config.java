package fr.mateoox600.mcb.utils;

import org.json.JSONObject;

import java.io.*;

public class Config {

    private String status;

    public final String STATUS_PREFIX = "(In Dev) ";
    public final String STATUS_MESSAGE = "Existing... little bit";
    public final String PREFIX = ".";
    public final String TOKEN = "NzE0MTU1NzY5NTE1MjEyODMx.Xut8Kg.3zEcKAqQX9QHKgaC0c_ogO58wLs";
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
    }


    /*private String status;
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
    }*/

}
