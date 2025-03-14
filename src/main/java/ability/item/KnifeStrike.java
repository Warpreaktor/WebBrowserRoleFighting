package ability.item;

import ability.WeaponAbility;
import config.ApplicationProperties;
import dto.attack.AttackDto;
import enums.AbilityType;
import fight.FightService;
import hero.Hero;
import item.weapon.Knife;
import lombok.Getter;

import static constants.AbilityGameWeight.KNIFE_STRIKE_GW;
import static enums.AbilityType.ENEMY_TARGET;
import static tools.Dice.byMinMaxChance;

/**
 * Удар ножом
 */
public class KnifeStrike extends WeaponAbility {

    private static final String PICTURE_PATH = ApplicationProperties.getHost() + "/images/ability/item/knife_strike.png";

    private static final String NAME = "Удар ножом";

    private static final AbilityType TYPE = ENEMY_TARGET;

    private static final String DESCRIPTION = "Наносит противнику колющий урон";

    private static final int COST = 1;

    private static final int COOL_DOWN = 0;

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
        super(PICTURE_PATH, NAME, TYPE, DESCRIPTION, COST, COOL_DOWN, abilityOwner);

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
