package core;

import mechanic.interfaces.Attackable;
import mechanic.interfaces.Defensible;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static tools.Dice.rollHundred;

public class GameMaster {

    private List<EventTimer> eventTimers;

    private Integer currentRound;

    public GameMaster() {
        eventTimers = new ArrayList<>();
        currentRound = 0;
    }

    /*
     * Логарифмическая формула: шанс попадания зависит от разницы точности и ловкости
     * Если accuracy намного выше agility, шанс попадания приближается к 100%.
     * Если agility выше, шанс уменьшается.
     * Если значения равны, шанс будет около 50%.
     */
    public double hitChance(Attackable attacker, Defensible defender) {
        var accuracy = attacker.getAccuracy();
        var evasion = defender.getEvasion();

        return 100 / (Math.exp((evasion - accuracy) / 10.0));
    }

    public boolean isHit(Attackable attacker, Defensible defender) {
        double roll = rollHundred();

        return roll <= hitChance(attacker, defender);
    }

    public int nextRound() {
        goodMorning();
        return currentRound += 1;
    }

    private void goodMorning() {
        var iterator = eventTimers.iterator();

        while (iterator.hasNext()) {
            var event = iterator.next();
            if (event.timeIsEnd()) {
                unBlock(event.flag);
                iterator.remove();
            }
        }
    }

    public void block(AtomicBoolean flag, int roundCount) {
        flag.set(true);
        eventTimers.add(new EventTimer(flag, roundCount));
    }

    public void unBlock(AtomicBoolean flag) {
        flag.set(false);
    }

    private static class EventTimer {

        AtomicBoolean flag;

        int roundCount;

        public EventTimer(AtomicBoolean flag, int roundCount) {
            this.flag = flag;
            this.roundCount = roundCount;
        }

        public boolean timeIsEnd() {
            roundCount -= 1;
            return roundCount < 0;
        }
    }
}
