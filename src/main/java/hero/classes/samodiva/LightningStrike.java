package hero.classes.samodiva;

import config.ApplicationProperties;
import dto.MinMax;
import dto.ability.AbilityDto;
import dto.damage.DamageDto;
import enums.AbilityType;
import lombok.Getter;
import mechanic.Ability;
import mechanic.Damage;
import mechanic.interfaces.Defensible;

import static enums.AbilityType.ENEMY_TARGET;
import static tools.Dice.randomByMinMax;
import static tools.Dice.tryTo;

/**
 * Удар молнией
 */
public class LightningStrike extends Ability {

    @Getter
//    private static final String picturePath = ApplicationProperties.getHost() + "/images/ability/hero/samodiva/lightning_strike.png";
    private static final String picturePath = ApplicationProperties.getHost() + "/images/ability/hero/samodiva/lightning_strike.png";

    private static final String name = "Удар молнией";

    private static final AbilityType type = ENEMY_TARGET;

    private static final String description = "Удар молнией";

    /**
     * Информация об уроне.
     */
    @Getter
    private Damage damage;

    /**
     * Шанс наложить шок
     */
    private double chanceToShock = 0.2;

    /**
     * Длительность шока
     */
    private int shockTemp = 1;

    public LightningStrike() {
        super(picturePath, name, type, description);

        this.damage = Damage
                .builder()
                .electric(new MinMax(1d, 3d))
                .build();
    }

    /**
     * Удар молнией.
     * Удар имеет шанс ввести в шок противника.
     */
    @Override
    public DamageDto apply(Defensible target) {

        if (tryTo(chanceToShock)){
            var shock = target.getState().getShock();
            target.getState().switchOn(shock, shockTemp);
        }

        var damageDto = new DamageDto();
        damageDto.setElectric(randomByMinMax(damage.getElectric()));
        return damageDto;
    }

    @Override
    public AbilityDto toDto() {
        return AbilityDto
                .builder()
                .picturePath(picturePath)
                .name(name)
                .type(type)
                .description(description)
                .build();
    }

}
