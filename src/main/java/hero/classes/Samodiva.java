package hero.classes;

import ability.hero.samodiva.Discharge;
import hero.Hero;
import lombok.Getter;
import spec.HeroClass;

import java.util.List;

import static hero.constants.messages.SamodivaMessages.ATTACK_MESSAGES;
import static hero.constants.messages.SamodivaMessages.BLOCKED_MESSAGES;
import static hero.constants.messages.SamodivaMessages.MISSED_MESSAGES;
import static hero.constants.messages.SamodivaMessages.PAIN_MESSAGES;
import static hero.constants.messages.SamodivaMessages.REST_MESSAGES;
import static hero.constants.messages.SamodivaMessages.SHIELD_ABSORB_MESSAGES;
import static tools.Dice.randomInt;

/**
 * Самодивы – прекрасные духи воздуха, ветра и воды, обладающие магической силой.
 * Они часто изображались как волшебные женщины с крыльями или покрытые сияющей дымкой.
 * Вилы могли помогать или мстить людям, напоминая капризных западных фей.
 */
@Getter
public class Samodiva extends Hero {

    /**
     * Класс героя — Самодива.
     */
    private final HeroClass heroClass = HeroClass.SAMODIVA;

    private static final int INTELLIGENCE = 7;
    private static final int STRENGTH = 1;
    private static final int DEXTERITY = 5;

    //Стартовые характеристики
    private static final double HEALTH = 13;
    private static final double MAGIC_SCREEN = 5;
    private static final double ACCURACY = 0.4;
    private static final double EVASION = 0.6;
    private static final int ENDURANCE = 3;
    private static final int ENDURANCE_GROWER = 1;
    private static final double BLOCK_CHANCE = 0d;
    private static final double CRIT_CHANCE = 0.3;

    /**
     * Создаёт нового героя с заданным именем и базовыми характеристиками.
     *
     * @param name Имя героя
     */
    public Samodiva(String name) {
        super();

        setName(name);

        // Установка характеристик
        setIntelligence(INTELLIGENCE);
        setStrength(STRENGTH);
        setDexterity(DEXTERITY);

        setCritChance(CRIT_CHANCE);

        getHealth().addMaxValue(HEALTH);
        getMagicScreen().addMaxValue(MAGIC_SCREEN);

        setAccuracy(ACCURACY);
        setEvasion(EVASION);

        getEndurance().setMaxValue(ENDURANCE);
        getEndurance().setGrower(ENDURANCE_GROWER);

        setBlockChance(BLOCK_CHANCE);
        setCritChance(CRIT_CHANCE);

        addAbilities(List.of(new Discharge(this)));

        getHealth().fillUp();
        getMagicScreen().fillUp();
        getEndurance().fillUp();
    }

    public String getPainMessage() {
        int index = randomInt(PAIN_MESSAGES.size());
        return String.format(PAIN_MESSAGES.get(index), getName());
    }

    public String getMagicScreenAbsorbMessage() {
        int index = randomInt(SHIELD_ABSORB_MESSAGES.size());
        return String.format(SHIELD_ABSORB_MESSAGES.get(index), getName());
    }

    /**
     * Получает случайное сообщение при отражении атаки.
     *
     * @return Строка с текстом атаки, включающая имя персонажа.
     */
    public String getBlockedMessage() {
        int index = randomInt(BLOCKED_MESSAGES.size());
        return String.format(BLOCKED_MESSAGES.get(index), getName());
    }

    /**
     * Получает случайное сообщение при атаке.
     *
     * @return Строка с текстом атаки, включающая имя персонажа.
     */
    public String getAttackMessage() {
        int index = randomInt(ATTACK_MESSAGES.size());
        return String.format(ATTACK_MESSAGES.get(index), getName());
    }


    /**
     * Получает случайное сообщение при промахе.
     *
     * @return Строка с текстом промаха, включающая имя персонажа.
     */
    public String getMissedMessage() {
        int index = randomInt(MISSED_MESSAGES.size());
        return String.format(MISSED_MESSAGES.get(index), getName());
    }

    /**
     * Получает случайное сообщение при перезарядке.
     *
     * @return Строка с текстом перезарядки, включающая имя персонажа.
     */
    public String getRestMessage() {
        int index = randomInt(REST_MESSAGES.size());
        return String.format(REST_MESSAGES.get(index), getName());
    }
}
