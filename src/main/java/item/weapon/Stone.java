package item.weapon;

import config.ApplicationProperties;
import item.weapon.abstracts.Weapon;

import java.util.UUID;

/**
 * Камень. Довольно бесполезный, но всё же лучше чем кулаками.
 */
public class Stone extends Weapon {

    private static final String picturePath = ApplicationProperties.getHost() + "/images/weapon/stone.png";

    public Stone() {
        super(UUID.randomUUID().toString(), "Камень", picturePath);

        getDamage().getPhysicalDamage().setCrushingMax(1.2);

        setAttackSpeed(1.3);

        setThrowing(true);

        setTwoHand(false);
    }

}
