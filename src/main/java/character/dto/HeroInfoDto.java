package character.dto;

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

    private double reloader;

    private double maxHitpoint;
}
