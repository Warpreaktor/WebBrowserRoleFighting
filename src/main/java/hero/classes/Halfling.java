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

    private static final int INTELLIGENCE = 2;

    private static final int STRENGTH = 2;

    //Полурослики чрезвычайно ловкие
    private static final int DEXTERITY = 6;

    //Полурослики народ свирепый и имеют повышенный шанс крита
    private static final double CRIT_CHANCE = 0.25;

    public Halfling(String name) {
        super();

        setName(name);

        setIntelligence(INTELLIGENCE);
        setStrength(STRENGTH);
        setDexterity(DEXTERITY);

        setCritChance(CRIT_CHANCE);

        //Полурослик коротыщшка у него пониженное здоровье
        getHealth().setMaxValue(3.0);
        getHealth().fillUp();

        getInventory().put(new SlingShot());
    }

    @Override
    public String getBlockedMessage() {
        int index = randomInt(HALFLING_BLOCKED_MESSAGES.size());
        return String.format(HALFLING_BLOCKED_MESSAGES.get(index), getName());
    }
    private static final List<String> HALFLING_BLOCKED_MESSAGES = List.of(
            "%s ловко подставил щит, отразив весь урон!",
            "%s спрятался за щитом, не получив ни царапины!",
            "%s вовремя выставил щит, поглотив удар!",
            "%s насмешливо выглянул из-за щита: ‘Это всё, что ты можешь?’",
            "%s отбил удар щитом, не дрогнув ни на мгновение!"
    );

    @Override
    public String getPainMessage() {
        int index = randomInt(HALFLING_PAIN_MESSAGES.size());
        return String.format(HALFLING_PAIN_MESSAGES.get(index), getName());
    }
    private static final List<String> HALFLING_PAIN_MESSAGES = List.of(
            "%s вскрикнул, ощущая резкую боль!",
            "%s стиснул зубы, стараясь не показывать слабость!",
            "%s охнул, схватившись за ушибленное место!",
            "%s моргнул от неожиданного удара, но остался на ногах!",
            "%s зашипел сквозь зубы, ощущая неприятное жжение в теле!"
    );

    public String getShieldAbsorbMessage() {
        int index = randomInt(HALFLING_SHIELD_ABSORB_MESSAGES.size());
        return String.format(HALFLING_SHIELD_ABSORB_MESSAGES.get(index), getName());
    }
    private static final List<String> HALFLING_SHIELD_ABSORB_MESSAGES = List.of(
            "%s лишь ухмыльнулся, когда его щит поглотил всю силу удара!",
            "Удар разбился о магический щит!",
            "%s даже не моргнул, щит всё впитал!"
    );

    public String getAttackMessage() {
        int index = randomInt(HALFLING_ATTACK_MESSAGES.size());
        return String.format(HALFLING_ATTACK_MESSAGES.get(index), getName());
    }
    private static final List<String> HALFLING_ATTACK_MESSAGES = List.of(
            "%s быстро скользнул в тень и нанес точный удар!",
            "%s ловко пробрался за спину врага и ударил!",
            "%s с хитрой улыбкой метнул кинжал!",
            "%s, использовав свою малую тень, атакует врага неожиданно!",
            "%s кувырком ушел от атаки и контратаковал!",
            "%s изящно уклонился и нанес ответный удар!"
    );

    public String getMissedMessage() {
        int index = randomInt(HALFLING_MISSED_MESSAGES.size());
        return String.format(HALFLING_MISSED_MESSAGES.get(index), getName());
    }
    private static final List<String> HALFLING_MISSED_MESSAGES = List.of(
            "%s слишком переоценил свою ловкость и промахнулся!",
            "%s скользнул, но его удар прошел мимо цели!",
            "%s слишком увлекся маневрированием и не попал!"
    );

    public String getReloadMessage() {
        int index = randomInt(HALFLING_RELOAD_MESSAGES.size());
        return String.format(HALFLING_RELOAD_MESSAGES.get(index), getName());
    }
    private static final List<String> HALFLING_RELOAD_MESSAGES = List.of(
            "%s ловко перекидывает кинжал из руки в руку!",
            "%s прячется в тени, готовясь к новому удару!",
            "%s находит удобный момент для атаки!"
    );
}
