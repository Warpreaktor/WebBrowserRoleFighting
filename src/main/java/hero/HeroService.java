package hero;

import hero.classes.Archer;
import hero.classes.Mage;
import hero.classes.Skeleton;
import hero.classes.Warrior;
import spec.HeroClass;

import java.util.List;
import java.util.Map;

import static hero.constants.names.HeroNames.ARCHER_NAMES;
import static hero.constants.names.HeroNames.MAGE_NAMES;
import static hero.constants.names.HeroNames.WARRIOR_NAMES;
import static hero.constants.names.HeroNames.SKELETON_NAMES;

import static spec.HeroClass.ARCHER;
import static spec.HeroClass.MAGE;
import static spec.HeroClass.SKELETON;
import static spec.HeroClass.WARRIOR;
import static tools.Dice.randomInt;

/**
 * Сервис для создания и управления персонажами.
 * Позволяет создавать персонажей различных классов, сохранять их и получать по ключу.
 */
public class HeroService {

    private static HeroService instance;

    private final HeroTable heroTable;

    /**
     * Сопоставление классов персонажей с их возможными именами
     */
    private static final Map<HeroClass, List<String>> CLASS_NAMES = Map.of(
            ARCHER, ARCHER_NAMES,
            MAGE, MAGE_NAMES,
            WARRIOR, WARRIOR_NAMES,
            SKELETON, SKELETON_NAMES
    );

    /**
     * Список доступных классов персонажей
     */
    private static final List<HeroClass> HERO_CLASSES = List.of(
            ARCHER, MAGE, WARRIOR, SKELETON
    );

    private HeroService() {
        this.heroTable = HeroTable.getInstance();
    }

    /**
     * Синглтончик
     *
     * @return Единственный экземпляр HeroService.
     */
    public static HeroService getInstance() {
        if (instance == null) {
            instance = new HeroService();
            return instance;
        }

        return instance;
    }

    /**
     * Создаёт персонажа с указанным именем и классом.
     * Если имя или класс не указаны, создаёт случайного персонажа.
     *
     * @param name Имя персонажа.
     * @param heroClass Класс персонажа.
     * @return Созданный персонаж.
     */
    public Hero createHero(String name, HeroClass heroClass) {

        Hero hero;

        if (name == null || heroClass == null) {
            hero = createRandomCharacter();
        } else {
            hero = createCharacter(name, heroClass);
        }

        return hero;
    }

    /**
     * Создаёт случайного персонажа со случайным именем и классом.
     *
     * @return Случайно созданный персонаж.
     */
    private Hero createRandomCharacter() {
        HeroClass heroClass = HERO_CLASSES.get(randomInt(HERO_CLASSES.size()));
        String heroName = CLASS_NAMES.get(heroClass)
                .get(randomInt(CLASS_NAMES.get(heroClass).size()));

        return createCharacter(heroName, heroClass);
    }

    /**
     * Создаёт персонажа с указанным именем и классом.
     *
     * @param name Имя персонажа.
     * @param heroClass Класс персонажа.
     * @return Созданный персонаж.
     */
    private Hero createCharacter(String name, HeroClass heroClass) {

        switch (heroClass) {

            case ARCHER:
                return new Archer(name);

            case MAGE:
                return new Mage(name);

            case WARRIOR:
                return new Warrior(name);

            case SKELETON:
                return new Skeleton(name);

            default:
                return createRandomCharacter();
        }
    }

    /**
     * Сохраняет персонажа в таблице героев по указанному ключу.
     *
     * @param key Ключ для сохранения персонажа.
     * @param hero Персонаж для сохранения.
     * @return Сохранённый персонаж.
     */
    public Hero saveCharacter(String key, Hero hero) {
        return heroTable.save(key, hero);
    }

    /**
     * Возвращает персонажа по указанному ключу.
     *
     * @param key Ключ для поиска персонажа.
     * @return Найденный персонаж или null, если персонаж не найден.
     */
    public Hero get(String key) {
        return heroTable.get(key);
    }
}
