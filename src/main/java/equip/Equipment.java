package equip;

import item.Item;
import lombok.Getter;
import item.weapon.Fist;
import item.weapon.Weapon;
import lombok.Setter;

/**
 * Экипировка персонажа
 */
@Getter
public class Equipment {

    private Item buffer;

    /**
     * Кулак всегда один и он сохраняется как объект на персонаже.
     * При снятии оружия он становится на его место.
     */
    private final Fist fist;

    /**
     * В правой руке может быть только оружие.
     */
    @Setter
    private Weapon rightHand;

    public Equipment() {
        fist = new Fist();
        rightHand = fist;
    }

    /**
     * Установить предмет в слот
     */
    public boolean equipped(EquipSlot slot, Item item) {
        if (item == null){
            return false;
        }

        switch (slot) {
            case RIGHT_HAND:
                if (item instanceof Weapon) {
                    rightHand = (Weapon) item;
                    break;
            } else {
                    System.out.println("Это не вставить в правую руку");
                    return false;
                }
        }
        return true;
    }

    /**
     * Вытащить предмет из слота.
     * Концепция такая: любой предмет прежде чем переместиться куда-то должен попасть в буфер.
     * Это имитация поведения пользовательского интерфейса,
     * Когда предмет хватают мышкой он как бы подвисает нигде не находясь.
     * Чтобы предмет не потерять совсем будем помещать его в буфер.
     */
    public void unequipped(EquipSlot slot, String itemId) {
        var item = buffer;

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
