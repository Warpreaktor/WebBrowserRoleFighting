package mechanic.interfaces;

import fight.dto.AttackDto;
import fight.dto.DefenseDto;

/**
 * Всё что может защищаться от атак.
 */
public interface Defensible extends Evasion {

    DefenseDto defense(AttackDto attack);

}
