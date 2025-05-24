package ability.hero.samodiva;

import config.ApplicationProperties;
import dto.attack.AttackDto;
import dto.damage.DamageDto;
import enums.AbilityTarget;
import hero.Hero;
import hero.classes.Samodiva;
import lombok.Getter;
import ability.Ability;
import mechanic.Damage;
import effect.Shock;

import static ability.AbilityCosts.DISCHARGE_COOL_DOWN;
import static ability.AbilityCosts.DISCHARGE_COST;
import static constants.AbilityGameWeight.DISCHARGE_GW;
import static constants.AbilityGlobalDamage.DISCHARGE;
import static enums.AbilityTarget.ENEMY;
import static tools.Dice.byMinMaxChance;
import static tools.Dice.tryTo;

/**
 * Разряд
 */
public class Discharge extends Ability {

    private static final String PICTURE_PATH = ApplicationProperties.getHost() + "/images/ability/hero/samodiva/discharge.png";

    private static final String NAME = "Разряд";

    private static final String DESCRIPTION = "Самодива призывает к стихиям грома и бьёт врага электрическим разрядом. "
            + "С некоторой вероятностью, может ввести врага в состояние шока.";

    private static final AbilityTarget TYPE = ENEMY;

    /**
     * Игровой вес умения. По сути это то, на сколько оценивается мощь и полезность этого умения в бою.
     */
    @Getter
    private final int gameWeight = DISCHARGE_GW;

    /**
     * Ссылка на владельца способности.
     */
    @Getter
    private final Samodiva owner;

    //==================================================//
    // ███       💢 СПЕЦИАЛЬНЫЕ СВОЙСТВА 💢        ███ //
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
    private int shockDuration = 1;


    public Discharge(Samodiva owner) {
        super(PICTURE_PATH, NAME, DESCRIPTION, TYPE,  DISCHARGE_COST, DISCHARGE_COOL_DOWN);

        this.owner = owner;
        this.damage = DISCHARGE;
    }

    @Override
    public void apply(Hero target) {

        if (tryTo(chanceToShock)) {
            target.getEffectStack().impose(new Shock(), shockDuration);
        }

        var damageDto = new DamageDto();
        damageDto.setElectric(byMinMaxChance(damage.getElectric()));
        var attack = new AttackDto(damageDto, "ЖАХХ! Ударило молнией!");

        target.defense(attack);

        owner.exhaustion(getCost());
        coolDown();
    }

    @Override
    public void trigger() {
        //do nothing
    }
}
