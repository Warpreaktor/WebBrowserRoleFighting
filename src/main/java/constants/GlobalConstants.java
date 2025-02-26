package constants;

import config.ApplicationProperties;

public class GlobalConstants {

    /**
     * Весь урон в игре проходит через этот множитель. Увеличив его, увеличиться весь урон в игре.
     */
    public static final Double GLOBAL_DAMAGE_MULTIPLIER = 1.0;

    public static final Double GLOBAL_CRIT_DAMAGE_MULTIPLIER = 1.25;

    public static final String WEAPON_PICTURE_PATH = ApplicationProperties.getHost() + "/images/weapon";

    public static final Double HEALTH_PER_STRENGTH_MULTIPLIER = 2.0;

    public static final Double HEAL_PER_STRENGTH_MULTIPLIER = 0.0;

    public static final Double SHIELD_PER_INTELLIGENCE_MULTIPLIER = 0.0;

    public static final Double ACCURACY_PER_DEXTERITY_MULTIPLIER = 0.1;

    public static final Double EVASION_PER_DEXTERITY_MULTIPLIER = 0.1;

    public static final Double AGILITY_PER_DEXTERITY_MULTIPLIER = 0.1;

    public static final Double SHIELD_GROWER_PER_INTELLIGENCE_MULTIPLIER = 0.0;

    public static final Double COST_OF_AUTOATTACK = 1.0;

}
