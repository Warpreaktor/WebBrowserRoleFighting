package character.classes;

import character.Hero;
import fight.dto.AttackDto;
import fight.dto.DefenseDto;
import lombok.Getter;
import lombok.NonNull;
import dto.damage.DamageDto;
import mechanic.Intelligence;
import spec.HeroClass;

import java.util.List;

import static tools.Dice.randomInt;

/**
 * У мага повышенный рост магического щита.
 */
@Getter
public class Mage extends Hero {

    private final HeroClass heroClass = HeroClass.MAGE;

    //Классовые константы
    private static final Integer INTELLIGENCE = 4;

    private static final Integer STRENGTH = 2;

    private static final Integer DEXTERITY = 3;

    public Mage(String name) {
        super();

        setName(name);
        setDamage(new DamageDto(0.0, 6.0));

        //STATS
        var intelligence = new Intelligence(INTELLIGENCE);
        setIntelligence(INTELLIGENCE);
        //Маг стартует с полным щитом
        getShield().fillUp();
        //Маг получает бонус к скорости роста щита
        getShield().addShieldGrower(0.2);

        setStrength(STRENGTH);

        setDexterity(DEXTERITY);
        //Скорость атаки мага зависит от его интеллекта
        setAgility(INTELLIGENCE * 0.1);

        getHealth().fillUp();
    }

    @Override
    public AttackDto doReloadEvent() {
        return new AttackDto(this, getName() + " мутит магические пасы");
    }

    @Override
    public DefenseDto defense(@NonNull AttackDto attack) {
        double pain = attack.getDamageDto().getFullDamage();

        pain = getShield().takeDamage(pain);

        if (pain > 0) {
            takeDamage(pain);
            return new DefenseDto(attack.getDamageDto().getFullDamage(),
                    this.getName() + " терпит унизительную боль");
        } else {
            return new DefenseDto(pain,
                    " урон поглощён колдунством");
        }
    }

    public String getRandomAttackMessage() {
        int index = randomInt(MAGE_ATTACK_MESSAGES.size());
        return String.format(MAGE_ATTACK_MESSAGES.get(index), getName());
    }
    private static final List<String> MAGE_ATTACK_MESSAGES = List.of(
            "%s воздел руку и начертил в воздухе символ, после чего потоки магической энергии устремились к цели.",
            "%s молвил древние слова, от которых само пространство содрогнулось, и высвободил вихрь чистой разрушительной силы!",
            "%s изящным движением взмахнул посохом, и плазменная сфера устремилась к врагу, оставляя за собой шлейф синего пламени.",
            "%s сложил пальцы в знак заклинания, и воздух наполнился потрескиванием разряда — молния ударила с небес!",
            "%s возвысил голос, произнося имя давно забытого духа, и тот с готовностью исполнил его волю.",
            "%s чертил в воздухе мистические символы, излучая невыразимую мудрость древних миров.",
            "%s слегка улыбнулся и, щелкнув пальцами, сотворил огненную вспышку, осветившую поле битвы.",
            "%s поднял свой жезл, и из его вершины разлился ослепительный свет, поглотивший все вокруг.",
            "%s грациозным движением направил свою силу на врага, подобно дирижёру, управляющему симфонией стихий.",
            "%s процитировал строку из трактата архимагов, и вселенная покорно ответила на его зов.",
            "%s едва слышно выдохнул древнее имя, и оно эхом отозвалось во всём пространстве, высвобождая магию непреодолимой мощи.",
            "%s с достоинством расправил мантию и начертал в воздухе руну, которая расцвела ослепительными искрами.",
            "%s провёл рукой по воздуху, и в его ладони заплясали огненные нити, готовые обрушиться на противника.",
            "%s безмятежно улыбнулся, предвидя неминуемую победу, и направил свой посох в сторону врага.",
            "%s коснулся земли, и та ответила ему, породив ледяные копья, что взмыли в воздух и устремились к цели.",
            "%s с тихим восхищением наблюдал за тем, как потоки магической энергии переплетаются в его руках, готовые к сотворению чуда.",
            "%s поднял глаза к небесам и взмахнул посохом, пробуждая древнюю силу, дремавшую в самом воздухе.",
            "%s холодно посмотрел на противника и, не спеша, сложил ладони в знак концентрации — свет вспыхнул, и раздался раскат грома.",
            "%s прошептал заклинание так мягко, будто читал поэму, но его слова обладали мощью разрушительной бури.",
            "%s начертал перед собой рунический круг, а затем щелкнул пальцами, и врага окутали сковывающие оковы магического пламени.",
            "%s без суеты начертил в воздухе формулу, и реальность охотно подчинилась его воле.",
            "%s изящно повернул запястье, и из его ладони вырвался поток чистой магической энергии.",
            "%s грациозно склонил голову, будто выражая почтение противнику, и тем же движением выпустил магический снаряд.",
            "%s задумчиво нахмурил брови, словно решая философский вопрос, и тем временем его чары уже вершили судьбу боя.",
            "%s почти с сожалением посмотрел на противника, осознавая неизбежность его поражения.",
            "%s поднял руку к небу, и звёзды, кажется, ответили ему, окутывая поле битвы мерцающим сиянием.",
            "%s неспешно провёл по воздуху пальцем, словно рисуя картину, но из его руки вырвался поток ослепительного света.",
            "%s тихо рассмеялся, зная, что его знания о магии превосходят всё, что может противопоставить враг.",
            "%s легко взмахнул посохом, и тотчас же пространство вокруг него наполнилось шепотом древних чар.",
            "%s заговорил шёпотом, но даже сама ткань мироздания дрогнула под весом его слов."
    );

    public String getRandomMissedMessage() {
        int index = randomInt(MAGE_MISSED_MESSAGES.size());
        return String.format(MAGE_MISSED_MESSAGES.get(index), getName());
    }
    private static final List<String> MAGE_MISSED_MESSAGES = List.of(
            "%s задумался над уравнением движения, а враг уже сбежал!",
            "%s хмыкнул и поправил мантию – мелкие ошибки неизбежны.",
            "%s огорчённо вздохнул – магия всё же подвержена погрешностям.",
            "%s нахмурился – проклятье, не учёл гравитацию!",
            "%s скрестил пальцы – он ещё исправит недочёты!",
            "%s испепелил взглядом место, куда целился – вот только не туда.",
            "%s откашлялся – элегантность превыше всего, даже при промахе.",
            "%s зафиксировал в памяти новую ошибку – теперь он её учтёт.",
            "%s приподнял бровь – как любопытно, мана сегодня явно капризничает!",
            "%s сделал пометку в магическом дневнике – 'исправить формулу удара'.",
            "%s нахмурился – должно быть, меридианы силы сместились...",
            "%s улыбнулся – даже Архимаг ошибался, так что всё в порядке.",
            "%s кивнул – ну что ж, пусть враг наслаждается временной удачей.",
            "%s развернул манускрипт и быстро исправил формулу – следующий удар будет точен!",
            "%s зафиксировал новую закономерность – магическая точность требует калибровки.",
            "%s поправил шляпу – и начал новый виток расчётов.",
            "%s усмехнулся – промах, но зато какое изящество в жесте!",
            "%s пожал плечами – наука требует экспериментов, а эксперименты ошибок.",
            "%s покачал головой – явно что-то сбилось в потоках маны.",
            "%s записал промах как 'магическую аномалию'."
    );

    @Override
    public String getRandomReloadMessage() {
        int index = randomInt(MAGE_RELOAD_MESSAGES.size());
        return String.format(MAGE_RELOAD_MESSAGES.get(index), getName());
    }
    private static final List<String> MAGE_RELOAD_MESSAGES = List.of(
            "%s переплёл пальцы, сотворяя сложные магические символы в воздухе.",
            "%s закрыл глаза, шепча древние заклинания на неведомом языке.",
            "%s взмахнул руками, и воздух вокруг него начал мерцать энергией.",
            "%s начертил в воздухе светящийся рунный круг – подготовка в разгаре!",
            "%s медленно водит пальцами, словно играет на незримых струнах судьбы.",
            "%s выдохнул, направляя энергию в пальцы – нужно точное движение!",
            "%s тихо рассмеялся – магия любит терпеливых.",
            "%s взмахнул плащом, создавая завесу из искр и энергии.",
            "%s поправил перчатки – точность движений важна в каждом заклинании.",
            "%s повёл рукой – звёзды в ответ мигнули силой арканы.",
            "%s медленно вращает посох, концентрируя потоки магии.",
            "%s прикрыл глаза – настройка эфирных потоков требует внимания.",
            "%s расставил руки – его аура начала светиться таинственным светом.",
            "%s произнёс короткую мантру – она усиливает его концентрацию.",
            "%s провёл ладонью по книге заклинаний – правильные слова придут сами.",
            "%s улыбнулся – скоро вселенная узнает силу магического искусства!",
            "%s изменил стойку – так потоки маны текут лучше.",
            "%s начертил в воздухе сияющий знак – символ истинного волшебства.",
            "%s расставил пальцы – вот-вот энергия сольётся в единое целое!",
            "%s слегка склонил голову – мироздание задержало дыхание в ожидании..."
    );
}
