package mechanic.interfaces;

import dto.attack.AttackDto;
import dto.defense.DefenseDto;
import hero.State;
import mechanic.Health;
import mechanic.MagicScreen;

import static tools.Dice.getChance;

/**
 * Всё что может защищаться от атак.
 */
public interface Defensible extends Evasion {

    State getState();

    double getBlockChance();

    MagicScreen getMagicScreen();

    Health getHealth();

    String getPainMessage();

    String getMagicScreenAbsorbMessage();

    String getBlockedMessage();

    default DefenseDto defense(AttackDto attack) {
        double tryToBlock = getChance();

        if (getBlockChance() >= tryToBlock) {
            //Удар блокирован
            return new DefenseDto(0, getBlockedMessage());
        }

        double pain = attack.getDamageDto().getSumDamage();

        pain = getMagicScreen().takeDamage(pain);

        if (pain > 0) {

            getHealth().decreaseValue(pain);

            return new DefenseDto(attack.getDamageDto().getSumDamage(), getPainMessage());

        } else {
            return new DefenseDto(pain, getMagicScreenAbsorbMessage());
        }
    }

}
