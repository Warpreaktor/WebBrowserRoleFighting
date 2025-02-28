package hero.classes;

import hero.Hero;
import item.weapon.SlingShot;
import lombok.Getter;
import spec.HeroClass;

import java.util.List;

import static tools.Dice.randomInt;

@Getter
public class Halfling extends Hero {

    private final HeroClass heroClass = HeroClass.HALFLING;

    private static final int INTELLIGENCE = 5;
    private static final int STRENGTH = 3;
    private static final int DEXTERITY = 9;

    private static final double HEALTH = 45;
    private static final double SHIELD = 0;

    private static final double ACCURACY = 0.6;
    private static final double EVASION = 0.4;
    private static final double AGILITY = 0.7;
    private static final double ENDURANCE = 4.0;
    private static final double BLOCK_CHANCE = 0.15;
    private static final double CRIT_CHANCE = 0.25;


    public Halfling(String name) {
        super();

        setName(name);

        setIntelligence(INTELLIGENCE);
        setStrength(STRENGTH);
        setDexterity(DEXTERITY);

        setCritChance(CRIT_CHANCE);

        getHealth().addMaxValue(HEALTH);
        getShield().addMaxValue(SHIELD);

        setAccuracy(ACCURACY);
        setEvasion(EVASION);
        setEndurance(ENDURANCE);
        setAgility(AGILITY);

        setCritChance(CRIT_CHANCE);
        setBlockChance(BLOCK_CHANCE);

        getInventory().put(new SlingShot());

        getHealth().fillUp();
    }

    @Override
    public String getBlockedMessage() {
        int index = randomInt(BLOCKED_MESSAGES.size());
        return String.format(BLOCKED_MESSAGES.get(index), getName());
    }
    private static final List<String> BLOCKED_MESSAGES = List.of(
            "%s ловко подставил щит, отразив весь урон!",
            "%s спрятался за щитом, не получив ни царапины!",
            "%s вовремя выставил щит, поглотив удар!",
            "%s насмешливо выглянул из-за щита: ‘Это всё, что ты можешь?’",
            "%s отбил удар щитом, не дрогнув ни на мгновение!"
    );

    @Override
    public String getPainMessage() {
        int index = randomInt(PAIN_MESSAGES.size());
        return String.format(PAIN_MESSAGES.get(index), getName());
    }
    private static final List<String> PAIN_MESSAGES = List.of(
            "%s вскрикнул, ощущая резкую боль!",
            "%s стиснул зубы, стараясь не показывать слабость!",
            "%s охнул, схватившись за ушибленное место!",
            "%s моргнул от неожиданного удара, но остался на ногах!",
            "%s зашипел сквозь зубы, ощущая неприятное жжение в теле!"
    );

    public String getShieldAbsorbMessage() {
        int index = randomInt(SHIELD_ABSORB_MESSAGES.size());
        return String.format(SHIELD_ABSORB_MESSAGES.get(index), getName());
    }
    private static final List<String> SHIELD_ABSORB_MESSAGES = List.of(
            "%s лишь ухмыльнулся, когда его щит поглотил всю силу удара!",
            "Удар разбился о магический щит!",
            "%s даже не моргнул, щит всё впитал!"
    );

    public String getAttackMessage() {
        int index = randomInt(ATTACK_MESSAGES.size());
        return String.format(ATTACK_MESSAGES.get(index), getName());
    }
    private static final List<String> ATTACK_MESSAGES = List.of(
            "%s быстро скользнул в тень и нанес точный удар!",
            "%s ловко пробрался за спину врага и ударил!",
            "%s с хитрой улыбкой метнул кинжал!",
            "%s, использовав свою малую тень, атакует врага неожиданно!",
            "%s кувырком ушел от атаки и контратаковал!",
            "%s изящно уклонился и нанес ответный удар!"
    );

    public String getMissedMessage() {
        int index = randomInt(MISSED_MESSAGES.size());
        return String.format(MISSED_MESSAGES.get(index), getName());
    }
    private static final List<String> MISSED_MESSAGES = List.of(
            "%s слишком переоценил свою ловкость и промахнулся!",
            "%s скользнул, но его удар прошел мимо цели!",
            "%s слишком увлекся маневрированием и не попал!"
    );

    public String getRestMessage() {
        int index = randomInt(REST_MESSAGES.size());
        return String.format(REST_MESSAGES.get(index), getName());
    }
    private static final List<String> REST_MESSAGES = List.of(
            "%s ловко перекидывает кинжал из руки в руку!",
            "%s прячется в тени, готовясь к новому удару!",
            "%s находит удобный момент для атаки!"
    );
}
