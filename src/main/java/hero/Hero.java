package hero;

import equip.EquipSlot;
import equip.Equipment;
import equip.EquipmentDto;
import item.Inventory;
import item.Item;
import item.weapon.Knife;
import lombok.Getter;
import lombok.Setter;
import dto.damage.DamageDto;
import mechanic.Dexterity;
import mechanic.Health;
import mechanic.Intelligence;
import mechanic.Shield;
import mechanic.Strength;
import mechanic.interfaces.Heroic;
import item.weapon.Weapon;

import java.util.Objects;
import java.util.Optional;

import static equip.EquipSlot.RIGHT_HAND;

@Getter
@Setter
public abstract class Hero implements Heroic {

    /**
     * Имя персонажа. Должно быть у всех, даже у бедных.
     */
    private String name;

    /**
     * Экипировка. То что надето на персонажа.
     */
    private Equipment equipment;

    /**
     * Инвентарь персонажа. То что он носит в мешке за спиной.
     */
    private Inventory inventory;

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
        equipment = new Equipment();
        inventory = new Inventory();

        inventory.add(new Knife());
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

    /**
     * Метод, который должен пересчитывать все характеристики персонажа.
     * Если какая-то экипировка влияет на статы значит этот метод должен узнать об этом первый,
     * и предпринять необходимые пересчёты в связи с этим.
     * Должен вызываться всегда при экипировке и снятии любого предмета.
     * Логика одинаковая в обоих случаях.
     */
    private void refresh() {
    }

    public DamageDto getDamage() {
        return getEquipment().getRightHand().getDamage();
    }

    /**
     * Метод выбрасывает предмет из буфера в указанное место.
     * Местом может быть инвентарь, экипировочный слот или пространство уровня.
     * Метод буквально копирует предмет из одного места в другое, а в старом удаляет.
     */
    public boolean moveItem(String objectId, EquipSlot from, EquipSlot to) {

        Item item = getItemByIdAndSlot(objectId, from)
                .orElseThrow( () -> new RuntimeException("Предмета не существует"));

        if (to.name().startsWith("INVENTORY")) {
            int cell = Integer.parseInt(to.getValue());

            if (inventory.getCells()[cell] == null) {

                inventory.getCells()[cell] = item;

                clearSlotByObjectIdAndSlot(objectId, from);

                return true;

            } else if (!Objects.equals(inventory.getCells()[cell], item)) {

                throw new RuntimeException("Ячейка занята");

            } else {
                return false;
            }
        }

        switch (to) {

            case RIGHT_HAND -> {
                if (equipment.equipped(RIGHT_HAND, item)) {

                    clearSlotByObjectIdAndSlot(objectId, from);

                    return true;

                } else {
                    return false;
                }
            }

            default -> throw new RuntimeException("Слот экипировки [" + to + "] не определён");
        }
    }

    /**
     * Метод возвращает объект по его идентификатору и слоту в котором он находится.
     * Метод ищет предмет только в инвентаре героя и его слотах экипировки.
     * Главным образом метод действительно проверяет, а находится ли у меня на сервере
     * вот в таком-то слоте вот такой-то предмет исключая возможность ложного запроса с фронта.
     */
    private Optional<? extends Item> getItemByIdAndSlot(String objectId, EquipSlot slot) {

        if (slot.name().startsWith("INVENTORY")) {
            int cell = Integer.parseInt(slot.getValue());
            Item item = inventory.getCells()[cell];

            if (item != null
                    && Objects.equals(objectId, item.getId())) {

                return Optional.of(item);
            }
        }

        switch (slot) {

            case RIGHT_HAND -> {
                return Optional.of(equipment.getRightHand());
            }

            default -> {
                return Optional.empty();
            }
        }
    }

    /**
     * Метод очищает местонахождения от объекта который там находится.
     * Навсегда.
     * Безвозвратно.
     * Метод ищет предмет только в инвентаре героя и его слотах экипировки.
     * Если в очищаемой ячейке хранится предмет с идентификатором не соответствующим переданному,
     * то метод не будет производить очистку и выбросит исключение.
     *
     */
    private void clearSlotByObjectIdAndSlot(String objectId, EquipSlot slot) {

        if (slot.name().startsWith("INVENTORY")) {

            int cell = Integer.parseInt(slot.getValue());

            if (Objects.equals(objectId, inventory.getCells()[cell].getId())) {
                inventory.getCells()[cell] = null;
            }

        } else {
            switch (slot) {

                case RIGHT_HAND -> {
                    if (Objects.equals(equipment.getRightHand().getId(), objectId)) {
                        equipment.takeWeapon(this.getEquipment().getFist());
                    } else {
                        throw new RuntimeException("Предмет не найден по идентификатору: [" + objectId + "]" );
                    }
                }

                default -> {
                    new RuntimeException("Слот экипировки [" + slot + "] не определён");
                }
            }
        }
    }
}
