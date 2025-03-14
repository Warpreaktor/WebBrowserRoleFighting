package mechanic.interfaces;

import dto.damage.DamageDto;
import dto.attack.AttackDto;
import fight.FightService;

import static constants.GlobalConstants.COST_OF_WEAPON_ATTACK;
import static constants.GlobalConstants.GLOBAL_CRIT_DAMAGE_MULTIPLIER;
import static tools.Dice.byMinMaxChance;
import static tools.Dice.tryTo;

/**
 * Всё что может атаковать и может быть атаковано в ответ.
 * Вызывается в фазу атаки.
 */
public interface Attackable extends Endured, Accuracy, Damageable {

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

        var heroDamage = getPassiveDamage();

        var piercingDamage = heroDamage.getPiercing();
        var crushingDamage = heroDamage.getCrushing();
        var cuttingDamage = heroDamage.getCutting();
        var fireDamage = heroDamage.getFire();
        var electricDamage = heroDamage.getElectric();

        //MinMax калькуляцая
        var damageDto = new DamageDto(
                byMinMaxChance(piercingDamage),
                byMinMaxChance(crushingDamage),
                byMinMaxChance(cuttingDamage),
                byMinMaxChance(fireDamage),
                byMinMaxChance(electricDamage)
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

        if (getEndurance().getValue() >= COST_OF_WEAPON_ATTACK) {

            getEndurance().decreaseValue(getEndurance().getValue() - COST_OF_WEAPON_ATTACK);

            return doAttackEvent();

        } else {
            FightService.getInstance().getResult().addEventAndLog("Не хватает выносливости");
            throw new RuntimeException("Не хватает выносливости");
        }
    }

}
