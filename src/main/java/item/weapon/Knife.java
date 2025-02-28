package item.weapon;

import config.ApplicationProperties;
import dto.MinMax;
import item.weapon.abstracts.Weapon;
import lombok.Getter;

import java.util.UUID;

/**
 * Чтобы было чем нарезать хлеб в пути.
 */
@Getter
public class Knife extends Weapon {

    private static final String picturePath = ApplicationProperties.getHost() + "/images/weapon/blade/knife.png";

    public Knife() {
        super(UUID.randomUUID().toString(), "Нож", picturePath);

        getDamage().setPiercing(1.0, 2.0);

        setAttackSpeed(1.2);

        setThrowing(false);

        setTwoHand(false);
    }
}
