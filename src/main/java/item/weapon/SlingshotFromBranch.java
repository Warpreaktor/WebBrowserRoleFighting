package item.weapon;

import config.ApplicationProperties;
import hero.Hero;
import item.weapon.abstracts.Weapon;

import java.util.UUID;

import static constants.WeaponGlobalDamage.SLINGSHOT_FROM_BRANCH;

/**
 * Рогатка из ветки. Стань тем самым дворовым хулиганом, что стреляет по чужим коровам.
 */
public class SlingshotFromBranch extends Weapon {

    private static final String picturePath = ApplicationProperties.getHost() + "/images/weapon/slingshot.png";

    public SlingshotFromBranch(Hero owner) {
        super(UUID.randomUUID().toString(), "Рогатка из ветки", picturePath, SLINGSHOT_FROM_BRANCH, owner);

        setAttackSpeed(1.5);

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
