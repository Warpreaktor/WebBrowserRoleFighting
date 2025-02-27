package hero;

import hero.classes.Archer;
import hero.classes.Halfling;
import hero.classes.Mage;
import hero.classes.Skeleton;
import hero.classes.Barbarian;
import spec.HeroClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static hero.constants.names.HeroNames.ARCHER_NAMES;
import static hero.constants.names.HeroNames.HALFLING_NAMES;
import static hero.constants.names.HeroNames.MAGE_NAMES;
import static hero.constants.names.HeroNames.WARRIOR_NAMES;
import static hero.constants.names.HeroNames.SKELETON_NAMES;

import static spec.HeroClass.ARCHER;
import static spec.HeroClass.HALFLING;
import static spec.HeroClass.MAGE;
import static spec.HeroClass.SKELETON;
import static spec.HeroClass.BARBARIAN;
import static tools.Dice.randomInt;

/**
 * Сервис для создания и управления персонажами.
 * Позволяет создавать персонажей различных классов, сохранять их и получать по ключу.
 */
public class HeroService {

    private static HeroService instance;

    private final HeroTable heroTable;

    private List<Hero> levelingHeroList;

    /**
     * Сопоставление классов персонажей с их возможными именами
     */
    private static final Map<HeroClass, List<String>> CLASS_NAMES = Map.of(
            ARCHER, ARCHER_NAMES,
            MAGE, MAGE_NAMES,
            BARBARIAN, WARRIOR_NAMES,
            SKELETON, SKELETON_NAMES,
            HALFLING, HALFLING_NAMES
    );

    /**
     * Список доступных классов персонажей
     */
    private static final List<HeroClass> HERO_CLASSES = List.of(
            ARCHER, MAGE, BARBARIAN, SKELETON, HALFLING
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
     * Создаёт персонажа для игрока с указанным именем и классом.
     * Если имя или класс не указаны, создаёт случайного персонажа.
     *
     * @param name      Имя персонажа.
     * @param heroClass Класс персонажа.
     * @return Созданный персонаж.
     */
    public Hero createPlayerHero(String name, HeroClass heroClass) {
        return saveCharacter("player1", createHero(name, heroClass));
    }

    /**
     * Создаёт персонажа с указанным именем и классом.
     * Если имя или класс не указаны, создаёт случайного персонажа.
     *
     * @param name      Имя персонажа.
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
     * @param name      Имя персонажа.
     * @param heroClass Класс персонажа.
     * @return Созданный персонаж.
     */
    private Hero createCharacter(String name, HeroClass heroClass) {

        switch (heroClass) {

            case ARCHER:
                return new Archer(name);

            case MAGE:
                return new Mage(name);

            case BARBARIAN:
                return new Barbarian(name);

            case SKELETON:
                return new Skeleton(name);

            case HALFLING:
                return new Halfling(name);

            default:
                throw new RuntimeException("Класс героя не определён");
        }
    }

    /**
     * Сохраняет персонажа в таблице героев по указанному ключу.
     *
     * @param key  Ключ для сохранения персонажа.
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


    /**
     * Создаёт список скелетов с возрастающей сложностью от уровня 0 до maxLevel - 1.
     *
     * <p>Баланс построен так, что на первом уровне скелет очень слабый и полурослик
     * легко его побеждает. К 10-му уровню скелет становится значительно сильнее,
     * получая больше здоровья, урона, меткости и критического удара.</p>
     *
     * <p>Прогресс характеристик скелета:</p>
     * <ul>
     *     <li>Здоровье (HP): от 10 до 100 (растёт на 10 за уровень).</li>
     *     <li>Сила (Strength): от 1 до 10 (увеличивается на 1 за уровень).</li>
     *     <li>Ловкость (Dexterity): от 1 до 8 (увеличивается каждые 2 уровня).</li>
     *     <li>Интеллект (Intelligence): от 0 до 5 (растёт каждые 2 уровня).</li>
     *     <li>Крит. шанс: от 5% до 40%.</li>
     *     <li>Шанс блока: от 5% до 35%.</li>
     *     <li>Магический щит: зависит от интеллекта (если есть).</li>
     * </ul>
     *
     * @param maxLevel Количество уровней скелетов (обычно 10).
     * @return Список {@link Hero} скелетов с разной сложностью.
     */
    public void createLevelingHeroes(int maxLevel) {
        List<Hero> levelingCreatures = new ArrayList<>();

        for (int level = 0; level < maxLevel; level++) {
            Skeleton skeleton = new Skeleton("Скелет Ур. " + (level + 1));

            // Базовые характеристики
            int strength = 1 + level;
            int dexterity = 1 + (level / 2);
            int intelligence = (level / 3);

            // Применяем характеристики
            skeleton.setStrength(strength);
            skeleton.setDexterity(dexterity);
            skeleton.setIntelligence(intelligence);

            // Балансировка характеристик
            skeleton.getHealth().setValue(4.0);
            skeleton.getHealth().setMaxValue(4.0);
            skeleton.getShield().setMaxValue((double) intelligence); // Магический щит (если есть интеллект)

            // Меткость и уклонение
            skeleton.setAccuracy(0.5 + (dexterity * 0.05));
            skeleton.setEvasion(0.1 + (dexterity * 0.05));

            // Критический удар и блок
            skeleton.setCritChance(0.05 + (level * 0.035)); // Крит. шанс от 5% до 40%
            skeleton.setBlockChance(0.05 + (level * 0.03)); // Блок шанс от 5% до 35%

            levelingCreatures.add(skeleton);
        }

        levelingHeroList = levelingCreatures;
    }


    public Hero getLevelingHero(int creatureLevel) {

        return levelingHeroList.get(creatureLevel);
    }
}
