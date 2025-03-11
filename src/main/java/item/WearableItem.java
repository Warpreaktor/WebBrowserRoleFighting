package item;

import equip.EquipSlot;
import lombok.Getter;
import lombok.Setter;
import mechanic.Ability;

import java.util.List;

/**
 * Это класс для всех предметов, которые можно экипировать.
 * Определяет, какие слоты может занимать предмет.
 */
@Getter
@Setter
public abstract class WearableItem extends Item {

    /**
     * Для правой руки
     */
    private Boolean rightHand;

    /**
     * Для левой руки
     */
    private Boolean leftHand;

    /**
     * Двуручное
     */
    private Boolean twoHand;

    /**
     * Скорость атаки оружия.
     * Скорость атаки будет помножена на agility героя,
     * тем самым, либо ускоряя наполнение шкалы автоатак, либо замедляя её.
     */
    private double attackSpeed;

    public WearableItem(String id, String name, String picturePath) {
        super(id, name, picturePath);
    }

    /**
     * Проверяет, можно ли экипировать предмет в указанный слот
     */
    public Boolean canEquipToSlot(EquipSlot slot) {
        if (twoHand) {
            return slot == EquipSlot.BOTH_HANDS;
        }

        return switch (slot) {
            case RIGHT_HAND -> rightHand;
            case LEFT_HAND -> leftHand;
            default -> false;
        };
    }
}
