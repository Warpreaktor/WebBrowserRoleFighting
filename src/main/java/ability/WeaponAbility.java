package ability;

import enums.AbilityTarget;
import item.weapon.abstracts.Weapon;
import lombok.Getter;
import mechanic.Damage;

public abstract class WeaponAbility extends Ability {

    /**
     * Ссылка на владельца способности.
     */
    @Getter
    private final Weapon owner;

    protected WeaponAbility(String picturePath,
                            String name,
                            String description,
                            AbilityTarget type,
                            int cost,
                            int coolDown,
                            Weapon owner) {

        super(picturePath, name, description, type,  cost, coolDown);

        this.owner = owner;
    }

    public Damage getDamage() {
        return owner.getDamage();
    }
}
