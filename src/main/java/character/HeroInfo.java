package character;

import character.dto.HeroInfoDto;
import mechanic.Health;
import mechanic.Shield;
import spec.HeroClass;

public interface HeroInfo {

    String getName();

    HeroClass getHeroClass();

    Health getHealth();

    Double getReloader();

    Shield getShield();

    default HeroInfoDto getInfo() {
        return HeroInfoDto
                .builder()
                .name(getName())
                .heroClass(getHeroClass().name())
                .hitpoint(getHealth().getValue())
                .maxHitpoint(getHealth().getMaxValue())
                .mageShield(getShield().getValue())
                .maxMageShield(getShield().getMaxValue())
                .reloader(getReloader())
                .build();
    }
}
