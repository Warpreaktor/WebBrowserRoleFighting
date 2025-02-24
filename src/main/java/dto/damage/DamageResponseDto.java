package dto.damage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DamageResponseDto {


    /**
     * Физический урон
     */
    private Double physicalDamage;

    /**
     * Огненный урон
     */
    private Double magicDamage;

    /**
     * Сумма всех типов уронов. Служит больше для получения общей информации, но
     * реально в игре не применяется.
     */
    private Double sumDamage;

    public DamageResponseDto(Double physicalDamage, Double magicDamage) {
        this.physicalDamage = physicalDamage;
        this.magicDamage = magicDamage;
        sumDamage = physicalDamage + magicDamage;
    }

}
