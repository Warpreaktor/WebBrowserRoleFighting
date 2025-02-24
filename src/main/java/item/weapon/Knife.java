package item.weapon;

import config.ApplicationProperties;
import item.weapon.abstracts.Weapon;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Knife extends Weapon {

    private static final String picturePath = ApplicationProperties.getHost() + "/images/weapon/knife.png";

    public Knife() {
        super(UUID.randomUUID().toString(), "Нож", picturePath);

        getDamage().getPhysicalDamage().setPiercingMax(2.0);

        setAttackSpeed(1.2);

        setThrowing(false);

        setTwoHand(false);
    }
}
