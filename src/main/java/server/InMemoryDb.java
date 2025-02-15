package server;

import hero.Hero;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryDb {

    private static InMemoryDb instance;

    private final HashMap<String, Map<String, Object>> commonTable;

    private InMemoryDb() {
        this.commonTable = new HashMap<>();

        commonTable.put(Hero.class.getName(), new HashMap<>());
    }

    public static InMemoryDb getInstance() {

        if (instance == null) {
            instance = new InMemoryDb();
            return instance;
        }

        return instance;
    }

    public <T> Object put(T object) {
        return commonTable.get(object.getClass().getName())
                .put(UUID.randomUUID().toString(), object);
    }

    public <T> Object put(String key, T object) {
        return commonTable.get(object.getClass().getName())
                .put(key, object);
    }

    public Optional<Object> get(String id, Class cl) {

        return Optional.of(
                commonTable.get(cl.getClass().getName())
                .get(id));
    }
}
