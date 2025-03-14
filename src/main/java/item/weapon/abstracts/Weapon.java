package item.weapon.abstracts;

import hero.Hero;
import mechanic.Damage;
import item.WearableItem;
import lombok.Getter;
import lombok.Setter;

/**
 * Класс оружия
 */
public abstract class Weapon extends WearableItem {

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

        // По умолчанию оружие можно держать в правой руке
        setRightHand(true);
        setLeftHand(false); // Большинство оружия нельзя держать в левой руке
        setTwoHand(false);  // По умолчанию не двуручное
        setThrowing(false);  // По умолчанию не метательное
    }

    /**
     * Устанавливает оружие как двуручное
     */
    protected void setAsTwoHand() {
        setTwoHand(true);
        setRightHand(false);
        setLeftHand(false);
    }

    /**
     * Устанавливает оружие как одноручное, которое можно держать в любой руке
     */
    protected void setAsDualWearable() {
        setTwoHand(false);
        setRightHand(true);
        setLeftHand(true);
    }
}
