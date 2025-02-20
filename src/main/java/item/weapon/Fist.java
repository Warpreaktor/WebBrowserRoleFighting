package item.weapon;

import config.ApplicationProperties;
import dto.damage.DamageDto;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Fist extends Weapon {

    /**
     * У кулака нет картинки, так как он не для взаимодействия.
     * Его нельзя снятьи с персонажа и положить в рюкзак.
     */
    private static final String picturePath = "";

    public Fist() {
        super(UUID.randomUUID().toString(), "кулак", picturePath);
        getDamage().setPhysicalDamage(4.0);
        setThrowing(false);
        setTwoHand(false);
    }

}
