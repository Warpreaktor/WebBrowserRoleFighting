package effect;

import hero.Hero;
import lombok.Getter;
import mechanic.interfaces.Effect;

/**
 * Усилитель урона
 */
public class DamageBoost implements Effect {

    @Getter
    private final String name = "Усилитель урона";

    private boolean isActive = false;

    private final String description = "Вызывает у героя прилив сил и увеличивает его урон";

    @Getter
    private Hero owner;

    /**
     * Сколько ходов будет действовать бафф.
     */
    @Getter
    private final int duration = 2;

    /**
     * Множитель урона, где 1 это как есть, 1.25 значит увеличить на 25%, 2 значит увеличить в 2 раза.
     */
    @Getter
    private final double damageBoost = 1.5;

    public DamageBoost() {}

    public DamageBoost(Hero owner) {
        this.owner = owner;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void switchOn() {
        owner.getPassiveDamage().localMultiplierPlus(damageBoost);

        this.isActive = true;
    }

    @Override
    public void switchOff() {
        owner.getPassiveDamage().localMultiplierMinus(damageBoost);
        this.isActive = false;
    }

    @Override
    public void trigger() {
        //do something
    }

    @Override
    public void impose(Hero target) {
        target.getEffectStack().impose(
                new DamageBoost(target),
                duration
        );
    }
}
