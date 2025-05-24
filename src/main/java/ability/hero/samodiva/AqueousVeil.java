package ability.hero.samodiva;

import ability.Ability;
import config.ApplicationProperties;
import enums.AbilityTarget;
import hero.Hero;

import static enums.AbilityTarget.SELF;

/**
 * Водяная пелена
 */
public class AqueousVeil extends Ability {

    private static final String PICTURE_PATH = ApplicationProperties.getHost() + "/images/ability/hero/samodiva/aqueous_veil.png";

    private static final String NAME = "Водяная пелена";

    private static final String DESCRIPTION = "Самодива призывает потоки влаги, обволакивающие её лёгкой дымкой.";

    private static final AbilityTarget TYPE = SELF;

    protected AqueousVeil(int cost, int coolDown) {
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
