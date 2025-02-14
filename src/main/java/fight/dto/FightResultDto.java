package fight.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import character.Hero;

import java.util.ArrayList;

@Getter
@AllArgsConstructor
public class FightResultDto {

    private boolean isOver;

    private Hero winner;

    private String message;

    private ArrayList<String> log;
}
