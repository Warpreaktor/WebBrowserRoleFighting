package dto.damage;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DamageDto {

    /**
     * Физический урон
     */
    private Double physicalDamage;

    /**
     * Огненный урон
     */
    private Double magicDamage;

    public double getFullDamage() {
        return physicalDamage + magicDamage;
    }
}
