package constants;

import dto.MinMax;
import mechanic.Damage;

public class GlobalDamage {

    public static final Damage KNIFE_STRIKE = Damage
            .builder()
            .piercing(new MinMax(2d, 3d))
            .build();

    public static final Damage FIST_STRIKE = Damage
            .builder()
            .crushing(new MinMax(1d, 2d))
            .build();

    public static final Damage DISCHARGE = Damage
            .builder()
            .electric(new MinMax(1d, 3d))
            .build();

    public static final Damage SLINGSHOT_FROM_BRANCH = Damage
            .builder()
            .crushing(new MinMax(1d, 1.5))
            .build();

    public static final Damage SLING_FROM_ROPE = Damage
            .builder()
            .crushing(new MinMax(1.5, 2d))
            .build();

    public static final Damage GRANDPAS_RUSTY_SWORD = Damage
            .builder()
            .piercing(new MinMax(1d, 1.5))
            .crushing(new MinMax(1d))
            .build();

    public static final Damage MAGIC_WAND_OF_NEWBIE = Damage
            .builder()
            .fire(new MinMax(2d, 4d))
            .build();

    public static final Damage SHORTEST_BOW = Damage
            .builder()
            .piercing(new MinMax(2d, 3d))
            .build();

    public static final Damage JUST_A_STONE = Damage
            .builder()
            .crushing(new MinMax(1.2, 1.4))
            .build();
}
