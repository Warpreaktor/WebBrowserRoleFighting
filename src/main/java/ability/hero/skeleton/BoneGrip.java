package ability.hero.skeleton;

import ability.Effectable;
import config.ApplicationProperties;
import dto.attack.AttackDto;
import enums.AbilityTarget;
import fight.FightService;
import hero.Hero;
import hero.classes.Skeleton;
import lombok.Getter;
import ability.Ability;
import mechanic.Damage;
import effect.Strangulation;

import static ability.AbilityCosts.BONE_GRIP_COOL_DOWN;
import static ability.AbilityCosts.BONE_GRIP_COST;
import static constants.AbilityGameWeight.BONE_GRIP_GW;
import static constants.AbilityGlobalDamage.BONE_GRIP;
import static tools.Dice.byMinMaxChance;
import static tools.Dice.tryTo;
import static tools.Dice.tryToHit;

/**
 * Костяная хватка.
 * Краткое описание: Скелет резко вытягивает руку и с силой сдавливает горло противника,
 * оставляя на ней след ледяного прикосновения смерти.
 *
 * Лор:
 * «Костлявые пальцы с неожиданной силой сжимают плоть, а холодная, мёртвая энергия
 * проникает в самую глубину костей жертвы.
 * Не успевает противник отдёрнуть руку, как уже чувствует на себе печать небытия.»
 */
public class BoneGrip extends Ability implements Effectable {

    private static final String PICTURE_PATH =
            ApplicationProperties.getHost() + "/images/ability/hero/skeleton/bone_grip.png";

    private static final String NAME = "Костяная хватка";

    private static final AbilityTarget TYPE = AbilityTarget.ENEMY;

    private static final String DESCRIPTION =
            "Скелет резко вытягивает руку и с силой сдавливает конечность противника, оставляя на ней след ледяного прикосновения смерти.";

    /**
     * Игровой вес умения. По сути это то, на сколько оценивается мощь и полезность этого умения в бою.
     */
    @Getter
    private final int gameWeight = BONE_GRIP_GW;

    /**
     * Ссылка на владельца способности.
     */
    @Getter
    private final Skeleton owner;

    @Getter
    private final Strangulation effect;

    //==================================================//
    // ███       💢 СПЕЦИАЛЬНЫЕ СВОЙСТВА 💢        ███ //
    //==================================================//

    /**
     * Информация об уроне.
     */
    @Getter
    private Damage damage;

    /**
     * Вероятность удушения противника.
     */
    @Getter
    private final double strangulationChance = 0.3;

    /**
     * Конструктор
     */
    public BoneGrip(Skeleton skeletonOwner) {
        super(PICTURE_PATH, NAME, DESCRIPTION, TYPE, BONE_GRIP_COST, BONE_GRIP_COOL_DOWN);

        this.owner = skeletonOwner;
        this.damage = BONE_GRIP;
        this.effect = new Strangulation();
    }

    @Override
    public void apply(Hero target) {
        var journal = FightService.getInstance().getResult();

        if (!tryToHit(owner, target)) {
            journal.addEventAndLog(owner.getName() + " схватил лишь воздух");
        }

        var damageDto = byMinMaxChance(damage);
        var attack = new AttackDto(damageDto,  owner.getName() + " хватает за горло!");
        var defenseDto = target.defense(attack);

        journal.addEventAndLog(attack.getMessage());
        journal.addEventAndLog(defenseDto.getMessage());

        if (tryTo(strangulationChance)) {
            effect.impose(target);
        }

        owner.exhaustion(getCost());

        coolDown();
    }

    @Override
    public void trigger() {
        //do nothing
    }
}
