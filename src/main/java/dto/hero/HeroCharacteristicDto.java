package dto.hero;

import dto.damage.DamageResponseDto;
import lombok.Builder;
import lombok.ToString;

@ToString
@Builder
public class HeroCharacteristicDto {

    private String name;

    private String heroClass;

    private double healthValue;

    private double healthMaxValue;

    private double magicScreenValue;

    private double magicScreenMaxValue;

    private double enduranceMaxValue;

    private double enduranceValue;

    private double accuracy;

    private double evasion;

    private DamageResponseDto damage;

}
