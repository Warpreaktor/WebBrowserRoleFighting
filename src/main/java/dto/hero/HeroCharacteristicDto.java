package dto.hero;

import dto.damage.DamageResponseDto;
import lombok.Builder;
import lombok.ToString;

@ToString
@Builder
public class HeroCharacteristicDto {

    private String name;

    private String heroClass;

    private double health;

    private double maxHealth;

    private double shield;

    private double maxShield;

    private double accuracy;

    private double agility;

    private double evasion;

    private DamageResponseDto damage;

}
