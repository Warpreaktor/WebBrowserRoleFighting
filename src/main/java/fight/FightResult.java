package fight;

import hero.Hero;
import dto.fightresult.FightResultDto;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class FightResult {

    private boolean isOver;

    private Hero winner;

    private final ArrayList<String> message;

    private final ArrayList<String> log;

    public void setWinner(Hero winner) {
        isOver = true;
        this.winner = winner;
    }
    public FightResult() {
        isOver = false;
        this.message = new ArrayList<>();
        this.log = new ArrayList<>();
    }

    public List<String> getMessage() {
        return this.message;
    }

    public void addEventAndLog(String message) {
        log(message);
        this.message.add(message);
        getMessage();
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
