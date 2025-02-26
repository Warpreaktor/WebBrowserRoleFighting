package dto.damage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class DamageDto {

    /**
     * Колющий урон
     */
    private Double piercing;

    /**
     * Режущий урон
     */
    private Double cutting;

    /**
     * Дробящий урон
     */
    private Double crushing;

    /**
     * Огненный урон
     */
    private Double fire;

    public double getSumDamage() {

        return piercing
                + cutting
                + crushing
                + fire;
    }

    public double getAllPhysical() {

        return piercing
                + cutting
                + crushing;
    }
}
