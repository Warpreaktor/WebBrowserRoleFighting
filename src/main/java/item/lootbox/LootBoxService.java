package item.lootbox;

import item.Item;
import item.weapon.ItemService;
import item.weapon.Knife;
import item.weapon.Weapon;
import item.weapon.WoodenShaft;
import lombok.Getter;
import tools.Dice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class LootBoxService {

    private static LootBoxService instance;

    private static ItemService itemService;

    private LootBoxService() {
        itemService = ItemService.getInstance();
    }

    /**
     * Синглтончик
     */
    public static LootBoxService getInstance() {

        if (instance == null) {
            instance = new LootBoxService();
            return instance;
        }

        return instance;
    }

    public List<Item> generateLoot() {
        ArrayList<Item> items = new ArrayList<>();

        for (int i = 0; i < Dice.rollThree(); i++) {
            items.add(weaponLootRandom());
        }

        itemService.putAll(items);
        return items;
    }

    public Weapon weaponLootRandom() {

        Weapon[] weapons = {
                new Knife(),
                new WoodenShaft()
        };

        return weapons[Dice.randomInt(weapons.length)];
    }
}
