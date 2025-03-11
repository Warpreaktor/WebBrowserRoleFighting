package item.weapon;

import config.ApplicationProperties;
import dto.MinMax;
import item.weapon.abstracts.Weapon;
import lombok.Getter;

import java.util.UUID;

import static constants.GlobalDamage.KNIFE_STRIKE;

/**
 * Чтобы было чем нарезать хлеб в пути.
 */
@Getter
public class Knife extends Weapon {

    private static final String picturePath = ApplicationProperties.getHost() + "/images/weapon/blade/knife.png";


    public Knife() {
        super(UUID.randomUUID().toString(), "Нож", picturePath, KNIFE_STRIKE);

        setAttackSpeed(1.2);

        setThrowing(false);

        setTwoHand(false);
    }
}
