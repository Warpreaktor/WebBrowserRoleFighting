package ability.item;

import ability.WeaponAbility;
import config.ApplicationProperties;
import dto.attack.AttackDto;
import enums.AbilityTarget;
import fight.FightService;
import hero.Hero;
import item.weapon.Knife;
import lombok.Getter;

import static ability.AbilityCosts.KNIFE_STRIKE_COOL_DOWN;
import static ability.AbilityCosts.KNIFE_STRIKE_COST;
import static constants.AbilityGameWeight.KNIFE_STRIKE_GW;
import static enums.AbilityTarget.ENEMY;
import static tools.Dice.byMinMaxChance;

/**
 * Удар ножом
 */
public class KnifeStrike extends WeaponAbility {

    private static final String PICTURE_PATH = ApplicationProperties.getHost() + "/images/ability/item/knife_strike.png";

    private static final String NAME = "Удар ножом";

    private static final AbilityTarget TYPE = ENEMY;

    private static final String DESCRIPTION = "Наносит противнику колющий урон";

    /**
     * Игровой вес умения. По сути это то, на сколько оценивается мощь и полезность этого умения в бою.
     */
    @Getter
    private final int gameWeight = KNIFE_STRIKE_GW;

    //==================================================//
    //   ▄█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▄    //
    //  ▄█                                          █▄  //
    // ███       💢 СПЕЦИАЛЬНЫЕ СВОЙСТВА 💢        ███ //
    //  ▀█                                          █▀  //
    //   ▀█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▀    //
    //==================================================//

    public KnifeStrike(Knife abilityOwner) {
        super(PICTURE_PATH, NAME, DESCRIPTION, TYPE, KNIFE_STRIKE_COST, KNIFE_STRIKE_COOL_DOWN, abilityOwner);

    }

    @Override
    public void apply(Hero target) {
        var journal = FightService.getInstance().getResult();

        var damageDto = byMinMaxChance(getOwner().getDamage());

        var attack = new AttackDto(damageDto, "ПЫРЬ ножом!!");

        journal.addEventAndLog(attack.getMessage());

        var defense = target.defense(attack);

        journal.addEventAndLog(defense.getMessage());

        coolDown();

    }

    @Override
    public void trigger() {
        // do nothing
    }

}
