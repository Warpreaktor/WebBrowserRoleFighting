package item.weapon;

import ability.item.FistStrike;
import hero.Hero;
import item.weapon.abstracts.Weapon;
import lombok.Getter;

import java.util.UUID;

import static constants.WeaponGlobalDamage.FIST_STRIKE;

@Getter
public class Fist extends Weapon {

    /**
     * У кулака нет картинки, так как он не для взаимодействия.
     * Его нельзя снять с персонажа и положить в рюкзак.
     */
    private static final String PICTURE_PATH = "";

    private static final String NAME = "Кулак";

    public Fist(Hero owner) {
        super(UUID.randomUUID().toString(), NAME, PICTURE_PATH, FIST_STRIKE, owner);

        getAbilities().add(new FistStrike(this));

        setAttackSpeed(1.0);

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

    public void exhaustion(double cost) {
        getOwner().exhaustion(cost);
    }
}
