package hero;

import dto.damage.DamageResponseDto;
import dto.hero.HeroInfoDto;
import dto.hero.HeroStatisticDto;
import dto.damage.DamageDto;
import mechanic.Health;
import mechanic.Shield;
import spec.HeroClass;

public interface HeroInfo {

    String getName();

    HeroClass getHeroClass();

    Health getHealth();

    Double getReloader();

    Shield getShield();

    Double getAccuracy();

    Double getAgility();

    Double getEvasion();

    DamageDto getDamage();

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

    default HeroStatisticDto getStatistic() {
        return HeroStatisticDto
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
                .damage(DamageResponseDto
                        .builder()
                        .fullDamage(getDamage().getFullDamage())
                        .physicalDamage(getDamage().getPhysicalDamage())
                        .fireDamage(getDamage().getFireDamage())
                        .build())
                .build();
    }
}
