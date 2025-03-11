package item.weapon;

import config.ApplicationProperties;
import item.weapon.abstracts.Weapon;

import java.util.UUID;

import static constants.GlobalDamage.SLING_FROM_ROPE;

/**
 * Праща. ПоПРАЩАйся со своими цельным черепом мужик!
 */
public class Sling extends Weapon {

    private static final String picturePath = ApplicationProperties.getHost() + "/images/weapon/sling.png";

    public Sling() {
        super(UUID.randomUUID().toString(), "Праща", picturePath, SLING_FROM_ROPE);

        setAttackSpeed(0.9);

        setThrowing(true);

        setTwoHand(false);
    }

}
