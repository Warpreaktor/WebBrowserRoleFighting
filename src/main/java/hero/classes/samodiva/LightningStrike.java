package hero.classes.samodiva;

import config.ApplicationProperties;
import dto.MinMax;
import dto.ability.AbilityDto;
import dto.attack.AttackDto;
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
    private static final String PICTURE_PATH = ApplicationProperties.getHost() + "/images/ability/hero/samodiva/lightning_strike.png";

    private static final String NAME = "Удар молнией";

    private static final AbilityType TYPE = ENEMY_TARGET;

    private static final String DESCRIPTION = "Самодива призывает к стихиям грома и бьёт врага электрическим разрядом. "
            + "С некоторой вероятностью, может ввести врага в состояние шока.";

    private static final int COST = 1;

    private static final int COOL_DOWN = 0;

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
    private int shockLength = 1;

    /**
     * Активна ли способность. Если способность не активно, то использовать её нельзя.
     */
    private boolean isActive = true;

    public LightningStrike() {
        super(PICTURE_PATH, NAME, TYPE, DESCRIPTION, COST, COOL_DOWN);

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

        if (tryTo(chanceToShock)) {
            var shock = target.getState().getShock();
            target.getState().switchOn(shock, shockLength);
        }

        var damageDto = new DamageDto();
        damageDto.setElectric(randomByMinMax(damage.getElectric()));
        var attack = new AttackDto(damageDto, "ЖАХХ! Ударило молнией!");

        target.defense(attack);

        return damageDto;
    }

    @Override
    public AbilityDto toDto() {
        return AbilityDto
                .builder()
                .picturePath(PICTURE_PATH)
                .name(NAME)
                .type(TYPE)
                .description(DESCRIPTION)
                .build();
    }

    /**
     * Возвращает текущее состояние способности. Активна ли она.
     */
    @Override
    public boolean isActive() {
        return isActive;
    }

    /**
     * Включает способность
     */
    @Override
    public void switchOn() {
        isActive = true;
    }

    /**
     * Выключает способность
     */
    @Override
    public void switchOff() {
        isActive = false;
    }
}
