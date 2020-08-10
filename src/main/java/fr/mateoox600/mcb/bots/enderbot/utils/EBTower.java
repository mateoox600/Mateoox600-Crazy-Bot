package fr.mateoox600.mcb.bots.enderbot.utils;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class EBTower {

    HashMap<Integer, Integer> tower = new HashMap<>();

    public EBTower() throws IOException {

        InputStream in = getClass().getClassLoader().getResourceAsStream("EnderBotTower.json");
        InputStreamReader inr = new InputStreamReader(in, StandardCharsets.UTF_8);
        char[] cbuf = new char[2048];
        StringBuilder jsonFile = new StringBuilder();
        while (inr.read(cbuf, 0, cbuf.length) != -1) {
            jsonFile.append(new String(cbuf)).append("\n");
        }

        JSONObject t = new JSONObject(jsonFile.toString());
        for (String key : t.keySet()) {
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
