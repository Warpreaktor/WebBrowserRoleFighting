package hero.classes;

import hero.Hero;
import item.shield.WoodenShield;
import item.weapon.GrandpasRustySword;
import item.weapon.Knife;
import lombok.Getter;
import spec.HeroClass;

import java.util.List;

import static hero.constants.messages.BarbarianMessages.ATTACK_MESSAGES;
import static hero.constants.messages.BarbarianMessages.BLOCKED_MESSAGES;
import static hero.constants.messages.BarbarianMessages.MISSED_MESSAGES;
import static hero.constants.messages.BarbarianMessages.PAIN_MESSAGES;
import static hero.constants.messages.BarbarianMessages.REST_MESSAGES;
import static hero.constants.messages.BarbarianMessages.SHIELD_ABSORB_MESSAGES;
import static tools.Dice.randomInt;

/**
 * Класс, представляющий героя класса "Воин".
 * Наследует общие характеристики и поведение от класса {@link Hero}.
 */
@Getter
public class Barbarian extends Hero {

    /**
     * Класс героя — воин.
     */
    private final HeroClass heroClass = HeroClass.BARBARIAN;

    //===============================//
    //    Стартовые характеристики   //
    //===============================//
    private static final int INTELLIGENCE = 2;
    private static final int STRENGTH = 9;
    private static final int DEXTERITY = 5;

    private static final double HEALTH = 70;
    private static final double SHIELD = 0;
    
    private static final double ACCURACY = 0.65;
    private static final double EVASION = 0.1;
    private static final double AGILITY = 0.4;
    private static final double ENDURANCE = 3.5;
    private static final double BLOCK_CHANCE = 0.5;
    private static final double CRIT_CHANCE = 0.15;

    /**
     * Создаёт нового воина с заданным именем и базовыми характеристиками.
     *
     * @param name Имя воина
     */
    public Barbarian(String name) {
        super();

        setName(name);

        setIntelligence(INTELLIGENCE);
        setStrength(STRENGTH);
        setDexterity(DEXTERITY);

        getHealth().addMaxValue(HEALTH);
        getShield().addMaxValue(SHIELD);

        setAccuracy(ACCURACY);
        setEvasion(EVASION);
        setEndurance(ENDURANCE);
        setAgility(AGILITY);

        setCritChance(CRIT_CHANCE);
        setBlockChance(BLOCK_CHANCE);

        getInventory().put(new GrandpasRustySword());
        getInventory().put(new Knife());
        getInventory().put(new WoodenShield());

        getHealth().fillUp();
        getShield().fillUp();
    }

    @Override
    public String getShieldAbsorbMessage() {
        int index = randomInt(SHIELD_ABSORB_MESSAGES.size());
        return String.format(SHIELD_ABSORB_MESSAGES.get(index), getName());
    }

    @Override
    public String getPainMessage() {
        int index = randomInt(PAIN_MESSAGES.size());
        return String.format(PAIN_MESSAGES.get(index), getName());
    }

    @Override
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
    @Override
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
