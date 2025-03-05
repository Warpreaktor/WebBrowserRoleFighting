package mechanic.interfaces;

import dto.damage.DamageDto;
import dto.attack.AttackDto;

import static constants.GlobalConstants.COST_OF_AUTOATTACK;
import static constants.GlobalConstants.GLOBAL_CRIT_DAMAGE_MULTIPLIER;
import static tools.Dice.randomByMinMax;
import static tools.Dice.tryTo;

/**
 * Всё что может атаковать и может быть атаковано в ответ.
 * Вызывается в фазу атаки.
 */
public interface Attackable extends Restable, Accuracy, Damageable {

    String getRestMessage();

    String getAttackMessage();

    String getMissedMessage();

    Double getCritChance();

    /**
     * Метод отдыха по умолчанию.
     * Если персонаж пропускает ход без действий вызывается этот метод.
     */
    default AttackDto doRestEvent(){
        return new AttackDto(this,
                getRestMessage());
    }

    default AttackDto doAttackEvent() {

        var heroDamage = getStaticDamage();

        var piercingDamage = heroDamage.getPiercing();
        var crushingDamage = heroDamage.getCrushing();
        var cuttingDamage = heroDamage.getCutting();
        var fireDamage = heroDamage.getFire();
        var electricDamage = heroDamage.getElectric();

        //MinMax калькуляцая
        var damageDto = new DamageDto(
                randomByMinMax(piercingDamage),
                randomByMinMax(crushingDamage),
                randomByMinMax(cuttingDamage),
                randomByMinMax(fireDamage),
                randomByMinMax(electricDamage)
        );

        boolean isCritical = false;

        if (tryTo(getCritChance())) {
            //Критический удар
            damageDto.setCrushing(damageDto.getCrushing() * GLOBAL_CRIT_DAMAGE_MULTIPLIER);
            damageDto.setCutting(damageDto.getCutting() * GLOBAL_CRIT_DAMAGE_MULTIPLIER);
            damageDto.setPiercing(damageDto.getPiercing() * GLOBAL_CRIT_DAMAGE_MULTIPLIER);
            damageDto.setFire(damageDto.getFire() * GLOBAL_CRIT_DAMAGE_MULTIPLIER);
            damageDto.setElectric(damageDto.getElectric() * GLOBAL_CRIT_DAMAGE_MULTIPLIER);

           isCritical = true;
        }

        AttackDto attackDto =  new AttackDto(
                this,
                damageDto,
                getAttackMessage());

        attackDto.setCritical(isCritical);
        return attackDto;
    }

   default AttackDto doMissedEvent() {
        return new AttackDto(this, getMissedMessage());
    }

    default AttackDto attack(Defensible defensible) {
        if (getEndurance() >= COST_OF_AUTOATTACK) {

            setEndurance(getEndurance() - COST_OF_AUTOATTACK);
            return doAttackEvent();
        } else {
            return doRestEvent();
        }
    }

}
