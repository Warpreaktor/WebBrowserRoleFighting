package ability;

import enums.AbilityType;
import hero.Hero;
import lombok.Getter;
import mechanic.interfaces.Effect;

public abstract class AttackAbility extends Ability {

    /**
     * Ссылка на владельца способности.
     */
    @Getter
    private final Hero owner;

    /**
     * Класс накладываемого способностью эффекта.
     */
    @Getter
    private final Effect effect;

    protected AttackAbility(Hero owner,
                            String picturePath,
                            String name,
                            AbilityType type,
                            String description,
                            int cost,
                            int coolDown,
                            Effect effect
    ) {

        super(picturePath, name, type, description, cost, coolDown);
        this.owner = owner;
        this.effect = effect;
    }
}
