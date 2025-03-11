package mechanic;

import lombok.Getter;
import lombok.Setter;

public class Health {

    /**
     * Максимальное количество жизней у персонажа.
     */
    @Getter
    private double maxValue;

    /**
     * Значение здоровье.
     */
    @Setter
    @Getter
    private double value;

    /**
     * Лечение. Количество пунктов на которые персонаж может вылечиться за фокусировку.
     */
    @Setter
    @Getter
    private double grower;

    @Getter
    private Boolean isDead = false;

    public Health() {
        value = 0.0;
        maxValue = 0.0;
        grower = 0.0;
    }

    public void addGrower(Double value) {
        grower += value;
    }

    public void decreaseGrower(Double value) {
        grower -= value;
    }

    /**
     * Здоровье приращивается на фазе фокусировки.
     * Значение здоровья не может превышать максимальное.
     */
    public void grow() {
        value += grower;

        if (value > maxValue) {
            value = maxValue;
        }
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

    /**
     * Потратить здоровье.
     */
    public void decreaseValue(double value) {
        this.value -= value;
    }

    public void die() {
        value = 0.0;
        isDead = true;
    }
}
