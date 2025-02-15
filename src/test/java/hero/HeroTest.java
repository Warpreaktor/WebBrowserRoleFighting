package hero;

import hero.classes.Archer;
import hero.classes.Mage;
import hero.classes.Warrior;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HeroTest {

    @Test
    public void mageConstructor() throws IllegalAccessException {
        heroConstructor(new Mage("test"));
    }

    @Test
    public void warriorConstructor() throws IllegalAccessException {
        heroConstructor(new Warrior("test"));
    }

    @Test
    public void archerConstructor() throws IllegalAccessException {
        heroConstructor(new Archer("test"));
    }


    /**
     * Тест проверяет, что при создании героя определённого класса,
     * все его поля будут заполнены значениями.
     */
    private void heroConstructor(Hero hero) throws IllegalAccessException {
        Class<?> currentClass = hero.getClass();

        while (currentClass != null && currentClass != Object.class) {
            Field[] fields = currentClass.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(hero);

                assertNotNull(value,
                        "Поле " + field.getName() +
                        " в классе " + hero.getClass().getSimpleName() +
                        " равно null!");
            }

            currentClass = currentClass.getSuperclass();
        }
    }
}