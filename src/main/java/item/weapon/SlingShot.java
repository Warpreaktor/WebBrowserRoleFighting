package item.weapon;

import config.ApplicationProperties;
import item.weapon.abstracts.Weapon;

import java.util.UUID;

/**
 * Рогатка. Стань тем самым дворовым хулиганом что стреляет по чужим коровам.
 */
public class SlingShot extends Weapon {

    private static final String picturePath = ApplicationProperties.getHost() + "/images/weapon/slingshot.png";

    public SlingShot() {
        super(UUID.randomUUID().toString(), "Рогатка", picturePath);

        getDamage().setCrushing(1.0, 1.5);

        setAttackSpeed(1.5);

        setThrowing(true);

        setTwoHand(false);
    }

}
