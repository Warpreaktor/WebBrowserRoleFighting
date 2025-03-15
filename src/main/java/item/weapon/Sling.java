package item.weapon;

import config.ApplicationProperties;
import hero.Hero;
import item.weapon.abstracts.Weapon;

import java.util.UUID;

import static constants.WeaponGlobalDamage.SLING_FROM_ROPE;

/**
 * Праща. ПоПРАЩАйся со своими цельным черепом мужик!
 */
public class Sling extends Weapon {

    private static final String picturePath = ApplicationProperties.getHost() + "/images/weapon/sling.png";

    public Sling(Hero owner) {
        super(UUID.randomUUID().toString(), "Праща", picturePath, SLING_FROM_ROPE, owner);

        setAttackSpeed(0.9);

        setThrowing(true);

        setTwoHand(false);
    }

    @Override
    public void equiped(Hero owner) {
        owner.addAbilities(getAbilities());
    }

    @Override
    public void unequiped(Hero owner) {
        owner.getAbilities().removeAll(getAbilities());
    }
}
