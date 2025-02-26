package item.weapon.abstracts;

import mechanic.Damage;
import item.Item;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Weapon extends Item {

    /**
     * Информация об уроне оружия.
     */
    private Damage damage;

    /**
     * Двуручное
     */
    private Boolean twoHand;

    /**
     * Метательное
     */
    private Boolean throwing;

    /**
     * Скорость атаки оружия.
     * Скорость атаки будет помножена на agility героя,
     * тем самым, либо ускоряя наполнение шкалы автоатаки, либо замедляя её.
     */
    private double attackSpeed;

    public Weapon(String id, String name, String picturePath) {
        super(id, name, picturePath);
        damage = new Damage();
    }
}
