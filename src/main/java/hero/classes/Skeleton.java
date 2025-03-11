package hero.classes;

import dto.attack.AttackDto;
import dto.defense.DefenseDto;
import hero.Hero;
import item.shield.WoodenShield;
import item.weapon.Knife;
import lombok.Getter;
import lombok.NonNull;
import spec.HeroClass;

import static hero.constants.messages.SkeletonMessages.ATTACK_MESSAGES;
import static hero.constants.messages.SkeletonMessages.BLOCKED_MESSAGES;
import static hero.constants.messages.SkeletonMessages.MISSED_MESSAGES;
import static hero.constants.messages.SkeletonMessages.PAIN_MESSAGES;
import static hero.constants.messages.SkeletonMessages.REST_MESSAGES;
import static hero.constants.messages.SkeletonMessages.SHIELD_ABSORB_MESSAGES;
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

    //===============================//
    //    Стартовые характеристики   //
    //===============================//
    private static final int INTELLIGENCE = 3;
    private static final int STRENGTH = 5;
    private static final int DEXTERITY = 7;

    private static final double HEALTH = 40;
    private static final double MAGIC_SCREEN = 0;

    private static final double ACCURACY = 0.35;
    private static final double EVASION = 0.25;
    private static final double ENDURANCE = 3.0;
    private static final double ENDURANCE_GROWER = 0.6;
    private static final double BLOCK_CHANCE = 0.2;
    private static final double CRIT_CHANCE = 0.2;

    //============================================//
    //Коэффициенты прироста параметров за уровень//
    //==========================================//
    private static final double INTELLIGENCE_GROWTH = 0.2;
    private static final double STRENGTH_GROWTH = 0.5;
    private static final double DEXTERITY_GROWTH = 0.4;

    private static final double HEALTH_GROWTH = 3.0;
    private static final double ACCURACY_GROWTH = 0.015;
    private static final double EVASION_GROWTH = 0.01;
    private static final double ENDURANCE_GROWER_GROWTH = 0.03;
    private static final double ENDURANCE_GROWTH = 0.2;
    private static final double BLOCK_CHANCE_GROWTH = 0.01;
    private static final double CRIT_CHANCE_GROWTH = 0.015;

    /**
     * Создаёт нового скелета с заданным именем и базовыми характеристиками.
     *
     * @param name Имя скелета
     */
    public Skeleton(String name) {
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

        getInventory().put(new Knife());
        getInventory().put(new WoodenShield());

        getHealth().fillUp();
        getMagicScreen().fillUp();
        getEndurance().fillUp();
    }

    // Метод повышения уровня
    public void autoLevelUp() {
        setAutoLevel(getStatistic().getLevel() + 1);
    }

    public void setAutoLevel(int newLevel) {
        if (newLevel < 1) {
            throw new IllegalArgumentException("Уровень не может быть меньше 1");
        }
        getStatistic().setLevel(newLevel);

        getHealth().addMaxValue(HEALTH_GROWTH * (newLevel - 1));

        //TODO нужно будет добавить методы add-еры
        setAccuracy(ACCURACY + ACCURACY_GROWTH * (newLevel - 1));
        setEvasion(EVASION + EVASION_GROWTH * (newLevel - 1));
        getEndurance().setMaxValue(ENDURANCE + ENDURANCE_GROWTH * (newLevel - 1));
        getEndurance().setGrower(ENDURANCE_GROWER + ENDURANCE_GROWER_GROWTH * (newLevel - 1));
        setBlockChance(BLOCK_CHANCE + BLOCK_CHANCE_GROWTH * (newLevel - 1));
        setCritChance(CRIT_CHANCE + CRIT_CHANCE_GROWTH * (newLevel - 1));
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
        double pain = getMagicScreen().takeDamage(reduceDamage);

        if (pain > 0) {
            getHealth().decreaseValue(pain);
            return new DefenseDto(pain,
                    getPainMessage());
        }

        return new DefenseDto(0.0,
                getMagicScreenAbsorbMessage());
    }

    public String getBlockedMessage() {
        int index = randomInt(BLOCKED_MESSAGES.size());
        return String.format(BLOCKED_MESSAGES.get(index), getName());
    }

    public String getMagicScreenAbsorbMessage() {
        int index = randomInt(SHIELD_ABSORB_MESSAGES.size());
        return String.format(SHIELD_ABSORB_MESSAGES.get(index), getName());
    }

    public String getPainMessage() {
        int index = randomInt(PAIN_MESSAGES.size());
        return String.format(PAIN_MESSAGES.get(index), getName());
    }

    /**
     * Получает случайное сообщение при атаке.
     *
     * @return Строка с текстом атаки, включающая имя персонажа.
     */
    @Override
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
