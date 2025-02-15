package item.weapon;

import config.ApplicationProperties;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Knife extends Weapon {

    private static final String picturePath = ApplicationProperties.getHost() + "/images/knife.jpg";

    public Knife() {
        super(UUID.randomUUID().toString(), "Нож", picturePath);
        getDamage().setPhysicalDamage(2.0);
        setThrowing(false);
        setTwoHand(false);
    }
}
