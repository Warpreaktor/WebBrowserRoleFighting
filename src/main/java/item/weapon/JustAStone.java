package item.weapon;

import config.ApplicationProperties;
import hero.Hero;
import item.weapon.abstracts.Weapon;

import java.util.UUID;

import static constants.WeaponGlobalDamage.JUST_A_STONE;

/**
 * Просто камень. Довольно бесполезный, но всё же лучше, чем кулаками.
 */
public class JustAStone extends Weapon {

    private static final String picturePath = ApplicationProperties.getHost() + "/images/weapon/stone.png";

    public JustAStone(Hero owner) {
        super(UUID.randomUUID().toString(), "Камень", picturePath, JUST_A_STONE, owner);

        setAttackSpeed(1.3);

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
