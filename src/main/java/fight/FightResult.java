package fight;

import hero.Hero;
import fight.dto.FightResultDto;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class FightResult {

    private boolean isOver;

    private Hero winner;

    private StringBuilder message;

    private ArrayList<String> log;

    public void setWinner(Hero winner) {
        isOver = true;
        this.winner = winner;
    }
    public FightResult() {
        isOver = false;
        this.message = new StringBuilder();
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
        this.message.append("<p>").append(message).append("<p>");
        return getMessage();
    }

    public void clear() {
        message.setLength(0);
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
