package effect;

import hero.Hero;
import lombok.Getter;
import mechanic.interfaces.Effect;

/**
 * Удушение.
 * Вызывает у героя слабость на время. Отнимает максимум очков выносливости.
 */
public class Strangulation implements Effect {

    @Getter
    private final String name = "Удушение";

    private boolean isActive = false;

    private final String description = "Вызывает у героя слабость на время. Отнимает очки выносливости.";

    /**
     * Продолжительность удушения.
     */
    public static final int DURATION = 1;

    /**
     * Мощность удушения. Какое количество очков выносливости будет отнято у героя под эффектом.
     */
    public static final int ENDURANCE_DRAIN = 1;

    private Hero owner;

    public Strangulation(){
    }

    public Strangulation(Hero owner){
    this.owner = owner;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void switchOn() {
        owner.getEndurance().decreaseMaxValue(ENDURANCE_DRAIN);
        this.isActive = true;
    }

    @Override
    public void switchOff() {
        owner.getEndurance().addMaxValue(ENDURANCE_DRAIN);
        this.isActive = false;
    }

    @Override
    public void trigger() {
        //do something
    }

    @Override
    public void impose(Hero target) {
        target.getEffectStack().impose(
                new Strangulation(target),
                DURATION
        );
    }
}
