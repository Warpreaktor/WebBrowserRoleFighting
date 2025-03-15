package item.shield;

import config.ApplicationProperties;
import hero.Hero;
import item.shield.abstracts.Shield;

import java.util.UUID;

/**
 * Простой деревянный щит напоминающий кусок двери, который можно найти в начале игры.
 */
public class WoodenShield extends Shield {

    private static final String picturePath = ApplicationProperties.getHost() + "/images/shield/wooden_shield.png";

    public WoodenShield(Hero owner) {
        super(UUID.randomUUID().toString(), "Деревянный щит", picturePath, owner);

        setPhysicalBlock(0.3);
        setEvasionBonus(0.05);

        setLeftHand(true);
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
