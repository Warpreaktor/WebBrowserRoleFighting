package hero.classes;

import hero.Hero;
import item.weapon.ShortestBow;
import lombok.Getter;
import spec.HeroClass;

import static hero.constants.messages.ArcherMessages.ATTACK_MESSAGES;
import static hero.constants.messages.ArcherMessages.BLOCKED_MESSAGES;
import static hero.constants.messages.ArcherMessages.MISSED_MESSAGES;
import static hero.constants.messages.ArcherMessages.PAIN_MESSAGES;
import static hero.constants.messages.ArcherMessages.REST_MESSAGES;
import static hero.constants.messages.ArcherMessages.SHIELD_ABSORB_MESSAGES;
import static tools.Dice.randomInt;

/**
 * Класс, представляющий героя класса "Лучник".
 * Наследует общие характеристики и поведение от класса {@link Hero}.
 */
@Getter
public class Archer extends Hero {

    /**
     * Класс героя — лучник.
     */
    private final HeroClass heroClass = HeroClass.ARCHER;

    private static final int INTELLIGENCE = 4;
    private static final int STRENGTH = 4;
    private static final int DEXTERITY = 8;

    private static final double HEALTH = 50;
    private static final double MAGIC_SCREEN = 0;

    private static final double ACCURACY = 0.7;
    private static final double EVASION = 0.3;
    private static final double ENDURANCE = 3.8;
    private static final double ENDURANCE_GROWER = 0.65;
    private static final double BLOCK_CHANCE = 0.1;
    private static final double CRIT_CHANCE = 0.25;


    /**
     * Создаёт нового лучника с заданным именем и базовыми характеристиками.
     *
     * @param name Имя лучника
     */
    public Archer(String name) {
        super();

        setName(name);

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

        setCritChance(CRIT_CHANCE);
        setBlockChance(BLOCK_CHANCE);

        getInventory().put(new ShortestBow());

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
