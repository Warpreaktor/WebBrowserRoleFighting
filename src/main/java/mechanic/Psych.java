package mechanic;

import lombok.Getter;
import lombok.Setter;

public class Psych {

    /**
     * Максимальное количество психики у персонажа.
     */
    @Getter
    private double maxValue;

    /**
     * Текущее значение психики.
     */
    @Setter
    @Getter
    private double value;

    /**
     * Восстановление. Количество пунктов на которые персонаж может восстановиться за ход.
     */
    @Setter
    @Getter
    private double grower;

    public Psych() {
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
     * Полностью восстанавливает психику
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
     * Снизить психику.
     */
    public void decreaseValue(double value) {
        this.value -= value;
    }

    /**
     * Возвращает остаточный процент психики.
     *
     * @return 1 значит 100% психики 0.5 значит 50%
     */
    public double getRatio() {
        return value / maxValue;
    }
}
