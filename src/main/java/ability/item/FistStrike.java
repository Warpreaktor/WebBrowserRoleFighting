package ability.item;

import config.ApplicationProperties;
import core.GameMaster;
import dto.attack.AttackDto;
import enums.AbilityType;
import fight.FightService;
import hero.Hero;
import item.weapon.Fist;
import lombok.Getter;
import ability.Ability;

import static constants.AbilityGameWeight.FIST_STRIKE_GW;
import static enums.AbilityType.ENEMY_TARGET;
import static tools.Dice.byMinMaxChance;

/**
 * Удар кулаком
 */
public class FistStrike extends Ability {

    private static final String PICTURE_PATH = ApplicationProperties.getHost() + "/images/ability/item/fist_strike.png";

    private static final String NAME = "Удар кулаком";

    private static final AbilityType TYPE = ENEMY_TARGET;

    private static final String DESCRIPTION = "Бьёт кулаком как дробящим уроном!";

    private static final int COST = 1;

    private static final int COOL_DOWN = 0;

    /**
     * Игровой вес умения. По сути это то, на сколько оценивается мощь и полезность этого умения в бою.
     */
    @Getter
    private final int gameWeight = FIST_STRIKE_GW;

    //==================================================//
    // ███       💢 СПЕЦИАЛЬНЫЕ СВОЙСТВА 💢        ███ //
    //==================================================//
    /**
     * Информация о предмете которому принадлежит способность.
     */
    @Getter
    private final Fist owner;

    public FistStrike(Fist abilityOwner) {
        super(PICTURE_PATH, NAME, TYPE, DESCRIPTION, COST, COOL_DOWN);

        this.owner = abilityOwner;
    }

    @Override
    public void apply(Hero target) {
        var journal = FightService.getInstance().getResult();

        var damageDto = byMinMaxChance(owner.getDamage());

        var attack = new AttackDto(damageDto, "ДЫЩ кулаком!");

        journal.addEventAndLog(attack.getMessage());

        var defense = target.defense(attack);

        journal.addEventAndLog(defense.getMessage());

        owner.exhaustion(getCost());

        coolDown();

    }

    @Override
    public void trigger() {
        // do nothing
    }
}
