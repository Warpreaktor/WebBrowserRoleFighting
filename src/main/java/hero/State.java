package hero;

import core.GameMaster;
import lombok.Getter;
import mechanic.interfaces.Switchable;
import mechanic.state.Shock;

/**
 *  Состояние героя. Все его действующие баффы и дебафы описываются здесь.
 *  Все переключения состояний проходят через этот класс.
 *  Он следит за тем, чтобы при любом переключении своих состояний сообщить об этом GameMaster.
 *  Если это правило будет соблюдаться, то GameMaster вовремя выключит действие того или иного эффекта.
 */
public class State {

    private GameMaster gameMaster;

    public State() {
        this.gameMaster = GameMaster.getInstance();
    }

    @Getter
    private Shock shock = new Shock();

    public void switchOn(Switchable switchable, int roundCount) {
        switchable.switchOn();
        gameMaster.switchOn(switchable, roundCount);
    }

    public void switchOff(Switchable switchable, int numRound) {
        switchable.switchOff();
    }
}
