package fight.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import hero.Hero;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class FightResultDto {

    private boolean isOver;

    private Hero winner;

    private List<String> message;

    private ArrayList<String> log;
}
