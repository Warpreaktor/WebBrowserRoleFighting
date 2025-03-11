package dto.hero;

import hero.Statistic;
import lombok.Builder;
import lombok.ToString;

@ToString
@Builder
public class HeroInfoDto {

    private String name;

    private String heroClass;

    private double healthMaxValue;

    private double healthValue;

    private double magicScreenMaxValue;

    private double magicScreenValue;

    private double enduranceValue;

    private double enduranceMaxValue;

    private Statistic statistic;
}
