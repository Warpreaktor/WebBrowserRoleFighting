package tools;

import dto.MinMax;
import dto.damage.DamageDto;
import hero.Hero;
import mechanic.Damage;
import mechanic.interfaces.Attackable;
import mechanic.interfaces.Defensible;

import java.util.Random;

/**
 * 🎲
 */
public class Dice {

    private static final Random random = new Random();

    public static double getChance() {
        return random.nextDouble();
    }

    /**
     * Метод совершает проверку на что-нибудь... на всё что угодно, у чего имеется некоторый шанс.
     * Критический удар, блок, уклонение, наложить шок или яд.
     *
     * @param chance шанс героя совершить действие, которое будет подвергнуто проверке.
     *
     * @return true если попытка была успешной.
     */
    public static boolean tryTo(double chance) {
        double dice = getChance();

        return chance >= dice;
    }

    public static int rollSix() {
        return randomInt(6) + 1;
    }

    public static int rollThree() {
        return randomInt(3) + 1;
    }

    public static int rollTen() {
        return randomInt(9) + 1;
    }

    public static int rollHundred() { return randomInt(100) + 1;}

    /**
     * Возращает случайное число от нуля до указанной границы size (исключительно)
     *
     * @param size граница в рамках которого будет возвращено число
     */
    public static int randomInt(int size) {
        return random.nextInt(size);
    }

    /**
     * Возращает случайное число от минимального до максимального (включительно)
     *
     * @param minMax границы в рамках которого будет возвращено число
     */
    public static double byMinMaxChance(MinMax minMax) {

        if (minMax.getMax() <= 0d) {
            return 0d;
        }

        return random.nextDouble(minMax.getMin(), minMax.getMax() + 1);
    }

    /**
     * Метод берёт все существующие в игре типы урона и расчитывает их на шанс по Мин Макс.
     *
     * @param damage урон, который необходимо посчитать по Мин Макс
     *
     * @return возвращается копия DamageDto объект
     */
    public static DamageDto byMinMaxChance(Damage damage) {

        return DamageDto
                .builder()
                .piercing(byMinMaxChance(damage.getPiercing()))
                .cutting(byMinMaxChance(damage.getCutting()))
                .crushing(byMinMaxChance(damage.getCrushing()))
                .fire(byMinMaxChance(damage.getFire()))
                .electric(byMinMaxChance(damage.getElectric()))
                .build();
    }

    /**
     * Атакующий прводит попытку попасть по врагу.
     *
     * @param attacker атакующий пытается попасть
     * @param defender защищающийся пытается увернуться
     *
     * @return true если атакующий попал
     */
    public static boolean tryToHit(Attackable attacker, Defensible defender) {
        double roll = rollHundred();

        return roll <= hitChance(attacker, defender);
    }

    /**
     * Логарифмическая формула: шанс попадания зависит от разницы точности и ловкости
     * Если accuracy намного выше agility, шанс попадания приближается к 100%.
     * Если agility выше, шанс уменьшается.
     * Если значения равны, шанс будет около 50%.
     */
    public static double hitChance(Attackable attacker, Defensible defender) {
        var accuracy = attacker.getAccuracy();
        var evasion = defender.getEvasion();

        return 100 / (Math.exp((evasion - accuracy) / 10.0));
    }

    /**
     * Считаем примерный урон исходя из шанса попадания
     */
    public static double expectedDamage(Damage realDamage, Hero attacker, Hero defender) {

        var damageDto = Dice.byMinMaxChance(realDamage);
        double averageDamage = damageDto.getSumDamage();

        double hitChancePercent = Dice.hitChance(attacker, defender);
        double hitChance = hitChancePercent / 100.0;

        return averageDamage * hitChance;
    }
}
