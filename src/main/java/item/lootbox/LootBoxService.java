package item.lootbox;

import item.Item;
import item.ItemService;
import item.shield.WoodenShield;
import item.shield.abstracts.Shield;
import item.weapon.Knife;
import item.weapon.abstracts.Weapon;
import item.weapon.MagicWand;
import lombok.Getter;
import tools.Dice;

import java.util.ArrayList;
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
            // Решаем, что выпадет: оружие или щит
            if (Dice.randomInt(2) == 0) {
                items.add(weaponLootRandom());
            } else {
                items.add(shieldLootRandom());
            }
        }

        itemService.putAll(items);
        return items;
    }

    public Weapon weaponLootRandom() {

        Weapon[] weapons = {
                new Knife(),
                new MagicWand()
        };

        return weapons[Dice.randomInt(weapons.length)];
    }

    public Shield shieldLootRandom() {
        Shield[] shields = {
                new WoodenShield()
        };
        return shields[Dice.randomInt(shields.length)];
    }
}
