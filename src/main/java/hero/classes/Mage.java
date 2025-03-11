package hero.classes;

import hero.Hero;
import item.weapon.MagicWandOfNewbie;
import lombok.Getter;
import spec.HeroClass;

import static hero.constants.messages.MageMessages.ATTACK_MESSAGES;
import static hero.constants.messages.MageMessages.BLOCKED_MESSAGES;
import static hero.constants.messages.MageMessages.DEFEND_MESSAGES;
import static hero.constants.messages.MageMessages.MISSED_MESSAGES;
import static hero.constants.messages.MageMessages.PAIN_MESSAGES;
import static hero.constants.messages.MageMessages.REST_MESSAGES;
import static tools.Dice.randomInt;

/**
 * У мага повышенный рост магического щита.
 * <p>
 * Класс, представляющий героя класса "Маг".
 * Наследует общие характеристики и поведение от класса {@link Hero}.
 *
 */
@Getter
public class Mage extends Hero {

    /**
     * Класс героя — маг.
     */
    private final HeroClass heroClass = HeroClass.MAGE;

    private static final int INTELLIGENCE = 9;
    private static final int STRENGTH = 2;
    private static final int DEXTERITY = 4;

    private static final double HEALTH = 35;
    private static final double MAGIC_SCREEN = 20;

    private static final double ACCURACY = 0.8;
    private static final double EVASION = 0.15;
    private static final double ENDURANCE = 2.5;
    private static final double ENDURANCE_GROWER = 0.5;
    private static final double BLOCK_CHANCE = 0d;
    private static final double CRIT_CHANCE = 0.3;

    /**
     * Создаёт нового мага с заданным именем и базовыми характеристиками.
     *
     * @param name Имя мага
     */
    public Mage(String name) {
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

        getMagicScreen().addMagicScreenGrower(1d);

        getInventory().put(new MagicWandOfNewbie());

        getMagicScreen().fillUp();
        getHealth().fillUp();
        getEndurance().fillUp();
    }

    @Override
    public String getBlockedMessage() {
        int index = randomInt(BLOCKED_MESSAGES.size());
        return String.format(BLOCKED_MESSAGES.get(index), getName());
    }
    @Override
    public String getMagicScreenAbsorbMessage() {
        int index = randomInt(DEFEND_MESSAGES.size());
        return String.format(DEFEND_MESSAGES.get(index), getName());
    }

    @Override
    public String getPainMessage() {
        int index = randomInt(PAIN_MESSAGES.size());
        return String.format(PAIN_MESSAGES.get(index), getName());
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
    @Override
    public String getRestMessage() {
        int index = randomInt(REST_MESSAGES.size());
        return String.format(REST_MESSAGES.get(index), getName());
    }
}
