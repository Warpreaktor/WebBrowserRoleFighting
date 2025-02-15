package equip;

import item.Item;
import lombok.Getter;
import item.weapon.Fist;
import item.weapon.Weapon;

import java.util.HashMap;

/**
 * Экипировка персонажа
 */
@Getter
public class Equipement {

    private HashMap<String, Item> itemPool;

    private Item buffer;

    /**
     * Кулак всегда один и он сохраняется как объект на персонаже.
     * При снятии оружия он становится на его место.
     */
    private final Fist fist;

    /**
     * В правой руки может быть только оружие.
     */
    private Weapon rightHand;

    public Equipement() {
        fist = new Fist();
        rightHand = fist;
    }

    /**
     * Установить предмет в слот
     */
    public void equipped(EquipSlot slot, Item item) {

        switch (slot) {
            case RIGHT_HAND:
                if (item instanceof Weapon) {
                    rightHand = (Weapon) item;
            } else {
                    throw new RuntimeException("Это не вставить в правую руку");
                }
        }
    }

    /**
     * Вытащить предмет из слота.
     * Концепция такая: любой предмет прежде чем переместиться куда-то должен попасть в буфер.
     * Это имитация поведения пользовательского интерфейса,
     * Когда предмет хватают мышкой он как бы подвисает нигде не находясь.
     * Чтобы предмет не потерять совсем будем помещать его в буфер указывая сооветсвующий ключ в ite
     */
    public void unequipped(EquipSlot slot, String itemId) {
        var item = itemPool.get(itemId + slot);

        if (item == null) {
            throw new RuntimeException("Неизвестный предмет");
        } else {
            buffer = item;
        }

        switch (slot) {
            case RIGHT_HAND -> rightHand = fist;
        }
    }
}
