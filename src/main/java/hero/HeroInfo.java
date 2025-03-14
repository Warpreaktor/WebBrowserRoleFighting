package hero;

import dto.damage.DamageDto;
import dto.damage.DamageResponseDto;
import dto.hero.HeroInfoDto;
import dto.hero.HeroCharacteristicDto;
import mechanic.Endurance;
import mechanic.Health;
import mechanic.MagicScreen;
import spec.HeroClass;

public interface HeroInfo {

    String getName();

    HeroClass getHeroClass();

    Health getHealth();

    Endurance getEndurance();

    MagicScreen getMagicScreen();

    Double getAccuracy();

    Double getEvasion();

    DamageDto getDamageInfo();

    Statistic getStatistic();

    default HeroInfoDto getInfo() {
        return HeroInfoDto
                .builder()
                .name(getName())
                .statistic(getStatistic())
                .heroClass(getHeroClass().name())
                .healthValue(getHealth().getValue())
                .healthMaxValue(getHealth().getMaxValue())
                .magicScreenValue(getMagicScreen().getValue())
                .magicScreenMaxValue(getMagicScreen().getMaxValue())
                .enduranceMaxValue(getEndurance().getMaxValue())
                .enduranceValue(getEndurance().getValue())
                .build();
    }

    default HeroCharacteristicDto getCharacteristic() {
        return HeroCharacteristicDto
                .builder()
                .name(getName())
                .heroClass(getHeroClass().name())
                .healthValue(getHealth().getValue())
                .healthMaxValue(getHealth().getMaxValue())
                .magicScreenValue(getMagicScreen().getValue())
                .magicScreenMaxValue(getMagicScreen().getMaxValue())
                .enduranceMaxValue(getEndurance().getMaxValue())
                .enduranceValue(getEndurance().getValue())
                .accuracy(getAccuracy())
                .evasion(getEvasion())
                .damage(new DamageResponseDto(
                        getDamageInfo().getAllPhysical(),
                        getDamageInfo().getFire()))
                .build();
    }
}
