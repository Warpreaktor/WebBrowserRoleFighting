package hero.classes;

import hero.Hero;
import fight.dto.AttackDto;
import fight.dto.DefenseDto;
import lombok.Getter;
import lombok.NonNull;
import spec.HeroClass;

import static hero.constants.messages.MageMessages.MAGE_ATTACK_MESSAGES;
import static hero.constants.messages.MageMessages.MAGE_MISSED_MESSAGES;
import static hero.constants.messages.MageMessages.MAGE_RELOAD_MESSAGES;
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

    /**
     * Классовые характеристики
     */
    private static final Integer INTELLIGENCE = 4;
    private static final Integer STRENGTH = 2;
    private static final Integer DEXTERITY = 3;

    /**
     * Создаёт нового мага с заданным именем и базовыми характеристиками.
     *
     * @param name Имя мага
     */
    public Mage(String name) {
        super();

        setName(name);

        // Установка характеристик
        setIntelligence(INTELLIGENCE);
        setStrength(STRENGTH);
        setDexterity(DEXTERITY);

        //Маг стартует с полным щитом
        getShield().fillUp();
        //Маг получает бонус к скорости роста щита
        getShield().addShieldGrower(0.2);

        //Скорость атаки мага зависит от его интеллекта
        setAgility(INTELLIGENCE * 0.1);

        getHealth().fillUp();
    }

    /**
     * Выполняет перезарядку.
     *
     * @return Объект {@link AttackDto}, содержащий информацию о перезарядке.
     */
    @Override
    public AttackDto doReloadEvent() {
        return new AttackDto(this, getName() + " мутит магические пасы");
    }

    /**
     * Метод защиты при получении атаки.
     *
     * @param attack Объект атаки, содержащий информацию о наносимом уроне.
     * @return Объект {@link DefenseDto}, содержащий информацию о полученном уроне.
     */
    @Override
    public DefenseDto defense(@NonNull AttackDto attack) {
        double pain = attack.getDamageDto().getFullDamage();

        pain = getShield().takeDamage(pain);

        if (pain > 0) {
            takeDamage(pain);
            return new DefenseDto(attack.getDamageDto().getFullDamage(),
                    this.getName() + " терпит унизительную боль");
        } else {
            return new DefenseDto(pain,
                    " урон поглощён колдунством");
        }
    }

    /**
     * Получает случайное сообщение при атаке.
     *
     * @return Строка с текстом атаки, включающая имя персонажа.
     */
    public String getRandomAttackMessage() {
        int index = randomInt(MAGE_ATTACK_MESSAGES.size());
        return String.format(MAGE_ATTACK_MESSAGES.get(index), getName());
    }

    /**
     * Получает случайное сообщение при промахе.
     *
     * @return Строка с текстом промаха, включающая имя персонажа.
     */
    public String getRandomMissedMessage() {
        int index = randomInt(MAGE_MISSED_MESSAGES.size());
        return String.format(MAGE_MISSED_MESSAGES.get(index), getName());
    }

    /**
     * Получает случайное сообщение при перезарядке.
     *
     * @return Строка с текстом перезарядки, включающая имя персонажа.
     */
    @Override
    public String getRandomReloadMessage() {
        int index = randomInt(MAGE_RELOAD_MESSAGES.size());
        return String.format(MAGE_RELOAD_MESSAGES.get(index), getName());
    }
}
