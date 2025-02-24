package mechanic.interfaces;

import fight.dto.AttackDto;
import fight.dto.DefenseDto;
import mechanic.Health;
import mechanic.Shield;

import static tools.Dice.getChance;

/**
 * Всё что может защищаться от атак.
 */
public interface Defensible extends Evasion {

    double getBlockChance();

    Shield getShield();

    Health getHealth();

    String getPainMessage();

    String getShieldAbsorbMessage();

    String getBlockedMessage();

    default DefenseDto defense(AttackDto attack) {
        double tryToBlock = getChance();

        if (getBlockChance() >= tryToBlock) {
            //Удар блокирован
            return new DefenseDto(0, getBlockedMessage());
        }

        double pain = attack.getDamageDto().getFullDamage();

        pain = getShield().takeDamage(pain);

        if (pain > 0) {

            getHealth().takeDamage(pain);

            return new DefenseDto(attack.getDamageDto().getFullDamage(), getPainMessage());

        } else {
            return new DefenseDto(pain, getShieldAbsorbMessage());
        }
    }

}
