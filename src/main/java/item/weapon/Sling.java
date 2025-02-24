package item.weapon;

import config.ApplicationProperties;
import item.weapon.abstracts.Weapon;

import java.util.UUID;

/**
 * Праща. ПоПРАЩАйся со своими цельным черепом мужик!
 */
public class Sling extends Weapon {

    private static final String picturePath = ApplicationProperties.getHost() + "/images/weapon/sling.png";

    public Sling() {
        super(UUID.randomUUID().toString(), "Праща", picturePath);

        getDamage().getPhysicalDamage().setCrushingMax(2.0);

        setAttackSpeed(0.9);

        setThrowing(true);

        setTwoHand(false);
    }

}
