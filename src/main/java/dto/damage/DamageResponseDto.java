package dto.damage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class DamageResponseDto {


    private Double fullDamage;

    /**
     * Физический урон
     */
    private Double physicalDamage;

    /**
     * Огненный урон
     */
    private Double fireDamage;

    public Double getFullDamage() {
        return physicalDamage + fireDamage;
    }
}
