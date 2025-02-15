package dto.damage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DamageDto {

    /**
     * Физический урон
     */
    private Double physicalDamage;

    /**
     * Огненный урон
     */
    private Double fireDamage;

    public DamageDto() {
        physicalDamage = 0.0;
        fireDamage = 0.0;
    }

    public double getFullDamage() {
        return physicalDamage + fireDamage;
    }
}
