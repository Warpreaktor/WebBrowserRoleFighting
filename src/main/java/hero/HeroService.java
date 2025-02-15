package hero;

import hero.classes.Archer;
import hero.classes.Mage;
import hero.classes.Warrior;
import spec.HeroClass;

import java.util.List;
import java.util.Map;

import static spec.HeroClass.ARCHER;
import static spec.HeroClass.MAGE;
import static spec.HeroClass.WARRIOR;
import static tools.Dice.randomInt;

public class HeroService {

    private static HeroService instance;

    private final HeroTable heroTable;

    private static final List<String> WARRIOR_NAMES = List.of(
            "Рагнар", "Крум", "Торгрим", "Вольфрик", "Драгор", "Ульфгар",
            "Грогаш", "Бьорн", "Скар", "Тарг", "Хротгар", "Одинокий Волк",
            "Дреккар", "Моркар", "Зигфрид", "Фенрир", "Ворн", "Хаген",
            "Грюм", "Торстен", "Лугнор", "Эйнар", "Хельвар", "Гуннар",
            "Сигурд", "Кром", "Хальвар", "Вранг", "Борг", "Йорген"
    );

    private static final List<String> MAGE_NAMES = List.of(
            "Мерлин", "Эльдрак", "Затара", "Альзарак", "Фенрис", "Ордо",
            "Малкорион", "Таргон", "Сальвиус", "Астрагор", "Локриан", "Мидран",
            "Эльмариус", "Феаргрим", "Нокс", "Селезар", "Дрейкворт", "Альзирон",
            "Каларис", "Талмор", "Галадриан", "Руфинус", "Зерон", "Агатос",
            "Маласар", "Вардан", "Иллюмор", "Тарсис", "Санхариус", "Зарафим"
    );

    private static final List<String> ARCHER_NAMES = List.of(
            "Элдрин", "Тарвин", "Леголас", "Финдор", "Кайриан", "Рунальф",
            "Альториан", "Скайлен", "Эрвин", "Гилвар", "Тиандр", "Фенрир",
            "Ларвис", "Гайлен", "Сильвен", "Айден", "Вальтар", "Рейнальд",
            "Эльмир", "Зилвер", "Тайрон", "Орланд", "Фаргон", "Равен",
            "Стрелок Орис", "Хантер", "Бельмонт", "Треон", "Сендор", "Каспер"
    );

    private static final Map<HeroClass, List<String>> CLASS_NAMES = Map.of(
            ARCHER, ARCHER_NAMES,
            MAGE, MAGE_NAMES,
            WARRIOR, WARRIOR_NAMES
    );

    private static final List<HeroClass> HERO_CLASSES = List.of(
            ARCHER, MAGE, WARRIOR
    );

    private HeroService() {
        this.heroTable = HeroTable.getInstance();
    }

    /**
     * Синглтончик
     */
    public static HeroService getInstance() {
        if (instance == null) {
            instance = new HeroService();
            return instance;
        }

        return instance;
    }

    public Hero createHero(String name, HeroClass heroClass) {

        Hero hero;

        if (name == null || heroClass == null) {
            hero = createRandomCharacter();
        } else {
            hero = createCharacter(name, heroClass);
        }

        return hero;
    }

    private Hero createRandomCharacter(){
        HeroClass heroClass = HERO_CLASSES.get(randomInt(HERO_CLASSES.size()));
        String heroName = CLASS_NAMES.get(heroClass)
                .get(randomInt(CLASS_NAMES.get(heroClass).size()));

        return createCharacter(heroName, heroClass);
    }

    private Hero createCharacter(String name, HeroClass heroClass){

        switch (heroClass) {

            case ARCHER: return new Archer(name);

            case MAGE: return new Mage(name);

            case WARRIOR: return new Warrior(name);

            default : return createRandomCharacter();
        }
    }

    public Hero saveCharacter(String key, Hero hero) {
        return heroTable.save(key, hero);
    }

    public Hero get(String key) {
        return heroTable.get(key);
    }
}
