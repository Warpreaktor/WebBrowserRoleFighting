package fight;

import hero.Hero;
import fight.dto.FightResultDto;
import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedList;

@Getter
public class FightResult {

    private boolean isOver;

    private Hero winner;

    private LinkedList<String> message;

    private ArrayList<String> log;

    public void setWinner(Hero winner) {
        isOver = true;
        this.winner = winner;
    }
    public FightResult() {
        isOver = false;
        this.message = new LinkedList<>();
        this.log = new ArrayList<>();
    }

    public String getMessage() {
        return this.message.toString();
    }

    public boolean isOver() {
        return isOver;
    }

    public String addEventAndLog(String message) {
        log(message);
        this.message.add(message);
        return getMessage();
    }

    public void clear() {
        message.clear();
    }

    public FightResultDto getResultDto() {
        return new FightResultDto(
                isOver,
                winner,
                getMessage(),
                getLog()
        );
    }

    public void log(String log) {
        this.log.add(log);
    }
}
