package mechanic.interfaces;

import fight.dto.AttackDto;

/**
 * Всё что может атаковать и может быть атаковано в ответ.
 * Вызывается в фазу атаки.
 */
public interface Attackable extends Reloadable, Accuracy, Damageable {

    String getRandomReloadMessage();

    String getRandomAttackMessage();

    String getRandomMissedMessage();

    default AttackDto doReloadEvent(){
        return new AttackDto(this,
                getRandomReloadMessage());
    }

    default AttackDto doAttackEvent() {
        return new AttackDto(
                this,
                getDamage(),
                getRandomAttackMessage());
    }

   default AttackDto doMissedEvent() {
        return new AttackDto(this, getRandomMissedMessage());
    }

    default AttackDto attack(Defensible defensible) {
        if (getReloader() >= 1) {

            setReloader(0D);
            return doAttackEvent();
        } else {
            return doReloadEvent();
        }
    }

}
