package item.weapon;

import ability.item.FistStrike;
import item.weapon.abstracts.Weapon;
import lombok.Getter;
import mechanic.Ability;

import java.util.List;
import java.util.UUID;

import static constants.GlobalDamage.FIST_STRIKE;

@Getter
public class Fist extends Weapon {

    /**
     * У кулака нет картинки, так как он не для взаимодействия.
     * Его нельзя снять с персонажа и положить в рюкзак.
     */
    private static final String PICTURE_PATH = "";

    private static final String NAME = "Кулак";

    public Fist() {
        super(UUID.randomUUID().toString(), NAME, PICTURE_PATH, FIST_STRIKE);

        getAbilities().add(new FistStrike(this));

        setAttackSpeed(1.0);

        setThrowing(false);

        setTwoHand(false);
    }

}
