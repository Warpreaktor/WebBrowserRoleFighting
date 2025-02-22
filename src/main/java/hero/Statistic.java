package hero;

import lombok.Getter;

@Getter
public class Statistic {

    private int wins;

    public int plusWin() {
        return wins++;
    }

}
