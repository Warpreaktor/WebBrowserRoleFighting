package mechanic;

import core.GameMaster;
import lombok.Getter;
import mechanic.interfaces.Switchable;

@Getter
public class MagicScreen implements Switchable {

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

    private boolean isActive = false;

    @Override
    public boolean isActive() {
        return isActive;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public void setIsBlocked(boolean blocked) {
        isActive = blocked;
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

    public void addMagicScreenGrower(Double value) {
        shieldGrower += value;
    }

    public void decreaseMagicScreenGrower(Double value) {
        shieldGrower -= value;
    }

    public void setShieldGrower(double shieldGrower) {
        this.shieldGrower = shieldGrower;
    }

    /**
     * Щит приращивается на фазе фокусировки.
     * Значение щита не может превышать максимальное.
     */
    public void grow() {
        if (isActive) {
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

        GameMaster.getInstance().switchOn(this, 1);
        
        return remainingDamage;
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
        //do nothing
    }
}
