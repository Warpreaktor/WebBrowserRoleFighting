package item.weapon;

import java.util.HashMap;
import java.util.Optional;

public class ItemService {

    private static ItemService instance;

    private final HashMap<String, Weapon> weaponTable;

    private ItemService() {
        weaponTable = new HashMap<>();
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

    public Optional<Weapon> get(String weaponId) {
        return Optional.of(weaponTable.get(weaponId));
    }

    public Weapon generate() {
        return new Knife();
    }

}
