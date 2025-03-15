package core;

import fight.FightResult;
import fight.FightService;
import hero.Hero;
import hero.HeroTable;
import mechanic.interfaces.Switchable;

import java.util.ArrayList;
import java.util.List;

import static hero.constants.HeroConstants.PLAYER1;
import static hero.constants.HeroConstants.PLAYER2;

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
        turnOrderCursor = 0;
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

    /**
     * Метод определяет кто следующий ходит
     */
    public Hero nextTurn() {

        var hero = turnOrder.get(turnOrderCursor);

        hero.getEffectStack().activate();

        checkEffects();

        turnOrderCursor++;

        if (turnOrderCursor > turnOrder.size() - 1) {
            FightService.getInstance().getResult().setRoundCount(currentRound += 1);
            turnOrderCursor = 0;
            FightService.getInstance().getResult().clear();
        }

        return hero;
    }

    private void checkEffects() {
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
