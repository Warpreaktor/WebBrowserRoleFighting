package dto;

import lombok.Getter;
import lombok.NonNull;

public class MinMax {

    /**
     * Минимальное значение
     */
    @Getter
    private Double min;

    /**
     * Максимальное значение
     */
    @Getter
    private Double max;

    public MinMax(double value) {
        this.min = value;
        this.max = value;
    }

    public MinMax(double min, double max) {
        if (min > max) {
            throw new RuntimeException("минимальное значение не может быть больше максимального");
        }

        this.min = min;
        this.max = max;
    }

    public MinMax(@NonNull MinMax value) {
        this.min = value.getMin();
        this.max = value.getMax();
    }

    public MinMax multiply(Double multiplier) {
        this.min *= multiplier;
        this.max *= multiplier;
        return this;
    }

    public MinMax plus(MinMax value) {
        this.min += value.getMin();
        this.max += value.getMax();
        return this;
    }
}
