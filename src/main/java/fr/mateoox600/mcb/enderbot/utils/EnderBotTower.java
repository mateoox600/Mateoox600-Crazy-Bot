package fr.mateoox600.mcb.enderbot.utils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class EnderBotTower {

    HashMap<Integer, Integer> tower = new HashMap<>();

    public EnderBotTower() throws IOException {
        //BufferedReader reader = new BufferedReader(new FileReader(new File(getClass().getClassLoader().getResource("EnderBotTower.json").getPath().replace("%20", " "))));
        BufferedReader reader = new BufferedReader(new FileReader(new File("file:/app/target/MCB-1.0.0.jar/EnderBotTower.json")));
        StringBuilder jsonFile = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonFile.append(line).append("\n");
        }
        JSONObject t = new JSONObject(jsonFile.toString());
        for(String key : t.keySet()){
            tower.put(Integer.parseInt(key), t.getInt(key));
        }

    }

    public int getBeatableBoss(int def) {
        int atck = (int) Math.floor(def / 1.5);
        int tower_level = 0;
        int boss_attack = 0;
        int idx = 0;
        while (atck >= boss_attack) {
            idx++;
            if (idx > 100) {
                tower_level++;
                break;
            }
            boss_attack = tower.get(idx);
            tower_level++;
        }
        return tower_level - 1;
    }

}
