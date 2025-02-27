package equip;

public enum EquipSlot {

    RIGHT_HAND("правая рука"),
    LEFT_HAND("LEFT-HAND"),
    BOTH_HANDS("BOTH-HANDS"),
    HEAD("HEAD"),
    BODY("BODY"),
    BOOTS("BOOTS"),
    GLOVES("GLOVES"),
    BELT("BELT"),
    LEFT_RING("LEFT-RING"),
    RIGHT_RING("RIGHT-RING"),

    INVENTORY_0("0"),
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
    INVENTORY_11("11");

    private final String value;

    EquipSlot(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
