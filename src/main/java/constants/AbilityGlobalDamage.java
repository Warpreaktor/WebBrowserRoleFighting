package constants;

import dto.MinMax;
import mechanic.Damage;

import static constants.GlobalConstants.GLOBAL_DAMAGE_MULTIPLIER;

public class AbilityGlobalDamage {

    public static final Damage DISCHARGE = Damage
            .builder()
            .electric(new MinMax(
                    1d * GLOBAL_DAMAGE_MULTIPLIER,
                    2d * GLOBAL_DAMAGE_MULTIPLIER
            ))
            .build();

    public static final Damage BONE_GRIP = Damage
            .builder()
            .crushing(new MinMax(
                    2d * GLOBAL_DAMAGE_MULTIPLIER,
                    3d * GLOBAL_DAMAGE_MULTIPLIER
            ))
            .build();
}
