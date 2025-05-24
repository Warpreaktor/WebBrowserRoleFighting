package ability.hero.samodiva;

import ability.Ability;
import config.ApplicationProperties;
import enums.AbilityTarget;
import hero.Hero;

import static enums.AbilityTarget.ENEMY;

/**
 * Зов ветра
 */
public class CallOfTheWind extends Ability {

    private static final String PICTURE_PATH = ApplicationProperties.getHost() + "/images/ability/hero/samodiva/call_of_the_wind.png";

    private static final String NAME = "Зов ветра";

    private static final String DESCRIPTION = "Самодива исполняет резкий взмах руками, и порыв ветра со свистом обрушивается на противника.";

    private static final AbilityTarget TYPE = ENEMY;

    protected CallOfTheWind(int cost, int coolDown) {
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
