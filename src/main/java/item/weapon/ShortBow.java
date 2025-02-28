package item.weapon;

import config.ApplicationProperties;
import item.weapon.abstracts.Weapon;

import java.util.UUID;

/**
 * Короткий лук. Самый короткий лук на районе.
 * Дальность стрельбы — чуть дальше, чем если бы ты сам бросал стрелу.
 */
public class ShortBow extends Weapon {

    private static final String picturePath = ApplicationProperties.getHost() + "/images/weapon/short_bow.png";

    public ShortBow() {
        super(UUID.randomUUID().toString(), "Короткий лук", picturePath);

        getDamage().setPiercing(2.0, 3.0);

        setAttackSpeed(0.75);

        setThrowing(true);

        setTwoHand(true);
    }

}
