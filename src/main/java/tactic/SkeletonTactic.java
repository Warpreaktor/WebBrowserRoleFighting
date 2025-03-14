package tactic;

import ability.hero.skeleton.BoneGrip;
import ability.hero.skeleton.CreakingOfBones;
import ability.hero.skeleton.NecroticVictim;
import ability.WeaponAbility;
import ability.item.KnifeStrike;
import dto.attack.AttackDto;
import effect.DamageBoost;
import equip.EquipSlot;
import fight.FightResult;
import fight.FightService;
import hero.Hero;
import hero.classes.Skeleton;
import item.weapon.Knife;
import ability.Ability;
import tools.Calculator;
import tools.Dice;

import java.util.List;

import static tools.Dice.expectedDamage;
import static tools.Dice.hitChance;
import static tools.Dice.tryToHit;

public class SkeletonTactic implements Tactic {

    private final Skeleton owner;

    // Ссылки на способности для удобного доступа
    private final KnifeStrike knifeStrike;
    private final BoneGrip boneGrip;
    private final CreakingOfBones creakingOfBones;
    private final NecroticVictim necroticVictim;

    public SkeletonTactic(Skeleton owner) {
        this.owner = owner;

        var weapon = new Knife(null);
        owner.getEquipment().equipped(EquipSlot.RIGHT_HAND, weapon);

        owner.clearAbilities();

        this.knifeStrike = new KnifeStrike(weapon);
        this.boneGrip = new BoneGrip(owner);
        this.creakingOfBones = new CreakingOfBones(owner);
        this.necroticVictim = new NecroticVictim(owner);

        owner.addAbilities(List.of(knifeStrike, boneGrip, creakingOfBones, necroticVictim));
    }

    @Override
    public void turn(Hero enemy, FightResult fightResult) {
        FightResult journal = FightService.getInstance().getResult();
        int weaponWeight = calculateWeaponAttackWeight(owner, knifeStrike, enemy);
        int boneGripWeight = calculateBoneGripWeight(owner, enemy, boneGrip);
        int necroticWeight = calculateNecroticVictimWeight(owner, enemy, necroticVictim);
        int creakingWeight = calculateCreakingOfBonesWeight(owner, enemy, creakingOfBones);

        ActionOption bestOption = new ActionOption(knifeStrike, weaponWeight);

        if (boneGripWeight > bestOption.weight) {
            bestOption = new ActionOption(boneGrip, boneGripWeight);
        }
        if (necroticWeight > bestOption.weight) {
            bestOption = new ActionOption(necroticVictim, necroticWeight);
        }
        if (creakingWeight > bestOption.weight) {
            bestOption = new ActionOption(creakingOfBones, creakingWeight);
        }

        if (bestOption.ability.equals(knifeStrike)) {
            journal.addEventAndLog(owner.getName() + " решает ударить оружием!");
            weaponAttack(enemy, fightResult);

        } else if (bestOption.ability.equals(boneGrip)) {
            journal.addEventAndLog(owner.getName() + " использует Костяную хватку!");
            boneGrip.apply(enemy);

        } else if (bestOption.ability.equals(necroticVictim)) {
            journal.addEventAndLog(owner.getName() + " использует Некротическую жертву!");
            necroticVictim.apply(owner);

        } else if (bestOption.ability.equals(creakingOfBones)) {
            journal.addEventAndLog(owner.getName() + " использует Скрежет костей!");
            creakingOfBones.apply(enemy);

        } else {
            journal.addEventAndLog(owner.getName() + " в растерянности... бьёт оружием наугад!");
            weaponAttack(enemy, fightResult);
        }
    }


    /**
     * Обычная атака оружием с учётом вероятности попадания
     */
    public AttackDto weaponAttack(Hero defender, FightResult fightResult) {
        var journal = FightService.getInstance().getResult();

        boolean hit = tryToHit(owner, defender);

        AttackDto attack;

        if (hit) {
            attack = owner.attack(defender);
        } else {
            attack = owner.doMissedEvent();
        }

        journal.addEventAndLog(attack.getMessage());

        return attack;
    }

    /**
     * Вычисляет вес (полезность) обычного удара оружием для скелета.
     *
     * @param skeleton скелет, совершающий атаку
     * @param enemy    противник, которого атакуем
     * @return числовой вес (чем выше, тем привлекательнее ход)
     */
    public int calculateWeaponAttackWeight(Skeleton skeleton, WeaponAbility ability, Hero enemy) {

        if (!canUseAbility(ability)) {
            return 0;
        }

        int baseWeight = 0;
        int abilityWeight = 0;
        int penalty = 0;

        // Оцениваем примерный потенциал урона. Для этого возьмем средний урон.
        var potentialDamage = Dice.byMinMaxChance(ability.getDamage().mixed(skeleton.getPassiveDamage()));
        double averageDamage = potentialDamage.getSumDamage();
        // Оцениваем шанс попадания.
        double hitChancePercent = hitChance(skeleton, enemy);
        double hitChance = hitChancePercent / 100.0;

        // Рассчитываем "ожидаемый урон" = средний урон * шанс попадания.
        double expectedDamage = averageDamage * hitChance;

        // Если у врага мало здоровья, атака может быть особо выгодной (нужно добить).
        double enemyHp = enemy.getHealth().getValue();

        // Чем выше ожидаемый урон относительно HP врага, тем выше вес.
        abilityWeight = (int) Math.floor(expectedDamage / enemyHp) * 10;

        return reductionToAWhole(abilityWeight) - penalty + baseWeight;
    }

    /**
     * Считает вес для способности BoneGrip
     */
    public int calculateBoneGripWeight(Skeleton skeleton, Hero enemy, BoneGrip boneGrip) {

        if (!canUseAbility(boneGrip)) {
            return 0;
        }

        double expectedDamage = expectedDamage(boneGrip.getDamage(), skeleton, enemy);

        double enemyHp = enemy.getHealth().getValue();

        if (expectedDamage >= enemyHp) {
            return 10;
        }

        // Базовый вес за нанесение урона
        double baseWeight = (expectedDamage / enemyHp) * 10;

        // Определяем, насколько удушение  реально ограничит способности врага.
        double strangulationChance = boneGrip.getStrangulationChance();
        int enduranceDrain = boneGrip.getEffect().ENDURANCE_DRAIN;

        // Считаем, каких и на сколько важных способностей станет недоступно врагу,
        // если его макс выносливость уменьшится на enduranceDrain.
        double enemyMaxStamina = enemy.getEndurance().getMaxValue();
        double futureEnemyMaxStamina = enemyMaxStamina - enduranceDrain;

        // Просматриваем способности врага
        int blockedImportance = 0;
        for (Ability enemyAbility : enemy.getAbilities()) {

            if (!enemyAbility.isActive()) {
                continue;
            }

            int cost = enemyAbility.getCost();

            boolean canUseNow = (cost <= enemyMaxStamina);
            boolean blockedNext = (cost > futureEnemyMaxStamina);

            if (canUseNow && blockedNext) {
                blockedImportance += enemyAbility.getGameWeight();
            }
        }

        //Каков шанс того что удушение заблокирует способность
        double strangulationContribution = blockedImportance * strangulationChance;

        // Складываем всё вместе
        double totalWeight = baseWeight + strangulationContribution;

        return reductionToAWhole(totalWeight);
    }

    /**
     * Высчитывает вес применения способности NecroticVictim.
     */
    public int calculateNecroticVictimWeight(Skeleton skeleton,
                                             Hero enemy,
                                             NecroticVictim necroticVictim) {

        if (!canUseAbility(necroticVictim)) {
            return 0;
        }

        // Если уже есть аналогичный бафф DamageBoost с таким же множителем,
        var effect = skeleton.getEffectStack().findEffect(necroticVictim.getEffect().getName());
        if (effect.isPresent()) {
           return 0;
        }

        // Учтем длительность действия баффа
        int buffDuration = necroticVictim.getEffect().getDuration();

        // Оцениваем средний урон от "обычной" атаки без баффа
        double expectedDamageOneAttack = expectedDamage(skeleton.getPassiveDamage(), skeleton, enemy);

        // Суммарный урон за 2 атаки без баффа:
        double totalDamageNoBuff = expectedDamageOneAttack * buffDuration;

        // Суммарный урон за 2 атаки с баффом
        double totalDamageWithBuff = expectedDamageOneAttack * necroticVictim.getEffect().getDamageBoost() * buffDuration;

        // Прирост урона
        double extraDamage = totalDamageWithBuff - totalDamageNoBuff;

        // 4) Переводим прирост урона в базовый вес, учитывая, сколько HP у врага.
        double enemyHp = enemy.getHealth().getValue();
        double baseWeight = 0.0;
        if (enemyHp > 0) {
            baseWeight = (extraDamage / enemyHp) * 10;
        }

        // Добавим веса, если у врага HP, более чем на 4 удара
        if (enemyHp > expectedDamageOneAttack * 4) {
            baseWeight += 2;
        }

        //Штраф за потерю своего HP:
        double currentHp = skeleton.getHealth().getValue();
        double maxHp = skeleton.getHealth().getMaxValue();

        double sacrifice = currentHp * necroticVictim.getHealthSacrificePercent();
        double hpAfterCast = currentHp - sacrifice;

        // Если после бафа HP < чем на 3 атаки врага
        double enemyAverageDamage = enemy.getDamageInfo().getSumDamage();
        double penaltyForLowHp = 0.0;
        if (hpAfterCast / enemyAverageDamage < 5) {
            penaltyForLowHp = 5 - hpAfterCast / enemyAverageDamage;
        }

        double totalWeight = baseWeight - penaltyForLowHp;

        return reductionToAWhole(totalWeight);
    }

    /**
     * Округляем результат к целому в пределах 0..10
     */
    private int reductionToAWhole(double weight){

        int finalWeight = (int) Math.round(weight);

        if (finalWeight > 10) {
            return 10;
        } else if (finalWeight < 0) {
            return 0;
        }

        return finalWeight;
    }

    /**
     * Рассчитывает вес применения способности CreakingOfBones.
     */
    public int calculateCreakingOfBonesWeight(Skeleton skeleton,
                                              Hero enemy,
                                              CreakingOfBones creakingOfBones) {

        if (canUseAbility(creakingOfBones)) {
            return 0;
        }


        // Рассчитываем, сколько примерно «психики» снимем
        double avgFear = Calculator.average(creakingOfBones.getFearPower());

        // Учитываем вероятность срабатывания (fearChance)
        double expectedFear = avgFear * creakingOfBones.getFearChance();

        // Смотрим на «психику» врага
        double enemyPsych = enemy.getPsych().getValue();
        if (enemyPsych <= expectedFear) {
            return 0;
        }

        // Рассчитываем базовый вес эффекта,
        double baseWeight = (expectedFear / enemyPsych) * 10;

        // Учитываем общий вес способности
        int abilityGameWeight = creakingOfBones.getGameWeight();
        double synergyBonus = abilityGameWeight * 0.5;
        double totalWeight = baseWeight + synergyBonus;

        // Если психика у врага уже очень низкая будем давить страхом
        double enemyPsychRatio = enemyPsych / enemy.getPsych().getMaxValue();
        if (enemyPsychRatio < 0.30) {
            // усилим вес
            totalWeight += 2;
        }

        return reductionToAWhole(totalWeight);
    }

    private static class ActionOption {
        Ability ability;
        int weight;

        public ActionOption(Ability ability, int weight) {
            this.ability = ability;
            this.weight = weight;
        }
    }

    /**
     * Метод проверяет, не нужно ли произвести попытку лечения.
     * Возможно, использовать лечащую способность.
     *
     * @return true если лечение необходимо
     */
    private boolean needHealing() {

        double currentHP = owner.getHealth().getValue();

        double maxHP = owner.getHealth().getMaxValue();

        return currentHP / maxHP < 0.3;
    }

    /**
     * Метод проверяет можно ли использовать способность
     *
     * @param ability способность, которая подвергается проверке
     *
     * @return true если способность можно использовать
     */
    private boolean canUseAbility(Ability ability) {
        if (!ability.isActive()) {
            return false;
        }
        return owner.getEndurance().getValue() >= ability.getCost();
    }

}


