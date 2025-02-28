package dto.hero;

import hero.Statistic;
import lombok.Builder;
import lombok.ToString;

@ToString
@Builder
public class HeroInfoDto {

    private String name;

    private String heroClass;

    private double hitpoint;

    private double mageShield;

    private double maxMageShield;

    private double endurance;

    private double maxHitpoint;

    private Statistic statistic;
}
