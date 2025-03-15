package constants;

import dto.MinMax;
import mechanic.Damage;

import static constants.GlobalConstants.GLOBAL_DAMAGE_MULTIPLIER;

public class WeaponGlobalDamage {

    public static final Damage KNIFE_STRIKE = Damage
            .builder()
            .piercing(new MinMax(
                    2d * GLOBAL_DAMAGE_MULTIPLIER,
                    3d * GLOBAL_DAMAGE_MULTIPLIER
            ))
            .build();

    public static final Damage FIST_STRIKE = Damage
            .builder()
            .crushing(new MinMax(
                    1d * GLOBAL_DAMAGE_MULTIPLIER,
                    2d * GLOBAL_DAMAGE_MULTIPLIER
            ))
            .build();

    public static final Damage SLINGSHOT_FROM_BRANCH = Damage
            .builder()
            .crushing(new MinMax(
                    1d * GLOBAL_DAMAGE_MULTIPLIER,
                    1.5 * GLOBAL_DAMAGE_MULTIPLIER
            ))
            .build();

    public static final Damage SLING_FROM_ROPE = Damage
            .builder()
            .crushing(new MinMax(
                    1.5 * GLOBAL_DAMAGE_MULTIPLIER,
                    2d * GLOBAL_DAMAGE_MULTIPLIER
            ))
            .build();

    public static final Damage GRANDPAS_RUSTY_SWORD = Damage
            .builder()
            .piercing(new MinMax(
                    1d * GLOBAL_DAMAGE_MULTIPLIER,
                    1.5 * GLOBAL_DAMAGE_MULTIPLIER
            ))
            .crushing(new MinMax(
                    1d * GLOBAL_DAMAGE_MULTIPLIER))
            .build();

    public static final Damage MAGIC_WAND_OF_NEWBIE = Damage
            .builder()
            .fire(new MinMax(
                    2d * GLOBAL_DAMAGE_MULTIPLIER,
                    4d * GLOBAL_DAMAGE_MULTIPLIER
            ))
            .build();

    public static final Damage SHORTEST_BOW = Damage
            .builder()
            .piercing(new MinMax(
                    2d * GLOBAL_DAMAGE_MULTIPLIER,
                    3d * GLOBAL_DAMAGE_MULTIPLIER
            ))
            .build();

    public static final Damage JUST_A_STONE = Damage
            .builder()
            .crushing(new MinMax(
                    1.2 * GLOBAL_DAMAGE_MULTIPLIER,
                    1.4 * GLOBAL_DAMAGE_MULTIPLIER
            ))
            .build();
}
