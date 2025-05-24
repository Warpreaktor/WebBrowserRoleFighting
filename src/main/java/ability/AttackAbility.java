package ability;

import enums.AbilityTarget;
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
                            String description,
                            AbilityTarget type,
                            int cost,
                            int coolDown,
                            Effect effect
    ) {

        super(picturePath, name, description,type, cost, coolDown);
        this.owner = owner;
        this.effect = effect;
    }
}
