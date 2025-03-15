package ability;

import enums.AbilityType;
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
                          AbilityType type,
                          String description,
                          int cost,
                          int coolDown
    ) {

        super(picturePath, name, type, description, cost, coolDown);
        this.owner = owner;
    }
}
