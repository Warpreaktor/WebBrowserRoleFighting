package mechanic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class DamageDto {

    /**
     * Физический урон
     */
    private Double physicalDamage;

    /**
     * Огненный урон
     */
    private Double fireDamage;

    public double getFullDamage() {
        return physicalDamage + fireDamage;
    }
}
