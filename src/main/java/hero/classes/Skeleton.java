package hero.classes;

import fight.dto.AttackDto;
import fight.dto.DefenseDto;
import hero.Hero;
import lombok.Getter;
import lombok.NonNull;
import spec.HeroClass;

import static hero.constants.messages.SkeletonMessages.SKELETON_ATTACK_MESSAGES;
import static hero.constants.messages.SkeletonMessages.SKELETON_MISSED_MESSAGES;
import static hero.constants.messages.SkeletonMessages.SKELETON_RELOAD_MESSAGES;
import static tools.Dice.randomInt;

/**
 * Создаёт нового скелета с невыразимой тоской в пустых глазницах.
 * Возможно, он просто хотел поспать, но вместо этого оказался в эпическом замесе.
 * <p>
 * Класс, представляющий героя класса "Скелет".
 * Наследует общие характеристики и поведение от класса {@link Hero}.
 */
@Getter
public class Skeleton extends Hero {

    /**
     * Класс героя — скелет.
     */
    private final HeroClass heroClass = HeroClass.SKELETON;

    /**
     * Классовые характеристики
     */
    private static final Integer INTELLIGENCE = 1;
    private static final Integer STRENGTH = 3;
    private static final Integer DEXTERITY = 4;

    /**
     * Создаёт нового скелета с заданным именем и базовыми характеристиками.
     *
     * @param name Имя скелета
     */
    public Skeleton(String name) {
        super();

        setName(name);

        // Установка характеристик
        setIntelligence(INTELLIGENCE);
        setStrength(STRENGTH);
        setDexterity(DEXTERITY);

        // Скелет получает бонус к уклонению
        addEvasion(0.15);

        // Повышенное здоровье за счет костяной структуры
        getHealth().addMaxValue(20.0);
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
        // Скелет игнорирует 10% урона благодаря отсутствию плоти
        double reduceDamage = attack.getDamageDto().getFullDamage() * 0.9;
        double pain = getShield().takeDamage(reduceDamage);

        if (pain > 0) {
            takeDamage(pain);
            return new DefenseDto(pain,
                    String.format("%s кости трещат, но держатся!", getName()));
        }

        return new DefenseDto(0.0,
                String.format("%s холодно сверкнул глазницами - урон поглощен!", getName()));
    }

    /**
     * Получает случайное сообщение при атаке.
     *
     * @return Строка с текстом атаки, включающая имя персонажа.
     */
    @Override
    public String getRandomAttackMessage() {
        int index = randomInt(SKELETON_ATTACK_MESSAGES.size());
        return String.format(SKELETON_ATTACK_MESSAGES.get(index), getName());
    }

    /**
     * Получает случайное сообщение при промахе.
     *
     * @return Строка с текстом промаха, включающая имя персонажа.
     */
    @Override
    public String getRandomMissedMessage() {
        int index = randomInt(SKELETON_MISSED_MESSAGES.size());
        return String.format(SKELETON_MISSED_MESSAGES.get(index), getName());
    }

    /**
     * Получает случайное сообщение при перезарядке.
     *
     * @return Строка с текстом перезарядки, включающая имя персонажа.
     */
    @Override
    public String getRandomReloadMessage() {
        int index = randomInt(SKELETON_RELOAD_MESSAGES.size());
        return String.format(SKELETON_RELOAD_MESSAGES.get(index), getName());
    }
}
