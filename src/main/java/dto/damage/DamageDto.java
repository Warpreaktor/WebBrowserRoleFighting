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

    /**
     * "Электрический урон
     */
    private Double electric;

    public DamageDto() {
        this.piercing = 0.0;
        this.cutting = 0.0;
        this.crushing = 0.0;
        this.fire = 0.0;
        this.electric = 0.0;
    }

    public double getSumDamage() {

        return piercing
                + cutting
                + crushing
                + fire
                + electric;
    }

    public double getAllPhysical() {

        return piercing
                + cutting
                + crushing;
    }

    public double getAllMagic() {

        return fire
                + electric;
    }
}
