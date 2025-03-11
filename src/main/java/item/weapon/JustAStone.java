package item.weapon;

import config.ApplicationProperties;
import item.weapon.abstracts.Weapon;

import java.util.UUID;

import static constants.GlobalDamage.JUST_A_STONE;

/**
 * Просто камень. Довольно бесполезный, но всё же лучше, чем кулаками.
 */
public class JustAStone extends Weapon {

    private static final String picturePath = ApplicationProperties.getHost() + "/images/weapon/stone.png";

    public JustAStone() {
        super(UUID.randomUUID().toString(), "Камень", picturePath, JUST_A_STONE);

        setAttackSpeed(1.3);

        setThrowing(true);

        setTwoHand(false);
    }

}
