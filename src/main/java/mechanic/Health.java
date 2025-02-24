package mechanic;

import lombok.Getter;
import mechanic.interfaces.Healthy;

@Getter
public class Health implements Healthy {

    /**
     * Значение здоровье.
     */
    private Double value;

    /**
     * Лечение. Количество пунктов на которые персонаж может вылечиться за фокусировку.
     */
    private Double heal;

    /**
     * Максимальное количество жизней у персонажа.
     */
    private Double maxValue;

    private Boolean isDead = false;

    public Health() {
        value = 0.0;
        maxValue = 0.0;
        heal = 0.0;
    }

    public void setHeal(Double value) {
        heal = value;
    }

    public void addHeal(Double value) {
        heal += value;
    }

    public void decreaseHeal(Double value) {
        heal -= value;
    }

    /**
     * Здоровье приращивается на фазе фокусировки.
     * Значение здоровья не может превышать максимальное.
     */
    public void heal() {
        value += heal;

        if (value > maxValue) {
            value = maxValue;
        }
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;

        if (this.maxValue < value) {
            value = this.maxValue;
        }
    }

    /**
     * Полностью поправляет здоровье
     */
    public void fillUp() {
        value = maxValue;
    }

    public void addMaxValue(Double value) {
        maxValue += value;
    }

    public void decreaseMaxValue(Double value) {
        maxValue -= value;
        this.value -= value;
    }

    @Override
    public Health getHealth() {
        return this;
    }

    /**
     * Удар по здоровью.
     */
    @Override
    public void takeDamage(double damage) {
        value -= damage;
    }

    public void dead() {
        value = 0.0;
    }
}
