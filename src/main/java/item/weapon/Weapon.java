package item.weapon;

import dto.damage.DamageDto;
import item.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public abstract class Weapon extends Item {

    /**
     * Информация об уроне оружия.
     */
    private DamageDto damage;

    /**
     * Двуручное
     */
    private Boolean twoHand;

    /**
     * Метательное
     */
    private Boolean throwing;

    public Weapon(String id, String name, String picturePath) {
        super(id, name, picturePath);
        damage = new DamageDto();
    }
}
