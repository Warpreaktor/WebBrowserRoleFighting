package dto.damage;

import lombok.Getter;

import static constants.GlobalConstants.GLOBAL_DAMAGE_MULTIPLIER;

@Getter
public class PhysicalDamage {

    /**
     * Колющий урон
     */
    private Double piercingMax = 0.0;

    /**
     * Режущий урон
     */
    private Double cuttingMax = 0.0;

    /**
     * Дробящий урон
     */
    private Double crushingMax = 0.0;


    public void setPiercingMax(Double value) {
        piercingMax = value * GLOBAL_DAMAGE_MULTIPLIER;
    }

    public void setCuttingMax(Double value) {
        cuttingMax = value * GLOBAL_DAMAGE_MULTIPLIER;
    }

    public void setCrushingMax(Double value) {
        crushingMax = value * GLOBAL_DAMAGE_MULTIPLIER;
    }

    public void addPiercing(Double value) {
        piercingMax += value * GLOBAL_DAMAGE_MULTIPLIER;
    }

    public void addCutting(Double value) {
        cuttingMax += value * GLOBAL_DAMAGE_MULTIPLIER;
    }

    public void addCrushing(Double value) {
        crushingMax += value * GLOBAL_DAMAGE_MULTIPLIER;
    }

    public Double getSumDamage() {
        return piercingMax + cuttingMax + crushingMax;
    }
}
