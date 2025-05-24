package item.shield.abstracts;

import hero.Hero;
import item.offhand.Offhand;
import lombok.Getter;
import lombok.Setter;

/**
 * Класс щита
 */
@Getter
@Setter
public abstract class Shield extends Offhand {

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
    }
}
