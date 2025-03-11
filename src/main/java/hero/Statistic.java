package hero;

import lombok.Getter;

public class Statistic {

    @Getter
    private int level;

    @Getter
    private int wins;

    public Statistic() {
        level = 1;
        wins = 0;
    }

    public int plusWin() {
        return this.wins++;
    }

    public int plusLevel() {
        return this.level++;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}
