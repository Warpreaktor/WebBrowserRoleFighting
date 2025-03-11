package hero;

import dto.damage.DamageDto;
import mechanic.Ability;
import mechanic.Damage;
import equip.EquipSlot;
import equip.Equipment;
import item.Inventory;
import item.Item;
import lombok.Getter;
import lombok.Setter;
import mechanic.Dexterity;
import mechanic.Endurance;
import mechanic.Health;
import mechanic.Intelligence;
import mechanic.MagicScreen;
import mechanic.Strength;
import mechanic.interfaces.Heroic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static constants.GlobalConstants.ACCURACY_PER_DEXTERITY_MULTIPLIER;
import static constants.GlobalConstants.ENDURANCE_GROWER_PER_DEXTERITY_MULTIPLIER;
import static constants.GlobalConstants.EVASION_PER_DEXTERITY_MULTIPLIER;
import static constants.GlobalConstants.HEALTH_PER_STRENGTH_MULTIPLIER;
import static constants.GlobalConstants.HEAL_PER_STRENGTH_MULTIPLIER;
import static constants.GlobalConstants.MAGIC_SCREEN_GROWER_PER_INTELLIGENCE_MULTIPLIER;
import static constants.GlobalConstants.MAGIC_SCREEN_PER_INTELLIGENCE_MULTIPLIER;
import static tools.Calculator.average;

public abstract class Hero implements Heroic {

//==================================================//
//                  ОБЩЕЕ                           //
//==================================================//
    /**
     * Имя персонажа. Должно быть у всех, даже у бедных.
     */
    @Getter
    @Setter
    private String name;

    @Getter
    private Statistic statistic;

    @Getter
    private State state;

    //==================================================//
    //                  ОДЁЖКА                          //
    //==================================================//
    /**
     * Экипировка. То что надето на персонажа.
     */
    @Getter
    private Equipment equipment;

    /**
     * Инвентарь персонажа. То что он носит в мешке за спиной.
     */
    @Getter
    private Inventory inventory;

    //==================================================//
    //                  СТАТЫ                           //
    //==================================================//
    /**
     * Сила влияет на очки здоровья
     */
    private Strength strength;

    /**
     * Ловкость влияет на шанс попадение и шанс уклонения
     */
    private Dexterity dexterity;

    /**
     * Интеллект влияет на максимальный размер магического щита и на его скорость восполнения.
     */
    private Intelligence intelligence;

//==================================================//
//                  Характеристики                  //
//==================================================//

    /**
     * Магический щит. Постепенно увеличивается и уменьшает весь входящий урон.
     */
    @Getter
    private MagicScreen magicScreen;

    /**
     * Здоровье персонажа. Если здоровье упадёт до 0, вы умрёте.
     */
    @Getter
    private Health health;

    /**
     * Выносливость персонажа.
     */
    @Getter
    private Endurance endurance;

    /**
     * Меткость, влияет на шанс попадания любой прицельной атакой.
     * Прицельные атаки это любое оружие, которым тыкают, режут или метают, стреляют,
     * а так же и вся магия, которая бьёт в цель, типа фаерболов, молний и проч.
     */
    @Getter
    @Setter
    private Double accuracy;

    /**
     * Уклонение увеличивает шанс уклониться от удара, по сути, снижает вражескую меткость
     */
    @Getter
    @Setter
    private Double evasion;

    /**
     * Шанс блокировки удара
     */
    @Getter
    @Setter
    private double blockChance;

    /**
     * Шанс критического удара. Проверка на критический удар проходит во время фазы атаки.
     */
    @Getter
    @Setter
    private Double critChance;

    @Getter
    private List<Ability> abilities;

    public Hero() {
        statistic = new Statistic();
        state = new State();
        intelligence = new Intelligence(0);
        strength = new Strength(0);
        dexterity = new Dexterity(0);
        inventory = new Inventory();
        magicScreen = new MagicScreen();
        health = new Health();
        accuracy = 0.0;
        evasion = 0.0;
        endurance = new Endurance();
        blockChance = 0.0;
        critChance = 0.0;
        abilities = new ArrayList<>(4);
        equipment = new Equipment(this);
    }

    public void setIntelligence(Integer value) {
        intelligence.setValue(value);

        if (value >= intelligence.getValue()) {
            magicScreen.addMaxValue(value.doubleValue() * MAGIC_SCREEN_PER_INTELLIGENCE_MULTIPLIER);
        } else {
            magicScreen.decreaseMaxValue(value.doubleValue() * MAGIC_SCREEN_PER_INTELLIGENCE_MULTIPLIER);
        }

        if (value >= intelligence.getValue()) {
            magicScreen.addMagicScreenGrower(value * MAGIC_SCREEN_GROWER_PER_INTELLIGENCE_MULTIPLIER);
        } else {
            magicScreen.decreaseMagicScreenGrower(value * MAGIC_SCREEN_GROWER_PER_INTELLIGENCE_MULTIPLIER);
        }
    }

    /**
     * От установки значения силы зависит то, сколько HP будет у персонажа
     *
     * @param value количество силы
     */
    public void setStrength(Integer value) {
        strength.setValue(value);

        if (value >= strength.getValue()) {
            health.addMaxValue(value.doubleValue() * HEALTH_PER_STRENGTH_MULTIPLIER);
        } else {
            health.decreaseMaxValue(value.doubleValue() * HEALTH_PER_STRENGTH_MULTIPLIER);
        }

        if (value >= strength.getValue()) {
            health.addGrower(value * HEAL_PER_STRENGTH_MULTIPLIER);
        } else {
            health.decreaseGrower(value * HEAL_PER_STRENGTH_MULTIPLIER);
        }
    }

    public void setDexterity(Integer value) {
        dexterity.setValue(value);
        var dexterityValue = dexterity.getValue();

        if (value >= dexterityValue) {
            addAccuracy(value * ACCURACY_PER_DEXTERITY_MULTIPLIER);
        } else {
            decreaseAccuracy(value * ACCURACY_PER_DEXTERITY_MULTIPLIER);
        }

        if (value >= dexterityValue) {
            addEnduranceGrower(value * ENDURANCE_GROWER_PER_DEXTERITY_MULTIPLIER);
        } else {
            decreaseEnduranceGrower(value * ENDURANCE_GROWER_PER_DEXTERITY_MULTIPLIER);
        }

        if (value >= dexterityValue) {
            addEvasion(value * EVASION_PER_DEXTERITY_MULTIPLIER);
        } else {
            decreaseEvasion(value * EVASION_PER_DEXTERITY_MULTIPLIER);
        }
    }

    public void addAccuracy(Double value) {
        accuracy += value;
    }

    public void decreaseAccuracy(Double value) {
        accuracy -= value;
    }

    /**
     * Прибавить значение к текущей скорости атаки
     *
     * @param value прибавляемой значение
     */
    public void addEnduranceGrower(Double value) {
        endurance.addGrower(value);
    }

    public void decreaseEnduranceGrower(Double value) {
        endurance.decreaseGrower(value);
    }

    public void addEvasion(Double value) {
        evasion += value;
    }

    public void decreaseEvasion(Double value) {
        evasion -= value;
    }

    /**
     * Отдых. Метод восстанавливающий очки выносливости.
     */
    public void endurancePointGrow() {
        endurance.grow();
    }

    /**
     * Измождение. Метод отнимающий очки выносливости.
     *
     * @param cost количество вычитаемых очков
     */
    @Override
    public void exhaustion(double cost) {
        endurance.decreaseValue(cost);
    }

    public void magicScreenGrow() {
        magicScreen.grow();
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

    /**
     * Урон героя как есть, включая все модификаторы экипировки.
     */
    @Override
    public Damage getStaticDamage() {
        //TODO Здесь предполагается код, который будет опрашивать всю экипировку и действующие на героя модификаторы
        var rightHand = getEquipment().getRightHand().getDamage();

        var piercingDamage = rightHand.getPiercing();
        var crushingDamage = rightHand.getCrushing();
        var cuttingDamage = rightHand.getCutting();
        var fireDamage = rightHand.getFire();
        var electricDamage = rightHand.getElectric();

        return new Damage(
        piercingDamage,
        crushingDamage,
        cuttingDamage,
        fireDamage,
                electricDamage
        );
    }

    /**
     * Урон героя для отображения в статистике. Берутся средние значения.
     */
    @Override
    public DamageDto getDamageDto() {
        //TODO Здесь предполагается код, который будет опрашивать всю экипировку и действующие на героя модификаторы
        var rightHand = getEquipment().getRightHand().getDamage();

        var piercingDamage = rightHand.getPiercing();
        var crushingDamage = rightHand.getCrushing();
        var cuttingDamage = rightHand.getCutting();
        var fireDamage = rightHand.getFire();
        var electricDamage = rightHand.getElectric();

        return new DamageDto(
                average(List.of(piercingDamage.getMin(), piercingDamage.getMax())),
                average(List.of(crushingDamage.getMin(), crushingDamage.getMax())),
                average(List.of(cuttingDamage.getMin(), cuttingDamage.getMax())),
                average(List.of(fireDamage.getMin(), fireDamage.getMax())),
                average(List.of(electricDamage.getMin(), electricDamage.getMax()))
        );
    }

    /**
     * Метод выбрасывает предмет из буфера в указанное место.
     * Местом может быть инвентарь, экипировочный слот или пространство уровня.
     * Метод буквально копирует предмет из одного места в другое, а в старом удаляет.
     */
    public boolean moveItem(String objectId, EquipSlot from, EquipSlot to) {

        Item item = getItemByIdAndSlot(objectId, from)
                .orElseThrow(() -> new RuntimeException("Предмета не существует"));

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

        if (equipment.equipped(to, item)) {

            clearSlotByObjectIdAndSlot(objectId, from);

            return true;

        } else {

            return false;
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

            case LEFT_HAND -> {
                return Optional.of(equipment.getLeftHand());
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
                    //Проверяем что удаляемый предмет правда экипирован
                    if (Objects.equals(equipment.getRightHand().getId(), objectId)) {

                        equipment.unequipRightHand();
                    } else {
                        throw new RuntimeException("Предмет не найден по идентификатору: [" + objectId + "]");
                    }
                }

                case LEFT_HAND -> {
                    if (equipment.getLeftHand() != null && Objects.equals(equipment.getLeftHand().getId(), objectId)) {
                        // Сохраняем attackSpeed перед снятием
                        double attackSpeed = equipment.getLeftHand().getAttackSpeed();
                        // Возвращаем agility обратно
                        endurance.setGrower(endurance.getGrower() / attackSpeed);
                        equipment.unequipLeftHand();
                    } else {
                        throw new RuntimeException("Предмет не найден по идентификатору: [" + objectId + "]");
                    }
                }

                default -> {
                    new RuntimeException("Слот экипировки [" + slot + "] не определён");
                }
            }
        }
    }

    /**
     * Добавляет новую способность героя
     */
    public void addAbilities(List<Ability> ability) {
        this.abilities.addAll(ability);
    }

}
