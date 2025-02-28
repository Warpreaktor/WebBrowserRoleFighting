package tools;

import java.util.List;

public class Calculator {

    public static double average(List<Number> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            throw new IllegalArgumentException("Список чисел не должен быть пустым или null");
        }

        double sum = 0.0;
        for (Number number : numbers) {
            sum += number.doubleValue();
        }

        return sum / numbers.size();
    }

}
