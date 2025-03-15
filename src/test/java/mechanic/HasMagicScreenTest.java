package mechanic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HasMagicScreenTest {

    @Test
    void takeDamage1() {
        MagicScreen magicScreen = new MagicScreen();
        magicScreen.setValue(100.0);
        double remainingDamage = magicScreen.takeDamage(50);

        assertEquals(0, remainingDamage, "Щит должен полностью поглотить урон");
        assertEquals(50, magicScreen.getValue(), "Остаток щита должен быть 50");
    }

    @Test
    void takeDamage2() {
        MagicScreen magicScreen = new MagicScreen();
        magicScreen.setValue(30.0);

        double remainingDamage = magicScreen.takeDamage(50);

        assertEquals(20, remainingDamage, "Щит должен передать 20 урона, остальное компенсирует");
        assertEquals(0, magicScreen.getValue(), "Щит должен быть уничтожен");
    }

    @Test
    void takeDamage3() {
        MagicScreen magicScreen = new MagicScreen();
        magicScreen.setValue(100.0);
        double remainingDamage = magicScreen.takeDamage(0);

        assertEquals(0, remainingDamage, "Остаточный урон должен быть 0");
        assertEquals(100, magicScreen.getValue(), "Щит не должен измениться при уроне 0");
    }

    @Test
    void takeDamage4() {
        MagicScreen magicScreen = new MagicScreen();
        magicScreen.setValue(100.0);
        double remainingDamage = magicScreen.takeDamage(-50);

        assertEquals(0, remainingDamage, "Отрицательный урон не должен прибавлять щиту прочности");
        assertEquals(100, magicScreen.getValue(), "Щит не должен увеличиться");
    }

}