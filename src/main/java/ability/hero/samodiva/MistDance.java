package ability.hero.samodiva;

import ability.Ability;
import config.ApplicationProperties;
import enums.AbilityTarget;
import hero.Hero;

import static enums.AbilityTarget.FRIENDLY;

/**
 * Танец тумана.
 * Завораживающая пляска заставляет лёгкий туман стелиться по земле, исцеляя Самодиву и сбивая прицел врагам.
 */
public class MistDance extends Ability {

    private static final String PICTURE_PATH = ApplicationProperties.getHost() + "/images/ability/hero/samodiva/mist_dance.png";

    private static final String NAME = "Танец тумана";

    private static final String DESCRIPTION = " Завораживающая пляска заставляет лёгкий туман стелиться по земле, " +
            "исцеляя Самодиву и сбивая прицел врагам.";

    private static final AbilityTarget TYPE = FRIENDLY;

    protected MistDance(int cost, int coolDown) {
        super(PICTURE_PATH, NAME, DESCRIPTION, TYPE, cost, coolDown);
    }

    @Override
    public void apply(Hero target) {

    }

    @Override
    public int getGameWeight() {
        return 0;
    }

    @Override
    public void trigger() {

    }
}
