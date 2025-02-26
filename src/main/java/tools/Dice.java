package tools;

import dto.MinMax;

import java.util.Random;

/**
 * 🎲
 */
public class Dice {

    private static final Random random = new Random();

    public static double getChance() {
        return random.nextDouble();
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
    public static double randomByMinMax(MinMax minMax) {
        return random.nextDouble(minMax.getMin(), minMax.getMax() + 1);
    }
}
