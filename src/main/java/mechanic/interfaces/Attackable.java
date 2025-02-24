package mechanic.interfaces;

import dto.damage.DamageDto;
import fight.dto.AttackDto;
import fight.dto.DefenseDto;

import static constants.GlobalConstants.COST_OF_AUTOATTACK;
import static constants.GlobalConstants.CRIT_DAMAGE_MULTIPLIER;
import static tools.Dice.getChance;

/**
 * Всё что может атаковать и может быть атаковано в ответ.
 * Вызывается в фазу атаки.
 */
public interface Attackable extends Reloadable, Accuracy, Damageable {

    String getReloadMessage();

    String getAttackMessage();

    String getMissedMessage();

    Double getCritChance();

    /**
     * Метод перезарядки по умолчанию.
     */
    default AttackDto doReloadEvent(){
        return new AttackDto(this,
                getReloadMessage());
    }

    default AttackDto doAttackEvent() {
        var physicalDamage = getDamage().getPhysicalDamage().getSumDamage();
        var magicDamage = getDamage().getFireDamage();
        boolean isCritical = false;

        double tryToCrit = getChance();

        if (getCritChance() >= tryToCrit) {
            //Критический удар
           physicalDamage = physicalDamage * CRIT_DAMAGE_MULTIPLIER;
           isCritical = true;
        }

        AttackDto attackDto =  new AttackDto(
                this,
                new DamageDto(physicalDamage, magicDamage),
                getAttackMessage());

        attackDto.setCritical(isCritical);
        return attackDto;
    }

   default AttackDto doMissedEvent() {
        return new AttackDto(this, getMissedMessage());
    }

    default AttackDto attack(Defensible defensible) {
        if (getReloader() >= COST_OF_AUTOATTACK) {

            setReloader(getReloader() - COST_OF_AUTOATTACK);
            return doAttackEvent();
        } else {
            return doReloadEvent();
        }
    }

}
