package tools;

import dto.MinMax;
import dto.damage.DamageDto;
import mechanic.Damage;

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
}
