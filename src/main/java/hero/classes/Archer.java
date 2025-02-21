package hero.classes;

import hero.Hero;
import fight.dto.AttackDto;
import fight.dto.DefenseDto;
import lombok.Getter;
import lombok.NonNull;
import spec.HeroClass;

import static hero.constants.messages.ArcherMessages.ARCHER_ATTACK_MESSAGES;
import static hero.constants.messages.ArcherMessages.ARCHER_MISSED_MESSAGES;
import static hero.constants.messages.ArcherMessages.ARCHER_RELOAD_MESSAGES;
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

    /**
     * Классовые характеристики
     */
    private static final int INTELLIGENCE = 1;
    private static final int STRENGTH = 3;
    private static final int DEXTERITY = 5;

    /**
     * Создаёт нового лучника с заданным именем и базовыми характеристиками.
     *
     * @param name Имя лучника
     */
    public Archer(String name) {
        super();

        setName(name);

        // Установка характеристик
        setIntelligence(INTELLIGENCE);
        setStrength(STRENGTH);
        setDexterity(DEXTERITY);

        getHealth().fillUp();
    }

    /**
     * Метод защиты при получении атаки.
     *
     * @param attack Объект атаки, содержащий информацию о наносимом уроне.
     * @return Объект {@link DefenseDto}, содержащий информацию о полученном уроне.
     */
    @Override
    public DefenseDto defense(@NonNull AttackDto attack) {
        double pain = getShield().takeDamage(attack.getDamageDto().getFullDamage());

        takeDamage(pain);
        return new DefenseDto(pain,
                this.getName() + " получает по заслугам");
    }

    /**
     * Получает случайное сообщение при атаке.
     *
     * @return Строка с текстом атаки, включающая имя персонажа.
     */
    public String getRandomAttackMessage() {
        int index = randomInt(ARCHER_ATTACK_MESSAGES.size());
        return String.format(ARCHER_ATTACK_MESSAGES.get(index), getName());
    }

    /**
     * Получает случайное сообщение при промахе.
     *
     * @return Строка с текстом промаха, включающая имя персонажа.
     */
    public String getRandomMissedMessage() {
        int index = randomInt(ARCHER_MISSED_MESSAGES.size());
        return String.format(ARCHER_MISSED_MESSAGES.get(index), getName());
    }

    /**
     * Получает случайное сообщение при перезарядке.
     *
     * @return Строка с текстом перезарядки, включающая имя персонажа.
     */
    public String getRandomReloadMessage() {
        int index = randomInt(ARCHER_RELOAD_MESSAGES.size());
        return String.format(ARCHER_RELOAD_MESSAGES.get(index), getName());
    }
}
