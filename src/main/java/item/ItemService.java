package item;

import item.weapon.Knife;
import item.weapon.abstracts.Weapon;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class ItemService {

    private static ItemService instance;

    private final HashMap<String, Item> itemTable;

    private ItemService() {
        itemTable = new HashMap<>();
    }

    /**
     * Синглтончик
     */
    public static ItemService getInstance() {

        if (instance == null) {
            instance = new ItemService();
            return instance;
        }

        return instance;
    }

    public Optional<Item> get(String itemId) {
        return Optional.of(itemTable.get(itemId));
    }

    public Weapon generate() {
        return new Knife();
    }

    public void putAll(List<Item> items) {
        for (var item : items) {
            itemTable.put(item.getId(), item);
        }
    }
}
