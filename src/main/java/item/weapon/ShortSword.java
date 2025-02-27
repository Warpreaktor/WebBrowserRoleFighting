package item.weapon;

import config.ApplicationProperties;
import item.weapon.abstracts.Weapon;

import java.util.UUID;

/**
 * Какая игра без короткого меча, верно?
 */
public class ShortSword extends Weapon {

    private static final String picturePath = ApplicationProperties.getHost() + "/images/weapon/short_sword.png";

    public ShortSword(String id, String name, String picturePath) {
        super(UUID.randomUUID().toString(), "Короткий меч", picturePath);

        getDamage().setPiercing(0.9, 1.5);
        getDamage().setCrushing(1.0, 1.0);

        setAttackSpeed(1.0);
        setThrowing(false);

        // Позволяет использовать меч в любой руке (dual wielding)
        setAsDualWearable();
    }
}
