package item.offhand;

import hero.Hero;
import item.Item;
import lombok.Getter;
import lombok.Setter;

/**
 * Класс для предметов левой руки, но не являющихся щитами.
 * (факелы, книги заклинаний и не только, а также для других различных предметов, которые мы придумаем)
 */
@Getter
@Setter
public abstract class Offhand extends Item {

    /**
     * Магический урон, который может давать предмет для левой руки
     */
    private Double magicDamageBonus;

    /**
     * Бонус к броне/защите
     */
    private Double armorBonus;

    /**
     * Бонус к восстановлению маны/здоровья/энергии
     */
    private Double regenerationBonus;

    public Offhand(String id, String name, String picturePath, Hero owner) {
        super(id, name, picturePath, owner);
    }
}
