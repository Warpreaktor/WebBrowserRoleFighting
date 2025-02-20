package mechanic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShieldTest {

    @Test
    void takeDamage1() {
        Shield shield = new Shield();
        shield.setValue(100.0);
        double remainingDamage = shield.takeDamage(50);

        assertEquals(0, remainingDamage, "Щит должен полностью поглотить урон");
        assertEquals(50, shield.getValue(), "Остаток щита должен быть 50");
    }

    @Test
    void takeDamage2() {
        Shield shield = new Shield();
        shield.setValue(30.0);

        double remainingDamage = shield.takeDamage(50);

        assertEquals(20, remainingDamage, "Щит должен передать 20 урона, остальное компенсирует");
        assertEquals(0, shield.getValue(), "Щит должен быть уничтожен");
    }

    @Test
    void takeDamage3() {
        Shield shield = new Shield();
        shield.setValue(100.0);
        double remainingDamage = shield.takeDamage(0);

        assertEquals(0, remainingDamage, "Остаточный урон должен быть 0");
        assertEquals(100, shield.getValue(), "Щит не должен измениться при уроне 0");
    }

    @Test
    void takeDamage4() {
        Shield shield = new Shield();
        shield.setValue(100.0);
        double remainingDamage = shield.takeDamage(-50);

        assertEquals(0, remainingDamage, "Отрицательный урон не должен прибавлять щиту прочности");
        assertEquals(100, shield.getValue(), "Щит не должен увеличиться");
    }

}