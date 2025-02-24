package item.weapon;

import item.weapon.abstracts.Weapon;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Fist extends Weapon {

    /**
     * У кулака нет картинки, так как он не для взаимодействия.
     * Его нельзя снять и с персонажа и положить в рюкзак.
     */
    private static final String picturePath = "";

    public Fist() {
        super(UUID.randomUUID().toString(), "Кулак", picturePath);

        getDamage().getPhysicalDamage().setCrushingMax(1.0);

        setAttackSpeed(1.0);

        setThrowing(false);

        setTwoHand(false);
    }

}
