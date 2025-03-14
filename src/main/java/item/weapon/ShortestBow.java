package item.weapon;

import config.ApplicationProperties;
import hero.Hero;
import item.weapon.abstracts.Weapon;

import java.util.UUID;

import static constants.WeaponGlobalDamage.SHORTEST_BOW;

/**
 * Короткий лук. Самый короткий лук на районе.
 * Дальность стрельбы — чуть дальше, чем если бы ты сам бросал стрелу.
 */
public class ShortestBow extends Weapon {

    private static final String picturePath = ApplicationProperties.getHost() + "/images/weapon/short_bow.png";

    public ShortestBow(Hero owner) {
        super(UUID.randomUUID().toString(), "Самый короткий лук", picturePath, SHORTEST_BOW, owner);

        setAttackSpeed(0.75);

        setThrowing(true);

        setTwoHand(true);
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
