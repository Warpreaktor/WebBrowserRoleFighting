package ability;

import enums.AbilityTarget;
import hero.Hero;
import lombok.Getter;

public abstract class BuffAbility extends Ability {

    /**
     * Ссылка на владельца способности.
     */
    @Getter
    private final Hero owner;

    protected BuffAbility(Hero owner,
                          String picturePath,
                          String name,
                          String description,
                          AbilityTarget type,
                          int cost,
                          int coolDown
    ) {

        super(picturePath, name, description, type,  cost, coolDown);
        this.owner = owner;
    }
}
