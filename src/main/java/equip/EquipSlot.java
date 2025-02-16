package equip;

public enum EquipSlot {

    HEAD("голова"),

    LEFT_HAND("левая рука"),

    RIGHT_HAND("right-hand"),

    BODY("тело"),

    GLOVES("перчатки"),

    BOOTS("сапоги"),

    BELT("пояс"),

    LEFT_RING("левое кольцо"),

    RIGHT_RING("правое кольцо"),

    INVENTORY_1("1"),
    INVENTORY_2("2"),
    INVENTORY_3("3"),
    INVENTORY_4("4"),
    INVENTORY_5("5"),
    INVENTORY_6("6"),
    INVENTORY_7("7"),
    INVENTORY_8("8"),
    INVENTORY_9("9"),
    INVENTORY_10("10"),
    INVENTORY_11("11"),
    INVENTORY_12("12");

    private final String value;
    EquipSlot(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
