package hero;

import java.util.HashMap;

public class HeroTable {

    private static HeroTable instance;

    private final HashMap<String, Hero> table;

    private HeroTable() {
        table = new HashMap<>();
    }

    public static HeroTable getInstance() {

        if (instance == null) {
            instance = new HeroTable();
            return instance;
        }

        return instance;
    }

    public Hero save(String key, Hero hero) {
        table.put(key, hero);
        return hero;
    }

    public Hero get(String key) {
        return table.get(key);
    }
}
