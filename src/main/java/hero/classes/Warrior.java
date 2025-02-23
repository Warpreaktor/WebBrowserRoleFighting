package hero.classes;

import hero.Hero;
import fight.dto.AttackDto;
import fight.dto.DefenseDto;
import lombok.Getter;
import lombok.NonNull;
import spec.HeroClass;

import static hero.constants.messages.WarriorMessages.WARRIOR_ATTACK_MESSAGES;
import static hero.constants.messages.WarriorMessages.WARRIOR_MISSED_MESSAGES;
import static hero.constants.messages.WarriorMessages.WARRIOR_RELOAD_MESSAGES;
import static tools.Dice.getChance;
import static tools.Dice.randomInt;

/**
 * Класс, представляющий героя класса "Воин".
 * Наследует общие характеристики и поведение от класса {@link Hero}.
 */
@Getter
public class Warrior extends Hero {

    /**
     * Класс героя — воин.
     */
    private final HeroClass heroClass = HeroClass.WARRIOR;

    /**
     * Классовые характеристики
     */
    private static final Integer INTELLIGENCE = 1;
    private static final Integer STRENGTH = 4;
    private static final Integer DEXTERITY = 4;

    /**
     * Шанс заблокировать весь урон.
     */
    public static final double BLOCK_CHANCE = 0.4;

    /**
     * Создаёт нового воина с заданным именем и базовыми характеристиками.
     *
     * @param name Имя воина
     */
    public Warrior(String name) {
        super();

        setName(name);

        // Установка характеристик
        setIntelligence(INTELLIGENCE);
        setStrength(STRENGTH);
        setDexterity(DEXTERITY);

        //Воин уже в начале боя заряжен на удар
        setReloader(1D);
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
        double tryToDefense = getChance();
        double pain = getShield().takeDamage(attack.getDamageDto().getFullDamage());

        if (BLOCK_CHANCE >= tryToDefense) {
            pain = 0;
            return new DefenseDto(pain,
                    "по хуй дым! " + this.getName() + " защитился!");
        }

        takeDamage(pain);
        return new DefenseDto(pain, this.getName() + " пропизжен");
    }

    /**
     * Получает случайное сообщение при атаке.
     *
     * @return Строка с текстом атаки, включающая имя персонажа.
     */
    public String getRandomAttackMessage() {
        int index = randomInt(WARRIOR_ATTACK_MESSAGES.size());
        return String.format(WARRIOR_ATTACK_MESSAGES.get(index), getName());
    }

    /**
     * Получает случайное сообщение при промахе.
     *
     * @return Строка с текстом промаха, включающая имя персонажа.
     */
    @Override
    public String getRandomMissedMessage() {
        int index = randomInt(WARRIOR_MISSED_MESSAGES.size());
        return String.format(WARRIOR_MISSED_MESSAGES.get(index), getName());
    }

    /**
     * Получает случайное сообщение при перезарядке.
     *
     * @return Строка с текстом перезарядки, включающая имя персонажа.
     */
    @Override
    public String getRandomReloadMessage() {
        int index = randomInt(WARRIOR_RELOAD_MESSAGES.size());
        return String.format(WARRIOR_RELOAD_MESSAGES.get(index), getName());
    }
}
