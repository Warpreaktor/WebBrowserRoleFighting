package mechanic;

import lombok.Getter;
import lombok.Setter;

import static constants.GlobalConstants.ENDURANCE_GROWER_PER_DEXTERITY_MULTIPLIER;
import static constants.GlobalConstants.ENDURANCE_VALUE_PER_DEXTERITY_MULTIPLIER;

public class Endurance {

    /**
     * Значение выносливости. Выносливость влияет на очки выносливости.
     */
    @Getter
    private int maxValue;

    /**
     * Очки выносливости персонажа. Каждый раз когда он действует они тратятся.
     */
    @Setter
    @Getter
    private int value;

    /**
     * Скорость восстановления. Влияет на сколько очков за раунд поднимается endurancePoint.
     */
    @Setter
    @Getter
    private int grower;

    public Endurance() {
        maxValue = (int) (0 * ENDURANCE_VALUE_PER_DEXTERITY_MULTIPLIER);
        value = 0;
        grower = (int) (0 * ENDURANCE_GROWER_PER_DEXTERITY_MULTIPLIER);
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

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;

        if (this.maxValue < this.value) {
            this.value = this.maxValue;
        }
    }

    /**
     * Полностью восстанавливает выносливость
     */
    public void fillUp() {
        value = maxValue;
    }

    public void addMaxValue(int value) {
        maxValue += value;
    }

    public void decreaseMaxValue(int value) {
        maxValue -= value;
        this.maxValue -= value;
    }

    /**
     * Потратить выносливость.
     */
    public void decreaseValue(double value) {
        this.value -= value;
    }

}
