package item.shield.abstracts;

import hero.Hero;
import item.WearableItem;
import lombok.Getter;
import lombok.Setter;

/**
 * Класс щита
 */
@Getter
@Setter
public abstract class Shield extends WearableItem {

    /**
     * Блокирование физического урона
     */
    private double physicalBlock;

    /**
     * Блокирование магического урона
     */
    private double magicBlock;

    /**
     * Бонус к уклонению
     */
    private double evasionBonus;


    public Shield(String id, String name, String picturePath, Hero owner) {
        super(id, name, picturePath, owner);

        // Щиты по умолчанию экипируются только в левую руку
        setLeftHand(true);
        setRightHand(false);
        setTwoHand(false);

        // щиты замедляют атаку
        setAttackSpeed(0.9);
    }
}
