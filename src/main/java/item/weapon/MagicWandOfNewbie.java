package item.weapon;

import hero.Hero;
import item.weapon.abstracts.Weapon;

import java.util.UUID;

import static constants.GlobalConstants.WEAPON_PICTURE_PATH;
import static constants.WeaponGlobalDamage.MAGIC_WAND_OF_NEWBIE;

/**
 * Волшебная палочка новичка. Работает если ей очень быстро махать и что-то бормотать.
 * В крайнем случае, можно вспомнить, что это просто палка и жвахнуть по башке.
 */
public class MagicWandOfNewbie extends Weapon {

    private static final String picturePath = WEAPON_PICTURE_PATH + "/magic_wand.png";

    public MagicWandOfNewbie(Hero owner) {
        super(UUID.randomUUID().toString(), "Волшебная палочка", picturePath, MAGIC_WAND_OF_NEWBIE, owner);

        setAttackSpeed(0.5);

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
