package item.weapon;

import item.weapon.abstracts.Weapon;

import java.util.UUID;

import static constants.GlobalConstants.WEAPON_PICTURE_PATH;

/**
 * Волшебная палочка ученика. Работает если ей очень быстро махать и что-то бормотать.
 * В крайнем случае, можно вспомнить, что это просто палка и жвахнуть по башке.
 */
public class MagicWand extends Weapon {

    private static final String picturePath = WEAPON_PICTURE_PATH + "/magic_wand.png";

    public MagicWand() {
        super(UUID.randomUUID().toString(), "Волшебная палочка", picturePath);

        getDamage().setFireDamage(4.0);

        setAttackSpeed(0.5);

        setThrowing(true);

        setTwoHand(false);
    }

}
