package hero;

import equip.EquipSlot;
import equip.Equipment;
import hero.classes.Archer;
import hero.classes.Mage;
import hero.classes.Skeleton;
import hero.classes.Warrior;
import item.Inventory;
import item.Item;
import item.weapon.Knife;
import item.weapon.Weapon;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HeroTest {

    HeroService heroService = HeroService.getInstance();

    @ParameterizedTest
    @MethodSource("allConstructor")
    public void mageConstructor(Hero hero) throws IllegalAccessException {
        heroConstructor(hero);
    }

    private static Stream<Arguments> allConstructor() {
        return Stream.of(
                Arguments.of(new Warrior("test")),
                Arguments.of(new Mage("test")),
                Arguments.of(new Archer("test")),
                Arguments.of(new Skeleton("test"))
        );
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

    /**
     * Предмет не может быть брошен если bufferHand == null.
     */
    @Test
    void dropItem1() {
        Hero hero = heroService.createHero(null, null);

        assertThrows(RuntimeException.class,
                () -> hero.moveItem("пофигчто",
                        EquipSlot.INVENTORY_0, EquipSlot.INVENTORY_1));
    }

    /**
     * Если предмет переместить в свободную ячейку он там появится.
     * Из старой ячейки он исчезнет.
     * Рука будет очищена.
     */
    @Test
    void dropItem2() {
        Hero hero = heroService.createHero(null, null);
        Inventory inventoryMock = mock(Inventory.class);
        Equipment equipmentMock = mock(Equipment.class);

        hero.setInventory(inventoryMock);
        hero.setEquipment(equipmentMock);

        Item knife1 = new Knife();
        Item[] cells = new Item[12];
        cells[0] = knife1;

        when(inventoryMock.getCells())
                .thenReturn(cells);

        boolean result = hero.moveItem(knife1.getId(),
                EquipSlot.INVENTORY_0, EquipSlot.INVENTORY_1);

        Assertions.assertTrue(result);
        Assertions.assertNull(inventoryMock.getCells()[0]);
        Assertions.assertEquals(knife1, inventoryMock.getCells()[1]);
    }

    /**
     * Если ячейка инвентаря занята, метод выбрасывает исключение и очищает руку
     * предмет, который был подвергнут перемещению должен остаться на прежнем месте.
     */
    @Test
    void dropItem3() {
        Hero hero = heroService.createHero(null, null);
        Inventory inventoryMock = mock(Inventory.class);
        Equipment equipmentMock = mock(Equipment.class);

        hero.setInventory(inventoryMock);
        hero.setEquipment(equipmentMock);

        // Мокаем, что ячейка уже занята другим предметом
        Item knife1 = new Knife();
        Item knife2 = new Knife();

        Item[] cells = new Item[12];

        cells[0] = knife1;
        cells[1] = knife2;

        when(inventoryMock.getCells())
                .thenReturn(cells);

        assertThrows(RuntimeException.class,
                () -> hero.moveItem(knife1.getId(),
                        EquipSlot.INVENTORY_0, EquipSlot.INVENTORY_1));

        Assertions.assertEquals(knife1, cells[0]);
    }

    /**
     * Если предмет положить туда же где уже лежит он сам:
     * Рука должна очиститься.
     * Исключений вылетать не должно.
     * Предмет должен остаться на своём месте.
     */
    @Test
    void dropItem4() {
        Hero hero = heroService.createHero(null, null);

        hero.setInventory(new Inventory());
        hero.setEquipment(new Equipment());

        Item knife1 = new Knife();

        hero.getInventory().getCells()[0] = knife1;

        assertDoesNotThrow(
                () -> hero.moveItem(knife1.getId(),
                        EquipSlot.INVENTORY_0, EquipSlot.INVENTORY_0));

        Assertions.assertEquals(knife1, hero.getInventory().getCells()[0]);
    }

    /**
     * Если предмет перемещается в RIGHT_HAND:
     * Рука очищается.
     * Предмет исчезает из прежнего места.
     * Предмет появляется в новом месте.
     */
    @Test
    void dropItem5() {
        Hero hero = heroService.createHero(null, null);
        hero.setInventory(new Inventory());
        hero.setEquipment(new Equipment());

        Item knife1 = new Knife();

        hero.getInventory().getCells()[0] = knife1;

        boolean result = hero.moveItem(knife1.getId(),
                EquipSlot.INVENTORY_0, EquipSlot.RIGHT_HAND);

        Assertions.assertTrue(result);

        Assertions.assertNull(hero.getInventory().getCells()[0]);

        Assertions.assertEquals(knife1, hero.getEquipment().getRightHand());
    }

    /**
     * Если equipped() возвращает false, предмет не экипируется.
     * Рука очищается, предмет возвращается на своё место.
     */
    @Test
    void dropItem6() {
        Hero hero = heroService.createHero(null, null);
        Inventory inventoryMock = mock(Inventory.class);
        Equipment equipmentMock = mock(Equipment.class);
        Item testItem = mock(Item.class);

        hero.setInventory(inventoryMock);
        hero.setEquipment(equipmentMock);

        Item knife1 = new Knife();

        Item[] cells = new Item[12];

        cells[0] = knife1;

        when(inventoryMock.getCells())
                .thenReturn(cells);

        when(equipmentMock.equipped(EquipSlot.RIGHT_HAND, testItem))
                .thenReturn(false);

        boolean result = hero.moveItem(knife1.getId(),
                EquipSlot.INVENTORY_0, EquipSlot.RIGHT_HAND);

        Assertions.assertFalse(result);
        verify(equipmentMock, times(1))
                .equipped(EquipSlot.RIGHT_HAND, knife1);
    }

    /**
     * Если передан неподходящий слот, выбрасывается исключение.
     * Предмет возвращается на прежнее место.
     */
    @Test
    void dropItem7() {
        Hero hero = heroService.createHero(null, null);
        Inventory inventoryMock = mock(Inventory.class);
        Equipment equipmentMock = mock(Equipment.class);

        hero.setInventory(inventoryMock);
        hero.setEquipment(equipmentMock);

        Item knife1 = new Knife();

        Item[] cells = new Item[12];

        cells[0] = knife1;

        when(inventoryMock.getCells())
                .thenReturn(cells);

        assertThrows(RuntimeException.class,
                () -> hero.moveItem(knife1.getId(),
                        EquipSlot.INVENTORY_0, EquipSlot.HEAD));

        Assertions.assertEquals(knife1, cells[0]);
    }

    /**
     * Если передан неизвестный предмет, выбрасывается исключение.
     * Предмет возвращается на прежнее место.
     * На новом месте ничего не появляется.
     */
    @Test
    void dropItem8() {
        Hero hero = heroService.createHero(null, null);
        Inventory inventoryMock = mock(Inventory.class);
        Equipment equipmentMock = mock(Equipment.class);

        hero.setInventory(inventoryMock);
        hero.setEquipment(equipmentMock);

        Item knife1 = new Knife();

        Item[] cells = new Item[12];

        cells[0] = knife1;

        when(inventoryMock.getCells())
                .thenReturn(cells);

        Assertions.assertThrows(RuntimeException.class,
                () -> hero.moveItem(UUID.randomUUID().toString(),
                        EquipSlot.INVENTORY_0, EquipSlot.RIGHT_HAND));

        Assertions.assertEquals(knife1, cells[0]);
    }

    /**
     * Если предмет из экипировки перемещается в другой экипировочный слот
     * не подходящий под этот предмет.
     * Предмет возвращается на прежнее место.
     * На новом месте ничего не появляется.
     */
    @Test
    void dropItem9() {
        Hero hero = heroService.createHero(null, null);

        Weapon knife1 = new Knife();
        hero.getEquipment().takeWeapon(knife1);


        Assertions.assertThrows(RuntimeException.class,
                () -> hero.moveItem(knife1.getId(),
                        EquipSlot.RIGHT_HAND, EquipSlot.HEAD));

        Assertions.assertEquals(
                hero.getEquipment().getRightHand(),
                knife1);

    }

    /**
     * Предмет из экипировки перемещается в пустую ячейку инвентаря
     * Предмет появляется в новой ячейки.
     * Предмет исчезает из экипировки.
     */
    @Test
    void dropItem10() {
        Hero hero = heroService.createHero(null, null);
        hero.setInventory(new Inventory());

        Weapon knife1 = new Knife();
        hero.getInventory().getCells()[4] = knife1;

        Assertions.assertDoesNotThrow( () -> hero.moveItem(knife1.getId(),
                        EquipSlot.INVENTORY_4, EquipSlot.RIGHT_HAND));

        Assertions.assertNull(
                hero.getInventory().getCells()[4]);

        Assertions.assertDoesNotThrow(() -> hero.moveItem(knife1.getId(),
                EquipSlot.RIGHT_HAND, EquipSlot.INVENTORY_4));

        Assertions.assertNull(
                hero.getEquipment().getRightHand());

    }
}