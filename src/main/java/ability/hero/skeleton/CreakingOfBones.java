package ability.hero.skeleton;

import config.ApplicationProperties;
import dto.MinMax;
import enums.AbilityTarget;
import fight.FightService;
import hero.Hero;
import hero.classes.Skeleton;
import lombok.Getter;
import ability.Ability;

import static ability.AbilityCosts.CREAKING_OF_BONES_COOL_DOWN;
import static ability.AbilityCosts.CREAKING_OF_BONES_COST;
import static constants.AbilityGameWeight.CREAKING_OF_BONES_GW;
import static tools.Dice.byMinMaxChance;
import static tools.Dice.tryTo;

/**
 * Скрежет костей.
 * Краткое описание: Скелет издаёт душераздирающий скрежет,
 * понижая боевой дух.
 *
 * Лор:
 * «Звон бьющихся костей оглушает противников и вызывает неприятный зуд в висках.
 * Кажется, будто со дна могил поднимается хор древних воинов,
 * заглушая мысль о победе и вселяя смутную тревогу.»
 */
public class CreakingOfBones extends Ability {

    /**
     * Путь к изображению способности.
     */
    private static final String PICTURE_PATH =
            ApplicationProperties.getHost() + "/images/ability/hero/skeleton/creaking_of_bones.png";

    /**
     * Название способности.
     */
    private static final String NAME = "Скрежет костей";

    /**
     * Тип способности.
     */
    private static final AbilityTarget TYPE = AbilityTarget.ENEMY;

    /**
     * Описание способности.
     */
    private static final String DESCRIPTION =
            "Скелет издаёт душераздирающий скрежет, понижая боевой дух и меткость всех врагов.";

    /**
     * Игровой вес умения. По сути это то, на сколько оценивается мощь и полезность этого умения в бою.
     */
    @Getter
    private final int gameWeight = CREAKING_OF_BONES_GW;

    /**
     * Ссылка на владельца способности.
     */
    @Getter
    private final Skeleton owner;

    //==================================================//
    // ███       💢 СПЕЦИАЛЬНЫЕ СВОЙСТВА 💢        ███ //
    //==================================================//

    /**
     * Вероятность успешного наложения страха (или снижения меткости) на каждую цель.
     */
    @Getter
    private final double fearChance = 0.8;

    /**
     * Сила ужаса.
     */
    @Getter
    private final MinMax fearPower = new MinMax(2, 4);


    public CreakingOfBones(Skeleton owner) {
        super(PICTURE_PATH, NAME, DESCRIPTION, TYPE,  CREAKING_OF_BONES_COST, CREAKING_OF_BONES_COOL_DOWN);
        this.owner = owner;
    }

    @Override
    public void apply(Hero target) {
        var journal = FightService.getInstance().getResult();

        boolean success = tryTo(fearChance);
        if (!success) {
            journal.addEventAndLog(owner.getName() + " попытался напугать " +
                    target.getName() + ", но безуспешно!");
            return;
        }

        journal.addEventAndLog(owner.getName() + " издаёт леденящий скрежет! " +
                "У " + target.getName() + " дрогнули колени...");

        target.getPsych().decreaseValue(byMinMaxChance(fearPower));

        coolDown();

    }

    @Override
    public void trigger() {
        // do nothing
    }
}

