package mechanic;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicBoolean;

@Getter
public class Shield {

    private Double value = 0D;

    /**
     * Накопитель магического щита.
     * Число, на которое прирастает магический щит во время фокусировки.
     */
    double shieldGrower = 0.0;

    /**
     * Максимальное значение магического щита.
     */
    private Double maxValue = 0D;

    private AtomicBoolean isBlocked = new AtomicBoolean(false);

    public void setValue(Double value) {
        this.value = value;
    }

    public void setIsBlocked(boolean blocked) {
        isBlocked.set(blocked);
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

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

    public void addShieldGrower(Double value) {
        shieldGrower += value;
    }

    public void decreaseShieldGrower(Double value) {
        shieldGrower -= value;
    }

    public void setShieldGrower(double shieldGrower) {
        this.shieldGrower = shieldGrower;
    }

    /**
     * Щит приращивается на фазе фокусировки.
     * Значение щита не может превышать максимальное.
     */
    public void shieldGrow() {
        if (isBlocked.get()) {
            return;
        }

        value += shieldGrower;
        if (value > maxValue) {
            value = maxValue;
        }
    }

    /**
     * Магический щит поглощает урон.
     *
     * @param damage урон, который пришёлся на магический щит
     * @return остаточный урон, который не был поглощен щитом.
     */
    public double takeDamage(double damage) {

        if (damage <= 0) {
            return 0.0;
        }

        // Весь урон поглощен щитом
        if (damage <= value) {
            value -= damage;
            return 0.0;
        }

        // Вычисляем остаток урона
        double remainingDamage = damage - value;
        value = 0.0;
        return remainingDamage;
    }
}
