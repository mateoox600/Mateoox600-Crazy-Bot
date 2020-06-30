package fr.mateoox600.mcb.enderbot.utils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class EBTower {

    HashMap<Integer, Integer> tower = new HashMap<>();

    public EBTower() throws IOException {
        /*BufferedReader reader = new BufferedReader(new FileReader(new File(getClass().getClassLoader().getResource("EnderBotTower.json").getPath().replace("%20", " "))));
        StringBuilder jsonFile = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonFile.append(line).append("\n");
        }*/
        JSONObject t = new JSONObject("{\n" +
                "  \"1\": 10,\n" +
                "  \"2\": 20,\n" +
                "  \"3\": 50,\n" +
                "  \"4\": 100,\n" +
                "  \"5\": 150,\n" +
                "  \"6\": 200,\n" +
                "  \"7\": 250,\n" +
                "  \"8\": 325,\n" +
                "  \"9\": 375,\n" +
                "  \"10\": 450,\n" +
                "  \"11\": 500,\n" +
                "  \"12\": 550,\n" +
                "  \"13\": 600,\n" +
                "  \"14\": 650,\n" +
                "  \"15\": 700,\n" +
                "  \"16\": 800,\n" +
                "  \"17\": 850,\n" +
                "  \"18\": 950,\n" +
                "  \"19\": 1025,\n" +
                "  \"20\": 1100,\n" +
                "  \"21\": 1200,\n" +
                "  \"22\": 1300,\n" +
                "  \"23\": 1425,\n" +
                "  \"24\": 1475,\n" +
                "  \"25\": 1575,\n" +
                "  \"26\": 1650,\n" +
                "  \"27\": 1800,\n" +
                "  \"28\": 1850,\n" +
                "  \"29\": 1900,\n" +
                "  \"30\": 2000,\n" +
                "  \"31\": 2100,\n" +
                "  \"32\": 2200,\n" +
                "  \"33\": 2300,\n" +
                "  \"34\": 2400,\n" +
                "  \"35\": 2500,\n" +
                "  \"36\": 2600,\n" +
                "  \"37\": 2700,\n" +
                "  \"38\": 2900,\n" +
                "  \"39\": 3250,\n" +
                "  \"40\": 3500,\n" +
                "  \"41\": 4500,\n" +
                "  \"42\": 4750,\n" +
                "  \"43\": 5000,\n" +
                "  \"44\": 5400,\n" +
                "  \"45\": 5500,\n" +
                "  \"46\": 5700,\n" +
                "  \"47\": 6200,\n" +
                "  \"48\": 6500,\n" +
                "  \"49\": 6750,\n" +
                "  \"50\": 7000,\n" +
                "  \"51\": 7400,\n" +
                "  \"52\": 7600,\n" +
                "  \"53\": 7750,\n" +
                "  \"54\": 8000,\n" +
                "  \"55\": 8250,\n" +
                "  \"56\": 8350,\n" +
                "  \"57\": 8500,\n" +
                "  \"58\": 8750,\n" +
                "  \"59\": 9250,\n" +
                "  \"60\": 10000,\n" +
                "  \"61\": 13000,\n" +
                "  \"62\": 15750,\n" +
                "  \"63\": 16500,\n" +
                "  \"64\": 17000,\n" +
                "  \"65\": 17750,\n" +
                "  \"66\": 18500,\n" +
                "  \"67\": 19500,\n" +
                "  \"68\": 20500,\n" +
                "  \"69\": 21250,\n" +
                "  \"70\": 22000,\n" +
                "  \"71\": 23500,\n" +
                "  \"72\": 24250,\n" +
                "  \"73\": 25000,\n" +
                "  \"74\": 26000,\n" +
                "  \"75\": 27500,\n" +
                "  \"76\": 31000,\n" +
                "  \"77\": 30000,\n" +
                "  \"78\": 31000,\n" +
                "  \"79\": 33500,\n" +
                "  \"80\": 40000,\n" +
                "  \"81\": 45000,\n" +
                "  \"82\": 50000,\n" +
                "  \"83\": 55000,\n" +
                "  \"84\": 60000,\n" +
                "  \"85\": 65000,\n" +
                "  \"86\": 70000,\n" +
                "  \"87\": 80000,\n" +
                "  \"88\": 90000,\n" +
                "  \"89\": 100000,\n" +
                "  \"90\": 110000,\n" +
                "  \"91\": 120000,\n" +
                "  \"92\": 130000,\n" +
                "  \"93\": 140000,\n" +
                "  \"94\": 150000,\n" +
                "  \"95\": 160000,\n" +
                "  \"96\": 170000,\n" +
                "  \"97\": 180000,\n" +
                "  \"98\": 190000,\n" +
                "  \"99\": 200000,\n" +
                "  \"100\": 999999\n" +
                "}");
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
