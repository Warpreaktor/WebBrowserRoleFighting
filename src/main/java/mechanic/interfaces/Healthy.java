package mechanic.interfaces;

import mechanic.Health;

/**
 * Имеет здоровье, может лечиться.
 */
public interface Healthy {

    Health getHealth();

    default void takeDamage(double pain) {
        getHealth()
                .takeDamage(pain);
    }

    default void setHeal(Double value) {
        getHealth().setHeal(value);
    }

    default void heal() {
        getHealth().heal();
    }
}
