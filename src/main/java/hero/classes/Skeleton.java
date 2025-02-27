package hero.classes;

import dto.attack.AttackDto;
import dto.defense.DefenseDto;
import hero.Hero;
import item.shield.WoodenShield;
import item.weapon.Knife;
import lombok.Getter;
import lombok.NonNull;
import spec.HeroClass;

import static hero.constants.messages.SkeletonMessages.SKELETON_ATTACK_MESSAGES;
import static hero.constants.messages.SkeletonMessages.SKELETON_BLOCKED_MESSAGES;
import static hero.constants.messages.SkeletonMessages.SKELETON_MISSED_MESSAGES;
import static hero.constants.messages.SkeletonMessages.SKELETON_PAIN_MESSAGES;
import static hero.constants.messages.SkeletonMessages.SKELETON_RELOAD_MESSAGES;
import static hero.constants.messages.SkeletonMessages.SKELETON_SHIELD_ABSORB_MESSAGES;
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
    private static final Integer STRENGTH = 2;
    private static final Integer DEXTERITY = 4;

    //Скелет слеповат и глуповат, потому часто бьёт просто наотмаш. Снижен шанс крита.
    private static final Double CRIT_CHANCE = -0.1;

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

        setCritChance(CRIT_CHANCE);

        // Скелет получает бонус к уклонению
        //TODO предлагаю в будущем переделать это на бонус к уклонению и уменьшению урона от колющих атак
        addEvasion(0.15);

        getHealth().setMaxValue(4.0);

        getInventory().put(new Knife());
        getInventory().put(new WoodenShield());

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
        double reduceDamage = attack.getDamageDto().getSumDamage() * 0.9;
        double pain = getShield().takeDamage(reduceDamage);

        if (pain > 0) {
            takeDamage(pain);
            return new DefenseDto(pain,
                    getPainMessage());
        }

        return new DefenseDto(0.0,
                getShieldAbsorbMessage());
    }

    public String getBlockedMessage() {
        int index = randomInt(SKELETON_BLOCKED_MESSAGES.size());
        return String.format(SKELETON_BLOCKED_MESSAGES.get(index), getName());
    }

    public String getShieldAbsorbMessage() {
        int index = randomInt(SKELETON_SHIELD_ABSORB_MESSAGES.size());
        return String.format(SKELETON_SHIELD_ABSORB_MESSAGES.get(index), getName());
    }

    public String getPainMessage() {
        int index = randomInt(SKELETON_PAIN_MESSAGES.size());
        return String.format(SKELETON_PAIN_MESSAGES.get(index), getName());
    }

    /**
     * Получает случайное сообщение при атаке.
     *
     * @return Строка с текстом атаки, включающая имя персонажа.
     */
    @Override
    public String getAttackMessage() {
        int index = randomInt(SKELETON_ATTACK_MESSAGES.size());
        return String.format(SKELETON_ATTACK_MESSAGES.get(index), getName());
    }

    /**
     * Получает случайное сообщение при промахе.
     *
     * @return Строка с текстом промаха, включающая имя персонажа.
     */
    @Override
    public String getMissedMessage() {
        int index = randomInt(SKELETON_MISSED_MESSAGES.size());
        return String.format(SKELETON_MISSED_MESSAGES.get(index), getName());
    }

    /**
     * Получает случайное сообщение при перезарядке.
     *
     * @return Строка с текстом перезарядки, включающая имя персонажа.
     */
    @Override
    public String getReloadMessage() {
        int index = randomInt(SKELETON_RELOAD_MESSAGES.size());
        return String.format(SKELETON_RELOAD_MESSAGES.get(index), getName());
    }
}
