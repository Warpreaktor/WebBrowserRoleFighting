package tactic;

import fight.FightResult;
import hero.Hero;

public interface Tactic {

    void turn(Hero enemy, FightResult fightResult);
}
