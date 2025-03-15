package tools;

import dto.MinMax;

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

    public static double average(MinMax minMax) {
        if (minMax == null || minMax.getMin() == null || minMax.getMax() == null) {
            throw new IllegalArgumentException("Список чисел не должен быть пустым или null");
        }

        return (minMax.getMin() + minMax.getMax()) / 2;
    }

}
