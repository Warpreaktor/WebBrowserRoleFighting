package effect;

import hero.Hero;
import lombok.Getter;
import mechanic.interfaces.Effect;

/**
 * Шок
 */
public class Shock implements Effect {

    @Getter
    private final String name = "Шок";

    private boolean isActive = false;

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void switchOn() {
        this.isActive = true;
    }

    @Override
    public void switchOff() {
        this.isActive = false;
    }

    @Override
    public void trigger() {
        //do something
    }

    @Override
    public void impose(Hero target) {

    }
}
