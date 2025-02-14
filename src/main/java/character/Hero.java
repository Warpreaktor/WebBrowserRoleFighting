package character;

import lombok.Getter;
import lombok.Setter;
import dto.damage.DamageDto;
import mechanic.Dexterity;
import mechanic.Health;
import mechanic.Intelligence;
import mechanic.Shield;
import mechanic.Strength;
import mechanic.interfaces.Heroic;

@Getter
@Setter
public abstract class Hero implements Heroic {

    /**
     * Имя персонажа. Должно быть у всех, даже у бедных.
     */
    private String name;

//==================================================//
//                  СТАТЫ                           //
//==================================================//
    private Strength strength;

    private Dexterity dexterity;

    private Intelligence intelligence;

//==================================================//
//                  Характеристики                  //
//==================================================//
    /**
     * Вся информация об уроне персонажа.
     */
    private DamageDto damage;

    /**
     * Магический щит. Постепенно увеличивается и уменьшает весь входящий урон.
     */
    private Shield shield;

    /**
     * Здоровье персонажа. Если здоровье упадёт до 0, вы умрёте.
     */
    private Health health;

    /**
     * Меткость, влияет на шанс попадания любой прицельной атакой.
     * Прицельные атаки это любое оружие, которым тыкают, режут или метают, стреляют
     * а так же и вся магия, которая бьёт в цель, типа фаерболов, молний и проч.
     */
    private Double accuracy;

    /**
     * Уклонение увеличивает шанс уклониться от удара, по сути, снижает вражескую меткость
     */
    private Double evasion;

    /**
     * Прогресс перезарядки оружия. Если >= 1 значит можно бить.
     * Обычно этот параметр не накапливается и сбрасывается в 0 при атаке.
     */
    private Double reloader;

    /**
     * Скорость атаки. Влияет на сколько пунктов за каждый ход поднимается параметр reloader.
     */
    private Double agility;

    public Hero() {
        intelligence = new Intelligence(0);
        strength = new Strength(0);
        dexterity = new Dexterity(0);
        shield = new Shield();
        health = new Health();
        accuracy = 0.0;
        agility = 0.0;
        evasion = 0.0;
        reloader = 0.0;
    }

    public void setIntelligence(Integer value) {
        intelligence.setValue(value);

        if (value >= intelligence.getValue()) {
            shield.addMaxValue(value.doubleValue() * 2);
        } else {
            shield.decreaseMaxValue(value.doubleValue() * 2);
        }

        if (value >= intelligence.getValue()) {
            shield.addShieldGrower(value * 0.1);
        } else {
            shield.decreaseShieldGrower(value * 0.1);
        }
    }


    public void setStrength(Integer value) {
        strength.setValue(value);

        if (value >= strength.getValue()) {
            health.addMaxValue(value.doubleValue() * 5);
        } else {
            health.decreaseMaxValue(value.doubleValue() * 5);
        }

        if (value >= strength.getValue()) {
            health.addHeal(value * 0.1);
        } else {
            health.decreaseHeal(value * 0.1);
        }
    }

    public void setDexterity(Integer value) {
        dexterity.setValue(value);
        var dexterityValue = dexterity.getValue();

        if (value >= dexterityValue) {
            addAccuracy(value * 0.1);
        } else {
            decreaseAccuracy(value * 0.1);
        }

        if (value >= dexterityValue) {
            addAgility(value * 0.1);
        } else {
            decreaseAgility(value * 0.1);
        }

        if (value >= dexterityValue) {
            addEvasion(value * 0.1);
        } else {
            decreaseEvasion(value * 0.1);
        }
    }

    public void addAccuracy(Double value) {
        accuracy += value;
    }

    public void decreaseAccuracy(Double value) {
        accuracy -= value;
    }

    public void addAgility(Double value) {
        agility += value;
    }

    public void decreaseAgility(Double value) {
        agility += value;
    }

    public void addEvasion(Double value) {
        evasion += value;
    }

    public void decreaseEvasion(Double value) {
        evasion -= value;
    }

    public void reload() {
        reloader += agility;
    }

    public void shieldGrow() {
        shield.shieldGrow();
    }
}
