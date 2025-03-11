package item.weapon;

import config.ApplicationProperties;
import item.weapon.abstracts.Weapon;

import java.util.UUID;

import static constants.GlobalDamage.SLINGSHOT_FROM_BRANCH;

/**
 * Рогатка из ветки. Стань тем самым дворовым хулиганом, что стреляет по чужим коровам.
 */
public class SlingshotFromBranch extends Weapon {

    private static final String picturePath = ApplicationProperties.getHost() + "/images/weapon/slingshot.png";

    public SlingshotFromBranch() {
        super(UUID.randomUUID().toString(), "Рогатка из ветки", picturePath, SLINGSHOT_FROM_BRANCH);

        setAttackSpeed(1.5);

        setThrowing(true);

        setTwoHand(false);
    }

}
