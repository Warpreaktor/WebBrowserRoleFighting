package item.weapon.abstracts;

import mechanic.Damage;
import item.WearableItem;
import lombok.Getter;
import lombok.Setter;

/**
 * Класс оружия
 */
@Getter
@Setter
public abstract class Weapon extends WearableItem {

    /**
     * Информация об уроне оружия.
     */
    private Damage damage;

    /**
     * Метательное
     */
    private Boolean throwing;

    public Weapon(String id, String name, String picturePath) {
        super(id, name, picturePath);
        damage = new Damage();

        // По умолчанию оружие можно держать в правой руке
        setRightHand(true);
        setLeftHand(false); // Большинство оружия нельзя держать в левой руке
        setTwoHand(false);  // По умолчанию не двуручное
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
