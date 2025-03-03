package hero.constants.names;


import java.util.List;

/**
 * Класс {@code HeroNames} содержит предопределенные списки имен для различных классов героев.
 * Используется для генерации случайных имен при создании персонажей.
 * <p>
 * Классы героев:
 * <ul>
 *     <li>Воин ({@link #WARRIOR_NAMES})</li>
 *     <li>Маг ({@link #MAGE_NAMES})</li>
 *     <li>Лучник ({@link #ARCHER_NAMES})</li>
 *     <li>Скелет ({@link #SKELETON_NAMES})</li>
 * </ul>
 */
public final class HeroNames {

    private HeroNames() {}

    /**
     * Список имен для класса "Воин".
     */
    public static final List<String> WARRIOR_NAMES = List.of(
            "Рагнар", "Крум", "Торгрим", "Вольфрик", "Драгор", "Ульфгар",
            "Грогаш", "Бьорн", "Скар", "Тарг", "Хротгар", "Одинокий Волк",
            "Дреккар", "Моркар", "Зигфрид", "Фенрир", "Ворн", "Хаген",
            "Грюм", "Торстен", "Лугнор", "Эйнар", "Хельвар", "Гуннар",
            "Сигурд", "Кром", "Хальвар", "Вранг", "Борг", "Йорген"
    );

    /**
     * Список имен для класса "Маг".
     */
    public static final List<String> MAGE_NAMES = List.of(
            "Мерлин", "Эльдрак", "Затара", "Альзарак", "Фенрис", "Ордо",
            "Малкорион", "Таргон", "Сальвиус", "Астрагор", "Локриан", "Мидран",
            "Эльмариус", "Феаргрим", "Нокс", "Селезар", "Дрейкворт", "Альзирон",
            "Каларис", "Талмор", "Галадриан", "Руфинус", "Зерон", "Агатос",
            "Маласар", "Вардан", "Иллюмор", "Тарсис", "Санхариус", "Зарафим"
    );

    /**
     * Список имен для класса "Лучник".
     */
    public static final List<String> ARCHER_NAMES = List.of(
            "Элдрин", "Тарвин", "Леголас", "Финдор", "Кайриан", "Рунальф",
            "Альториан", "Скайлен", "Эрвин", "Гилвар", "Тиандр", "Фенрир",
            "Ларвис", "Гайлен", "Сильвен", "Айден", "Вальтар", "Рейнальд",
            "Эльмир", "Зилвер", "Тайрон", "Орланд", "Фаргон", "Равен",
            "Стрелок Орис", "Хантер", "Бельмонт", "Треон", "Сендор", "Каспер"
    );

    /**
     * Список имен для класса "Скелет".
     */
    public static final List<String> SKELETON_NAMES = List.of(
            "Бони", "Грим", "Морт", "Скалли", "Дракс", "Костяной Джо",
            "Фантом", "Некро", "Скелер", "Гаргул", "Лич", "Остин",
            "Гор", "Мрак", "Костяной Рыцарь", "Ротгул", "Зловещий Смех",
            "Трупер", "Прах", "Мертвяк", "Теневой Страж", "Чёрный Череп",
            "Костяной щюп", "Скелетон", "Дред", "Погибель", "Хаос", "Тлен"
    );

    /**
     * Список имен для класса "Полурослик".
     */
    public static final List<String> HALFLING_NAMES = List.of(
            "Финник", "Тобин", "Меррик", "Дидо", "Ролло", "Самвел",
            "Лорик", "Перрин", "Бофур", "Тук", "Джинджер", "Одо",
            "Фродо", "Гейл", "Виспер", "Розко", "Тимбит", "Нимбл",
            "Бранни", "Твик", "Руфус", "Элдо", "Корбин", "Лайл",
            "Гудвин", "Брик", "Милло", "Гаспер", "Даггерт", "Шорти",
            "Коротыга"
    );

    /**
     * Список имен для класса "Самодива".
     */
    public static final List<String> SAMODIVA_NAMES = List.of(
            "Жива", "Миловзора", "Ветрена", "Зореслава", "Лада", "Ружена",
            "Веселина", "Дарина", "Свилена", "Ясноглазка", "Златея", "Божена",
            "Дивна", "Огнеслава", "Тихомира", "Радосвета", "Магура", "Снежана",
            "Любава", "Зорница", "Мирослава", "Видана", "Венцеслава", "Огнена",
            "Родена", "Велемира", "Светозара", "Влада", "Вихра", "Роксана"
    );

}
