package item.weapon.abstracts;

import hero.Hero;
import item.Item;
import mechanic.Damage;
import lombok.Getter;
import lombok.Setter;

/**
 * Класс оружия
 */
public abstract class Weapon extends Item {

    /**
     * Информация об уроне.
     */
    @Getter
    private Damage damage;

    /**
     * Метательное
     */
    @Getter
    @Setter
    private Boolean throwing;

    public Weapon(String id, String name, String picturePath, Damage damage, Hero owner) {
        super(id, name, picturePath, owner);
        this.damage = damage;

        setThrowing(false);  // По умолчанию не метательное
    }
}
