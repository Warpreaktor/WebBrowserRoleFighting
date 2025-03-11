package item.weapon;

import config.ApplicationProperties;
import item.weapon.abstracts.Weapon;

import java.util.UUID;

import static constants.GlobalDamage.SHORTEST_BOW;

/**
 * Короткий лук. Самый короткий лук на районе.
 * Дальность стрельбы — чуть дальше, чем если бы ты сам бросал стрелу.
 */
public class ShortestBow extends Weapon {

    private static final String picturePath = ApplicationProperties.getHost() + "/images/weapon/short_bow.png";

    public ShortestBow() {
        super(UUID.randomUUID().toString(), "Самый короткий лук", picturePath, SHORTEST_BOW);

        setAttackSpeed(0.75);

        setThrowing(true);

        setTwoHand(true);
    }

}
