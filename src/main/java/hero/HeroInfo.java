package hero;

import dto.damage.DamageDto;
import dto.damage.DamageResponseDto;
import dto.hero.HeroInfoDto;
import dto.hero.HeroCharacteristicDto;
import mechanic.Health;
import mechanic.Shield;
import spec.HeroClass;

public interface HeroInfo {

    String getName();

    HeroClass getHeroClass();

    Health getHealth();

    Double getEndurance();

    Shield getShield();

    Double getAccuracy();

    Double getAgility();

    Double getEvasion();

    DamageDto getDamageDto();

    Statistic getStatistic();

    default HeroInfoDto getInfo() {
        return HeroInfoDto
                .builder()
                .name(getName())
                .statistic(getStatistic())
                .heroClass(getHeroClass().name())
                .hitpoint(getHealth().getValue())
                .maxHitpoint(getHealth().getMaxValue())
                .mageShield(getShield().getValue())
                .maxMageShield(getShield().getMaxValue())
                .endurance(getEndurance())
                .build();
    }

    default HeroCharacteristicDto getCharacteristic() {
        return HeroCharacteristicDto
                .builder()
                .name(getName())
                .heroClass(getHeroClass().name())
                .health(getHealth().getValue())
                .maxHealth(getHealth().getMaxValue())
                .shield(getShield().getValue())
                .maxShield(getShield().getMaxValue())
                .accuracy(getAccuracy())
                .agility(getAgility())
                .evasion(getEvasion())
                .damage(new DamageResponseDto(
                        getDamageDto().getAllPhysical(),
                        getDamageDto().getFire()))
                .build();
    }
}
