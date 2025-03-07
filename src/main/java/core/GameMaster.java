package core;

import hero.Hero;
import hero.HeroTable;
import mechanic.interfaces.Attackable;
import mechanic.interfaces.Defensible;
import mechanic.interfaces.Switchable;

import java.util.ArrayList;
import java.util.List;

import static hero.constants.HeroConstants.PLAYER1;
import static hero.constants.HeroConstants.PLAYER2;
import static tools.Dice.rollHundred;

public class GameMaster {

    private static GameMaster instance;

    private final HeroTable heroTable;

    private final ArrayList<Hero> turnOrder;

    private int turnOrderCursor;

    private final List<EventTimer> eventTimers;

    private Integer currentRound;

    public GameMaster() {
        eventTimers = new ArrayList<>();
        currentRound = 0;
        heroTable = HeroTable.getInstance();

        turnOrder = new ArrayList<>(2);
        turnOrder.add(heroTable.get(PLAYER1));
        turnOrder.add(heroTable.get(PLAYER2));
    }

    public static GameMaster getInstance() {
        if (instance == null) {
            instance = new GameMaster ();
            return instance;
        }

        return instance;
    }

    public static GameMaster newInstance() {
        instance = null;
        return getInstance();
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
                event.switcher();
                iterator.remove();
            }
        }
    }

    /**
     * Активирует способность/эффект на некоторое количество раундов
     * Если нужно активировать способность или эффект только
     * на остаток текущего раунда в параметр roundCount нужно передать 0
     *
     * @param switchObject объект который нужно активировать
     * @param roundCount количество раундов на которое активируется объект
     */
    public void switchOn(Switchable switchObject, int roundCount) {
        switchObject.switchOn();
        eventTimers.add(new EventTimer(switchObject, roundCount));
    }

    /**
     * Деактивирует способность/эффект на некоторое количество раундов.
     * Если нужно заморозить способность или деактивировать эффект только
     * на остаток текущего раунда в параметр roundCount нужно передать 0
     *
     * @param switchObject объект который нужно активировать
     * @param roundCount количество раундов на которое активируется объект
     */
    public void switchOff(Switchable switchObject, int roundCount) {
        switchObject.switchOff();
        eventTimers.add(new EventTimer(switchObject, roundCount));
    }

    /**
     * Метод определяет кто следующий ходит
     */
    public Hero nextTurn() {
        if (turnOrderCursor > 1) {
            turnOrderCursor = 0;
        }
        var hero = turnOrder.get(turnOrderCursor);
        turnOrderCursor++;
        return hero;
    }

    private static class EventTimer {

        Switchable switchObject;

        int roundCount;

        public EventTimer(Switchable switchObject, int roundCount) {
            this.switchObject = switchObject;
            this.roundCount = roundCount;
        }

        public boolean timeIsEnd() {
            roundCount -= 1;
            return roundCount < 0;
        }

        /**
         * Переключает активность эффекта/способности в противоположное состояние.
         */
        public void switcher() {
            var switchable = this.switchObject;

            if (switchable.isActive()) {

                switchable.switchOff();
            } else {
                switchable.switchOn();
            }
        }
    }
}
