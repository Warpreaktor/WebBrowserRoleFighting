package ability.hero.samodiva;

import config.ApplicationProperties;
import dto.attack.AttackDto;
import dto.damage.DamageDto;
import enums.AbilityType;
import lombok.Getter;
import mechanic.Ability;
import mechanic.Damage;
import mechanic.interfaces.Defensible;

import static constants.GlobalDamage.DISCHARGE;
import static enums.AbilityType.ENEMY_TARGET;
import static tools.Dice.byMinMaxChance;
import static tools.Dice.tryTo;

/**
 * Разряд
 */
public class Discharge extends Ability {

    @Getter
    private static final String PICTURE_PATH = ApplicationProperties.getHost() + "/images/ability/hero/samodiva/lightning_strike.png";

    private static final String NAME = "Разряд";

    private static final AbilityType TYPE = ENEMY_TARGET;

    private static final String DESCRIPTION = "Самодива призывает к стихиям грома и бьёт врага электрическим разрядом. "
            + "С некоторой вероятностью, может ввести врага в состояние шока.";

    private static final int COST = 1;

    private static final int COOL_DOWN = 0;

    //==================================================//
    //   ▄█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▄    //
    //  ▄█                                          █▄  //
    // ███       💢 СПЕЦИАЛЬНЫЕ СВОЙСТВА 💢        ███ //
    //  ▀█                                          █▀  //
    //   ▀█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▀    //
    //==================================================//
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


    public Discharge() {
        super(PICTURE_PATH, NAME, TYPE, DESCRIPTION, COST, COOL_DOWN);

        this.damage = DISCHARGE;
    }

    @Override
    public void apply(Defensible target) {

        if (tryTo(chanceToShock)) {
            var shock = target.getState().getShock();
            target.getState().switchOn(shock, shockLength);
        }

        var damageDto = new DamageDto();
        damageDto.setElectric(byMinMaxChance(damage.getElectric()));
        var attack = new AttackDto(damageDto, "ЖАХХ! Ударило молнией!");

        target.defense(attack);

    }
}
