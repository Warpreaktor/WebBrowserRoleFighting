package ability.hero.skeleton;

import ability.BuffAbility;
import config.ApplicationProperties;
import effect.DamageBoost;
import enums.AbilityTarget;
import fight.FightService;
import hero.Hero;
import hero.classes.Skeleton;
import lombok.Getter;

import static ability.AbilityCosts.NECROTIC_VICTIM_COOL_DOWN;
import static ability.AbilityCosts.NECROTIC_VICTIM_COST;
import static constants.AbilityGameWeight.NECROTIC_VICTIM_GW;

/**
 * Некротическая жертва.
 * Краткое описание: Скелет жертвует частью своей жизненной силы,
 * обретая кратковременную мощь или восстанавливая здоровье.
 *
 * Лор:
 * «Рёбра скелета начинают светиться тусклым зелёным сиянием, когда он взывает к таинственным силам смерти.
 * Часть его тёмной энергии вырывается наружу, завывая в небытие, и скелет словно наполняется древней силой забвения.
 * Но любая связь с мёртвыми мирами не проходит бесследно...»
 */
public class NecroticVictim extends BuffAbility {

    /**
     * Путь к изображению способности.
     */
    private static final String PICTURE_PATH =
            ApplicationProperties.getHost() + "/images/ability/hero/skeleton/necrotic_victim.png";

    /**
     * Название способности.
     */
    private static final String NAME = "Некротическая жертва";

    /**
     * Тип способности.
     */
    private static final AbilityTarget TYPE = AbilityTarget.SELF;

    /**
     * Описание способности.
     */
    private static final String DESCRIPTION =
            "Скелет жертвует частью своей энергии, обретая кратковременную мощь или восстанавливая здоровье.";

    /**
     * Игровой вес умения. По сути это то, на сколько оценивается мощь и полезность этого умения в бою.
     */
    @Getter
    private final int gameWeight = NECROTIC_VICTIM_GW;

    //==================================================//
    // ███       💢 СПЕЦИАЛЬНЫЕ СВОЙСТВА 💢        ███ //
    //==================================================//

    /**
     * Класс накладываемого способностью эффекта.
     */
    @Getter
    private final DamageBoost effect;

    /**
     * Процент текущего здоровья, который придётся отдать скелету.
     */
    @Getter
    private final double healthSacrificePercent = 0.1;

    public NecroticVictim(Skeleton owner) {
        super(owner,PICTURE_PATH, NAME, DESCRIPTION, TYPE, NECROTIC_VICTIM_COST, NECROTIC_VICTIM_COOL_DOWN);
        this.effect = new DamageBoost();
    }

    @Override
    public void apply(Hero target) {

        var journal = FightService.getInstance().getResult();
        journal.addEventAndLog(getOwner().getName() + " призывает мрачные силы загробного мира...");

        //Скелет теряет часть текущего здоровья
        double currentHP = getOwner().getHealth().getValue();
        double sacrifice = currentHP * healthSacrificePercent;
        getOwner().getHealth().decreaseValue(sacrifice);

        journal.addEventAndLog(getOwner().getName() + " жертвует " + (int)sacrifice + " единиц своего здоровья!");

        getEffect().impose(getOwner());

        journal.addEventAndLog(getOwner().getName() + " впитывает силу смерти и получает усиление урона!");

        coolDown();
    }

    @Override
    public void trigger() {
        // do nothing
    }

}

