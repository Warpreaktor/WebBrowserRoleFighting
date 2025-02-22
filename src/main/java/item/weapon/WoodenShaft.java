package item.weapon;

import config.ApplicationProperties;

import java.util.UUID;

/**
 * Деревянная волшебная палочка
 */
public class WoodenShaft  extends Weapon {

    private static final String picturePath = ApplicationProperties.getHost() + "/images/wooden_shaft.jpg";

    public WoodenShaft() {
        super(UUID.randomUUID().toString(), "Деревянная волшебная палочка", picturePath);
        getDamage().setFireDamage(6.0);
        setThrowing(true);
        setTwoHand(false);
    }

}
