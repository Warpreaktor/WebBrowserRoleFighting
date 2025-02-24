package dto.damage;

import lombok.Getter;

import static constants.GlobalConstants.GLOBAL_DAMAGE_MULTIPLIER;

@Getter
public class Damage {

    /**
     * Физический урон
     */
    private PhysicalDamage physicalDamage;

    /**
     * Огненный урон
     */
    private Double fireDamage = 0.0;

    public Damage() {
        physicalDamage = new PhysicalDamage();
    }

    public void setFireDamage(Double value) {
        fireDamage = value * GLOBAL_DAMAGE_MULTIPLIER;
    }

    public void addFireDamage(Double value) {
        fireDamage += value * GLOBAL_DAMAGE_MULTIPLIER;
    }
}
