package tools;

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
     * возращает случайно число в от нуля до указанной границы size
     * @param size
     * @return
     */
    public static int randomInt(int size) {
        return random.nextInt(size);
    }
}
