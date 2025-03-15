package item.weapon;

import config.ApplicationProperties;
import hero.Hero;
import item.weapon.abstracts.Weapon;
import lombok.Getter;

import java.util.UUID;

import static constants.WeaponGlobalDamage.KNIFE_STRIKE;

/**
 * Чтобы было чем нарезать хлеб в пути.
 */
@Getter
public class Knife extends Weapon {

    private static final String picturePath = ApplicationProperties.getHost() + "/images/weapon/blade/knife.png";


    public Knife(Hero owner) {
        super(UUID.randomUUID().toString(), "Нож", picturePath, KNIFE_STRIKE, owner);

        setAttackSpeed(1.2);

        setThrowing(false);

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
