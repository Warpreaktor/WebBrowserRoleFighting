package commentator;

import java.util.List;

import static tools.Dice.randomInt;
import static tools.Dice.rollSix;

/**
 * Сервис генерации всяких дурацкий сообщений о ходе боя
 */
public class CommentatorService {
    private static final List<String> BATTLE_MESSAGES = List.of(
            "%s и %s злобно смотрят друг на друга!",
            "%s и %s переминаются с ноги на ногу!",
            "%s делает ложный манёвр, но %s лишь усмехается.",
            "%s сжимает кулаки, готовясь к бою, %s стиснул зубы!",
            "%s и %s кружат в танце смерти выжидая момент.",
            "%s поскальзывается на банановой кожуре, но не теряет равновесия!",
            "%s дарит противнику презрительный взгляд.",
            "%s и %s одновременно делают шаг назад.",
            "%s нервно топает ножкой",
            "%s и %s изучающе смотрят друг на друга",
            "%s хищно улыбается, %s показывает язык.",
            "%s прыгает на месте, %s смотрит на него ничего не понимая",
            "Слышно лишь, как шуршит ветер гоняя листву",
            "%s напрягает мышцы, готовясь к атаке",
            "%s облизывает сухие губы",
            "%s сплёвывает комок в горле",
            "%s размашисто крутит оружием",
            "%s издаёт боевой клич",
            "%s и $s встали как вкопанные",
            "%s и %s оценивают друг друга!"
    );

    public static String getRandomBattleMessage(String name1, String name2) {
        int index = randomInt(BATTLE_MESSAGES.size());

        //Перемешиваем имена
        if (rollSix() > 3) {
            return String.format(BATTLE_MESSAGES.get(index), name1, name2);
        } else {
            return String.format(BATTLE_MESSAGES.get(index), name2, name1);
        }

    }
}
